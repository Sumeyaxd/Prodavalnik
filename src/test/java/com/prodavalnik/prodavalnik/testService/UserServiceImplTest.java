package com.prodavalnik.prodavalnik.testService;

import com.prodavalnik.prodavalnik.model.entity.Role;
import com.prodavalnik.prodavalnik.model.entity.User;
import com.prodavalnik.prodavalnik.model.enums.RoleEnum;
import com.prodavalnik.prodavalnik.model.enums.UpdateInfo;
import com.prodavalnik.prodavalnik.model.user.UserInfoDTO;
import com.prodavalnik.prodavalnik.model.user.UserRegisterDTO;
import com.prodavalnik.prodavalnik.model.user.UserUpdateInfoDTO;
import com.prodavalnik.prodavalnik.repository.UserRepository;
import com.prodavalnik.prodavalnik.service.RoleService;
import com.prodavalnik.prodavalnik.service.events.UserRegistration;
import com.prodavalnik.prodavalnik.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userServiceToTest;

    @Captor
    private ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    @Mock
    private ApplicationEventPublisher mockApplicationEventPublisher;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private RoleService mockRoleService;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    public void setUp() {
        this.userServiceToTest = new UserServiceImpl(mockApplicationEventPublisher, mockUserRepository,
                mockRoleService, mockPasswordEncoder, new ModelMapper());
    }

    @Test
    public void testRegisterUser_NullRegisterUserDTO() {
        boolean result = userServiceToTest.registerUser(null);
        assertFalse(result);
        verify(mockUserRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    public void testRegisterUser_Password_DifferentFrom_ConfirmPassword() {
        UserRegisterDTO userRegisterDTO = createUserRegisterDTO();
        userRegisterDTO.setPassword("Password1234");
        userRegisterDTO.setConfirmPassword("Different1234");

        when(mockUserRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(mockUserRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        boolean result = userServiceToTest.registerUser(userRegisterDTO);

        assertFalse(result);
        verify(mockUserRepository, never()).saveAndFlush(any(User.class));
    }

    @Test
    public void testRegisterUser_Success() {
        UserRegisterDTO userRegisterDTO = createUserRegisterDTO();

        Role role = new Role();
        role.setRole(RoleEnum.USER);

        when(mockUserRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(mockUserRepository.findByEmail("testuser@example.com")).thenReturn(Optional.empty());
        when(mockRoleService.findByRole(RoleEnum.USER)).thenReturn(Optional.of(role));
        when(mockPasswordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("encodedPassword123");

        boolean result = userServiceToTest.registerUser(userRegisterDTO);

        verify(mockUserRepository).saveAndFlush(userCaptor.capture());

        assertTrue(result);
        assertNotNull(userCaptor.getValue());
        assertEquals(userRegisterDTO.getFullName(), userCaptor.getValue().getFullName());
        assertEquals(userRegisterDTO.getAddress(), userCaptor.getValue().getAddress());
        assertEquals(userRegisterDTO.getPhoneNumber(), userCaptor.getValue().getPhoneNumber());
        assertEquals(userRegisterDTO.getEmail(), userCaptor.getValue().getEmail());

        verify(mockUserRepository, times(1)).saveAndFlush(any(User.class));
        verify(mockApplicationEventPublisher, times(1))
                .publishEvent(any(UserRegistration.class));
    }

    @Test
    public void testRegisterUser_UsernameExists() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testuser");
        userRegisterDTO.setEmail("test@example.com");

        when(mockUserRepository.findByUsername(userRegisterDTO.getUsername())).thenReturn(Optional.of(new User()));

        boolean result = userServiceToTest.registerUser(userRegisterDTO);

        assertFalse(result);
    }

    @Test
    public void testGetUserDetailsInfo() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));

        UserInfoDTO userInfoDTO = userServiceToTest.getUserDetailsInfo(userId);

        assertNotNull(userInfoDTO);
        verify(mockUserRepository, times(1)).findById(userId);
    }

    @Test
    public void testUpdateUserPropertyUsername() {
        String currentUsername = "currentuser";
        String newUsername = "newuser";
        User user = new User();
        user.setUsername(currentUsername);

        UserUpdateInfoDTO userUpdateInfoDTO = new UserUpdateInfoDTO();
        userUpdateInfoDTO.setUpdateInfo(UpdateInfo.USERNAME);
        userUpdateInfoDTO.setData(newUsername);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));
        when(mockUserRepository.findByUsername(newUsername)).thenReturn(Optional.empty());

        boolean result = userServiceToTest.updateUserProperty(userUpdateInfoDTO);

        assertTrue(result);
        verify(mockUserRepository, times(1)).saveAndFlush(user);
        assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void testUpdateUserPropertyUsername_Failed() {
        String currentUsername = "currentuser";
        String newUsername = "";
        User user = new User();
        user.setUsername(currentUsername);

        UserUpdateInfoDTO userUpdateInfoDTO = new UserUpdateInfoDTO();
        userUpdateInfoDTO.setUpdateInfo(UpdateInfo.USERNAME);
        userUpdateInfoDTO.setData(newUsername);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));
        when(mockUserRepository.findByUsername(newUsername)).thenReturn(Optional.empty());

        boolean result = userServiceToTest.updateUserProperty(userUpdateInfoDTO);

        assertFalse(result);
        verify(mockUserRepository, never()).saveAndFlush(user);
    }

    @Test
    void testUpdateFullName_Successful() {
        User user = new User();
        String currentUsername = "currentuser";
        user.setUsername(currentUsername);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));

        UserUpdateInfoDTO dto = new UserUpdateInfoDTO();
        dto.setUpdateInfo(UpdateInfo.FULL_NAME);
        dto.setData("John Doe");

        boolean result = userServiceToTest.updateUserProperty(dto);

        assertTrue(result);
        verify(mockUserRepository).saveAndFlush(user);
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void testUpdateFullName_Failed() {
        User user = new User();
        String currentUsername = "currentuser";
        user.setUsername(currentUsername);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));

        UserUpdateInfoDTO dto = new UserUpdateInfoDTO();
        dto.setUpdateInfo(UpdateInfo.FULL_NAME);
        dto.setData("Jo");

        boolean result = userServiceToTest.updateUserProperty(dto);

        assertFalse(result);
        verify(mockUserRepository, never()).saveAndFlush(user);
    }

    @Test
    public void testUpdateUserPropertyEmail_Successful() {
        String currentUsername = "currentuser";
        String newEmail = "new_email@example.com";
        User user = new User();
        user.setUsername(currentUsername);

        UserUpdateInfoDTO dto = new UserUpdateInfoDTO();
        dto.setUpdateInfo(UpdateInfo.EMAIL);
        dto.setData(newEmail);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));
        when(mockUserRepository.findByEmail(newEmail)).thenReturn(Optional.empty());

        boolean result = userServiceToTest.updateUserProperty(dto);

        assertTrue(result);
        verify(mockUserRepository, times(1)).saveAndFlush(user);
        assertEquals(newEmail, user.getEmail());
    }

    @Test
    public void testUpdateUserPropertyEmail_Failed() {
        String currentUsername = "currentuser";
        String newEmail = "new_email@example.com";
        User user = new User();
        user.setUsername(currentUsername);

        UserUpdateInfoDTO userUpdateInfoDTO = new UserUpdateInfoDTO();
        userUpdateInfoDTO.setUpdateInfo(UpdateInfo.EMAIL);
        userUpdateInfoDTO.setData(newEmail);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));
        when(mockUserRepository.findByEmail(newEmail)).thenReturn(Optional.of(user));

        boolean result = userServiceToTest.updateUserProperty(userUpdateInfoDTO);

        assertFalse(result);
        verify(mockUserRepository, never()).saveAndFlush(user);
    }


    @Test
    void testUpdatePhoneNumber_Failed() {
        User user = new User();
        String currentUsername = "currentuser";
        user.setUsername(currentUsername);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));

        UserUpdateInfoDTO dto = new UserUpdateInfoDTO();
        dto.setUpdateInfo(UpdateInfo.PHONE_NUMBER);
        dto.setData("11");

        boolean result = userServiceToTest.updateUserProperty(dto);

        assertFalse(result);
        verify(mockUserRepository, never()).saveAndFlush(user);
    }


    @Test
    void testUpdateAddress_Failed() {
        User user = new User();
        String currentUsername = "currentuser";
        user.setUsername(currentUsername);

        mockSecurityContext(currentUsername);
        when(mockUserRepository.findByUsername(currentUsername)).thenReturn(Optional.of(user));

        UserUpdateInfoDTO dto = new UserUpdateInfoDTO();
        dto.setUpdateInfo(UpdateInfo.ADDRESS);
        dto.setData("");

        boolean result = userServiceToTest.updateUserProperty(dto);

        assertFalse(result);
        verify(mockUserRepository, never()).saveAndFlush(user);
    }

    @Test
    public void testFindUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        Optional<User> result = userServiceToTest.findUserByUsername(username);

        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
    }

    @Test
    public void testFindUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userServiceToTest.findUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
    }

    @Test
    public void testFindUserByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(mockUserRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userServiceToTest.findUserByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    private UserRegisterDTO createUserRegisterDTO() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();

        userRegisterDTO.setUsername("testuser");
        userRegisterDTO.setFullName("Test User");
        userRegisterDTO.setPhoneNumber("111222333");
        userRegisterDTO.setAddress("Test address");
        userRegisterDTO.setEmail("testuser@example.com");
        userRegisterDTO.setPassword("Test1234");
        userRegisterDTO.setConfirmPassword("Test1234");

        return userRegisterDTO;
    }

    private void mockSecurityContext(String username) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);
    }

}
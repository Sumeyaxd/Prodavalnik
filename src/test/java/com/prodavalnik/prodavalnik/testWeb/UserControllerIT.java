package com.prodavalnik.prodavalnik.testWeb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.prodavalnik.prodavalnik.model.user.UserRegisterDTO;
import com.prodavalnik.prodavalnik.service.UserService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() {
        ServerSetup smtp = new ServerSetup(port, host, "smtp");
        greenMail = new GreenMail(smtp);
        greenMail.start();
        greenMail.setUser(username, password);
    }

    @AfterEach
    void tearDown() {
        if (greenMail != null) {
            greenMail.stop();
        }
    }

    @Test
    void testLogin_Get() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testRegister_Get() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterDTO"));
    }



    @Test
    void testRegister_Post_ErrorInConfirmPassword() throws Exception {
        UserRegisterDTO userRegisterDTO = createUserDTO();
        userRegisterDTO.setConfirmPassword("Wrong1234");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userRegisterDTO.getUsername())
                        .param("email", userRegisterDTO.getEmail())
                        .param("password", userRegisterDTO.getPassword())
                        .param("fullName", userRegisterDTO.getFullName())
                        .param("phoneNumber", userRegisterDTO.getPhoneNumber())
                        .param("address", userRegisterDTO.getAddress())
                        .param("confirmPassword", userRegisterDTO.getConfirmPassword())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testRegister_Post_InvalidData() throws Exception {
        UserRegisterDTO userRegisterDTO = createUserDTO();
        userRegisterDTO.setFullName("");
        userRegisterDTO.setAddress("no");

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", userRegisterDTO.getUsername())
                        .param("email", userRegisterDTO.getEmail())
                        .param("password", userRegisterDTO.getPassword())
                        .param("fullName", userRegisterDTO.getFullName())
                        .param("phoneNumber", userRegisterDTO.getPhoneNumber())
                        .param("address", userRegisterDTO.getAddress())
                        .param("confirmPassword", userRegisterDTO.getConfirmPassword())
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userRegisterDTO"))
                .andExpect(model().attributeHasFieldErrors("userRegisterDTO", "fullName", "address"));
    }

    private UserRegisterDTO createUserDTO() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();

        userRegisterDTO.setUsername("testuser");
        userRegisterDTO.setEmail("testuser@example.com");
        userRegisterDTO.setPassword("User1234");
        userRegisterDTO.setFullName("Test User");
        userRegisterDTO.setConfirmPassword("User1234");
        userRegisterDTO.setPhoneNumber("111222333");
        userRegisterDTO.setAddress("Test address");

        return userRegisterDTO;
    }
}

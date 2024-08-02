package com.prodavalnik.prodavalnik.service;

import com.prodavalnik.prodavalnik.model.entity.User;
import com.prodavalnik.prodavalnik.model.user.UserInfoDTO;
import com.prodavalnik.prodavalnik.model.user.UserRegisterDTO;
import com.prodavalnik.prodavalnik.model.user.UserUpdateInfoDTO;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserById(Long id);

    void saveAndFlushUser(User user);

    boolean updateUserProperty(UserUpdateInfoDTO userUpdateInfoDTO);

    String getLoggedUsername();

    Optional<User> findUserByUsername(String username);

    boolean registerUser(UserRegisterDTO userRegisterDTO);

    UserInfoDTO getUserDetailsInfo(Long id);
}

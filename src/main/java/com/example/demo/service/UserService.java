package com.example.demo.service;

import com.example.demo.model.entity.User;
import com.example.demo.model.user.UserInfoDTO;
import com.example.demo.model.user.UserRegisterDTO;
import com.example.demo.model.user.UserUpdateInfoDTO;

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

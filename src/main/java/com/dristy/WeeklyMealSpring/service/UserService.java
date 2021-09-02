package com.dristy.WeeklyMealSpring.service;

import com.dristy.WeeklyMealSpring.controller.data.LoginFormData;
import com.dristy.WeeklyMealSpring.db.UserRepository;
import com.dristy.WeeklyMealSpring.domain.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean validateUserDate(LoginFormData loginFormData) {
        String username = loginFormData.getUsername().toLowerCase();
        String passwordHash = DigestUtils.sha256Hex(loginFormData.getPassword());
        Optional<User> optionalUser = userRepository.findByUsername(username);

        return optionalUser.isPresent() &&
                passwordHash.equalsIgnoreCase(optionalUser.get().getPassword());
    }
}

package com.ecommerce.user.service.impl;

import com.ecommerce.user.convertor.UserMapper;
import com.ecommerce.user.dto.SignUpRequest;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signUpUser(SignUpRequest signUpRequest) {
        LOGGER.info("UserServiceImpl | signUpUser is started");

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return "Username already exists";
        }

        User user = UserMapper.signUpRequestToUser(signUpRequest);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        LOGGER.info("UserServiceImpl | signUpUser | User saved to MySQL");

        return "Sign Up completed";
    }
}

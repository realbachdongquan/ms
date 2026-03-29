package com.ecommerce.user.service;

import com.ecommerce.user.dto.SignUpRequest;

public interface UserService {
    public String signUpUser(SignUpRequest signUpRequest);
}

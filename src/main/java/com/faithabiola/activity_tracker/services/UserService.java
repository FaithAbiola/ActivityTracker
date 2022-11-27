package com.faithabiola.activity_tracker.services;

import com.faithabiola.activity_tracker.dto.UserSignUpDto;
import com.faithabiola.activity_tracker.utils.ApiResponse;

public interface UserService {
    ApiResponse saveUser(UserSignUpDto userSignUpDto);
    ApiResponse loginUser(String email, String password);
}

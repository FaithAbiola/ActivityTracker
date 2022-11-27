package com.faithabiola.activity_tracker.controllers;

import com.faithabiola.activity_tracker.dto.UserSignUpDto;
import com.faithabiola.activity_tracker.services.UserService;
import com.faithabiola.activity_tracker.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vi/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse signUp(@RequestBody UserSignUpDto userSignUpDto) {
        return userService.saveUser(userSignUpDto);
    }

    @GetMapping("/login")
    public ApiResponse login(@RequestBody UserSignUpDto userSignUpDto){
        return userService.loginUser(userSignUpDto.getEmail(), userSignUpDto.getPassword());
    }

}

package com.faithabiola.activity_tracker.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ResponseManager<T> {
    public ApiResponse<T> success(T data) {
        return new ApiResponse<>("Operation successful", true, data);
    }

    public ApiResponse<T> error(String errorMessage) {
        return new ApiResponse<>(errorMessage, false, null);
    }

}

package com.faithabiola.activity_tracker.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiResponse<T> {
    private String message;
    private boolean status;
    private T data;
}

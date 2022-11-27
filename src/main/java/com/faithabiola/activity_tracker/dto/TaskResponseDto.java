package com.faithabiola.activity_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskResponseDto {
    private String title;
    private String description;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Date completedAt;
    private String uuid;
}

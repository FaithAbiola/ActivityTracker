package com.faithabiola.activity_tracker.dto;

import com.faithabiola.activity_tracker.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskCreationDto {
    private String title;
    private String description;
    private String uuid;
    private User user;
}

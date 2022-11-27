package com.faithabiola.activity_tracker.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserSignUpDto {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;

}

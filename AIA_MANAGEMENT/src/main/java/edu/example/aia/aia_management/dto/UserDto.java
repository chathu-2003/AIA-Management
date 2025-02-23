package edu.example.aia.aia_management.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class UserDto {

    private int userId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}

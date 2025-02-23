package edu.example.aia.aia_management.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class User {

    private int userId;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}

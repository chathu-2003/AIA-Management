package edu.example.aia.aia_management.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Manager {

    private int managerId; // Ensure this is 'managerId'
    private String name;
    private String email;
    private String phone;

    public Manager(String name, String email, String phone, int managerId) {
    }
}

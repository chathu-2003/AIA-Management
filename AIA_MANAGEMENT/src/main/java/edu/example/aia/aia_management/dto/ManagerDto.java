package edu.example.aia.aia_management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ManagerDto {

    private int managerId; // Ensure this is 'managerId'
    private String name;
    private String email;
    private String phone;
}

package edu.example.aia.aia_management.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ManagerTm {

    private int managerId; // Ensure this is 'managerId'
    private String name;
    private String email;
    private String phone;
}

package edu.example.aia.aia_management.tm;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerTm {

    private int customerId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int managerId;
}

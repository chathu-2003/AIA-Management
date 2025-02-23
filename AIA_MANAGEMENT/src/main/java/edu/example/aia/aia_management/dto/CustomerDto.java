package edu.example.aia.aia_management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CustomerDto {

    private int customerId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int managerId;


    public String getCustomerName() {
        return name;
    }

    public void setCustomerName(String name) {
        this.name = name;
    }

    public String getCustomerEmail() {
        return email;
    }
}


package edu.example.aia.aia_management.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {

    private int customerId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int managerId;

    public Customer(String name, String address, String phone, String email, int managerId, int customerId) {
    }


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


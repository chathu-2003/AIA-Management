package edu.example.aia.aia_management.entity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDetails {

    private int paymentId;
    private int policyId;
    private String month;
    private String status;
}

package edu.example.aia.aia_management.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class PaymentDetailsDto {

    private int paymentId;
    private int policyId;
    private String month;
    private String status;


}

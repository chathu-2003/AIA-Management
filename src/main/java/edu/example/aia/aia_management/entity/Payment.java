package edu.example.aia.aia_management.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Payment {

    private int paymentId;
    private BigDecimal amount;
    private String paymentMethod;

 //   private PaymentDetailsDto paymentDetails;

}

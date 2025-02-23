package edu.example.aia.aia_management.dto;

import java.math.BigDecimal;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class PaymentDto {

    private int paymentId;
    private BigDecimal amount;
    private String paymentMethod;

 //   private PaymentDetailsDto paymentDetails;

}

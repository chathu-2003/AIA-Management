package edu.example.aia.aia_management.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SavePaymentDto {

    private ArrayList<PaymentDto> payment;
    private ArrayList<PaymentDetailsDto> paymentDetails;

}

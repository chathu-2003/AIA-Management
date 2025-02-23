package edu.example.aia.aia_management.entity;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SavePayment {

    private ArrayList<Payment> payment;
    private ArrayList<PaymentDetails> paymentDetails;

}

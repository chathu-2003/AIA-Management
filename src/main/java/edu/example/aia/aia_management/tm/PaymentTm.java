package edu.example.aia.aia_management.tm;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PaymentTm {
    private int paymentId;
//    private int policyId;
    private BigDecimal amount;
    private String paymentMethod;
}

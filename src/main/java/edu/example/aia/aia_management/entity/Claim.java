package edu.example.aia.aia_management.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Claim {

    private int claimId;
    private int policyId;
    private BigDecimal claimAmount;
    private Date claimDate;
}

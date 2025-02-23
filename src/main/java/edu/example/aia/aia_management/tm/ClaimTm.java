package edu.example.aia.aia_management.tm;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ClaimTm {

    private int claimId;
    private int policyId;
    private BigDecimal claimAmount;
    private Date claimDate;
}

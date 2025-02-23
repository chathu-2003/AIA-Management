package edu.example.aia.aia_management.dto;

import java.math.BigDecimal;
import java.sql.Date;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ClaimDto {

    private int claimId;
    private int policyId;
    private BigDecimal claimAmount;
    private Date claimDate;
}

package edu.example.aia.aia_management.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CommissionDTO {
    private int commissionId;
    private int agentId;
    private BigDecimal amount;
    private LocalDate date;
}

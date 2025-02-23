package edu.example.aia.aia_management.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Commission {
    private int commissionId;
    private int agentId;
    private BigDecimal amount;
    private LocalDate date;

    public Commission(int agentId, BigDecimal amount, LocalDate date, int commissionId) {
    }
}

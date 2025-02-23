package edu.example.aia.aia_management.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor

public class Policy {

   private int policyId;
   private String policyNumber;
   private Date startDate;
   private Date endDate;
   private BigDecimal premium;
   private String coverage;
   private int managerId;
   private int underWriterId;
}

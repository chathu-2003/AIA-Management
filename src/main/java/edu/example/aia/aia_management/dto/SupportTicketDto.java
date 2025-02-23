package edu.example.aia.aia_management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupportTicketDto {
    private int supportTicketId;
    private int customerId;
    private String issueDescription;
    private String status;
}

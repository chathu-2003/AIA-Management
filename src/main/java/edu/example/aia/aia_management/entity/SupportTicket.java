package edu.example.aia.aia_management.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupportTicket {
    private int supportTicketId;
    private int customerId;
    private String issueDescription;
    private String status;
}

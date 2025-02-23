package edu.example.aia.aia_management.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupportTicketTm {

    private int supportTicketId;
    private int customerId;
    private String issueDescription;
    private String status;

}

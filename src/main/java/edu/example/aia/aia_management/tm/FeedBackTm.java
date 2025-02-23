package edu.example.aia.aia_management.tm;


import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FeedBackTm {

    private int feedbackId;
    private int managerId;
    private Date feedbackDate;
    private String comment;
}

package edu.example.aia.aia_management.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FeedBack {

    private int feedbackId;
    private int managerId;
    private Date feedbackDate;
    private String comment;
}

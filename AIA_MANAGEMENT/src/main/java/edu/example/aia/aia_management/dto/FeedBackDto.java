package edu.example.aia.aia_management.dto;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class FeedBackDto {

    private int feedbackId;
    private int managerId;
    private Date feedbackDate;
    private String comment;
}

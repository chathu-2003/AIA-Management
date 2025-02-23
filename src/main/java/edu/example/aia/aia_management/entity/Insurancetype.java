package edu.example.aia.aia_management.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Insurancetype {

    private int typeId;  // Updated from type to typeId
    private int policyid;
    private String typename;
    private String description;
}

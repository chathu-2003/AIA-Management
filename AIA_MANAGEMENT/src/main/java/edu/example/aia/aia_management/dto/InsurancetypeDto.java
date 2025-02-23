package edu.example.aia.aia_management.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class InsurancetypeDto {

    private int typeId;  // Updated from type to typeId
    private int policyid;
    private String typename;
    private String description;
}

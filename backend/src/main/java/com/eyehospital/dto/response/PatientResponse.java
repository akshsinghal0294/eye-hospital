package com.eyehospital.dto.response;

import com.eyehospital.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    private String fatherOrHusbandName;

    private String mobile;

    private String address;

    private Gender gender;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
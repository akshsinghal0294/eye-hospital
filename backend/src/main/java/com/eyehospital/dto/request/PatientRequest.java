package com.eyehospital.dto.request;

import com.eyehospital.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    @NotBlank(message = "Patient name is required")
    private String name;

    private LocalDate dateOfBirth;

    private String fatherOrHusbandName;

    @NotBlank(message = "Mobile number is required")
    private String mobile;

    private String address;

    @NotNull(message = "Gender is required")
    private Gender gender;
}
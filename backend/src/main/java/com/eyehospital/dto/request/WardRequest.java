package com.eyehospital.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WardRequest {

    private Long visitId;

    private String wardNumber;

    private String bedNumber;

    private String admittingDoctor;

    private String admissionDiagnosis;

    private String vitalsOnAdmission;

    private String nursingNotes;

    private String surgeryPerformed;

    private LocalDate surgeryDate;

    private LocalDate dischargeDate;

    private String dischargeSummary;

    private String followUpInstructions;
}
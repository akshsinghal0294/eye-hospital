package com.eyehospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WardResponse {

    private Long id;

    private Long visitId;

    private String wardNumber;

    private String bedNumber;

    private LocalDateTime admissionDate;

    private String admittingDoctor;

    private String admissionDiagnosis;

    private String vitalsOnAdmission;

    private String nursingNotes;

    private String surgeryPerformed;

    private LocalDate surgeryDate;

    private LocalDate dischargeDate;

    private String dischargeSummary;

    private String followUpInstructions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<DailyProgressResponse> dailyProgressList;
}
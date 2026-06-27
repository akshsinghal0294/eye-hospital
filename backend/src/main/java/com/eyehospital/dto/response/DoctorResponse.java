package com.eyehospital.dto.response;

import com.eyehospital.enums.EyeAffected;
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
public class DoctorResponse {

    private Long id;

    private String opdNumber;


    private Long optometristRecordId;

    private LocalDateTime recordDate;

    private String anteriorSegment;

    private String posteriorSegment;

    private String photoPath;

    private String generalInvestigation;

    private String ophthalmicInvestigation;

    private String provisionalDiagnosis;

    private String finalDiagnosis;

    private EyeAffected eyeInvolved;

    private Boolean surgeryAdvised;

    private String surgeryType;

    private LocalDate surgeryDate;

    private String otNotes;

    private Boolean reviewRequired;

    private String reviewAfter;

    private LocalDate reviewDate;

    private String reviewNotes;

    private String createdBy;

    private LocalDateTime createdAt;

    private List<MedicineResponse> medicines;
}
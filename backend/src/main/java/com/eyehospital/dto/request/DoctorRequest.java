package com.eyehospital.dto.request;

import com.eyehospital.enums.EyeAffected;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {

    @NotNull(message = "Optometrist Record is required.")
    private Long optometristRecordId;

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

    @Valid
    private List<MedicineRequest> medicines;
}
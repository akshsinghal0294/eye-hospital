package com.eyehospital.dto.response;

import com.eyehospital.enums.VisitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardActivityResponse {

    private Long visitId;

    private String opdNumber;

    private Long patientId;

    private String patientName;

    private String mobile;

    private VisitStatus status;

    private LocalDate visitDate;

}
package com.eyehospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryResponse {

    private Long totalPatientsToday;

    private Long activeVisits;

    private Long pendingOpt;

    private Long pendingDoctor;

}
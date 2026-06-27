package com.eyehospital.dto.request;

import com.eyehospital.enums.PatientType;
import com.eyehospital.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Patient type is required")
    private PatientType patientType;

    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;

    private Boolean extendExistingVisit;

    private String createdBy;
}
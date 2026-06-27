package com.eyehospital.dto.response;

import com.eyehospital.enums.PatientType;
import com.eyehospital.enums.PaymentMode;
import com.eyehospital.enums.VisitStatus;
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
public class VisitResponse {

    private Long id;

    // private String visitCode;

    private Long patientId;

    private String patientName;

    private String mobile;

    private PatientType patientType;

    private String opdNumber;

    private PaymentMode paymentMode;

    private LocalDateTime visitDate;

    private LocalDate expiryDate;

    private VisitStatus status;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
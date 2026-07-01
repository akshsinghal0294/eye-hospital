package com.eyehospital.controller;

import com.eyehospital.dto.request.PatientRequest;
import com.eyehospital.dto.response.ApiResponse;
import com.eyehospital.dto.response.PatientResponse;
import com.eyehospital.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reception/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    /**
     * Register Patient
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> registerPatient(
            @Valid @RequestBody PatientRequest request) {

        PatientResponse response = patientService.registerPatient(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PatientResponse>builder()
                        .success(true)
                        .message("Patient registered successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Find Patient by Mobile
     */
    @GetMapping("/mobile/{mobile}")
    public ResponseEntity<ApiResponse<PatientResponse>> findByMobile(
            @PathVariable String mobile) {

        PatientResponse response = patientService.findByMobile(mobile);

        return ResponseEntity.ok(
                ApiResponse.<PatientResponse>builder()
                        .success(true)
                        .message("Patient retrieved successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Get Patient by Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(
            @PathVariable Long id) {

        PatientResponse response = patientService.getPatientById(id);

        return ResponseEntity.ok(
                ApiResponse.<PatientResponse>builder()
                        .success(true)
                        .message("Patient retrieved successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Get All Patients
     */
    @GetMapping
    public  ResponseEntity<ApiResponse<Page<PatientResponse>>> getAllPatients(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize,
         @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
     ) {

        Page<PatientResponse> response = patientService.getAllPatients(pageNumber, pageSize ,fromDate ,toDate);

        return ResponseEntity.ok(
                ApiResponse.<Page<PatientResponse>>builder()
                        .success(true)
                        .message("Patients retrieved successfully.")
                        .data(response)
                        .build());
    }

    /**
     * Update Patient
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request) {

        PatientResponse response = patientService.updatePatient(id, request);

        return ResponseEntity.ok(
                ApiResponse.<PatientResponse>builder()
                        .success(true)
                        .message("Patient updated successfully.")
                        .data(response)
                        .build());
    }
}
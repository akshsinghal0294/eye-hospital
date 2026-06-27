package com.eyehospital.controller;

import com.eyehospital.dto.request.DoctorRequest;
import com.eyehospital.dto.response.ApiResponse;
import com.eyehospital.dto.response.DoctorResponse;
import com.eyehospital.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    /**
     * Add Doctor Record
     */
    @PostMapping("/records")
    public ResponseEntity<ApiResponse<DoctorResponse>> addRecord(
            @Valid @RequestBody DoctorRequest request) {

        DoctorResponse response = doctorService.addRecord(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<DoctorResponse>builder()
                                .success(true)
                                .message("Doctor record saved successfully.")
                                .data(response)
                                .build()
                );
    }

    /**
     * Get Doctor Record by Id
     */
    @GetMapping("/records/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getRecordById(
            @PathVariable Long id) {

        DoctorResponse response =
                doctorService.getRecordById(id);

        return ResponseEntity.ok(
                ApiResponse.<DoctorResponse>builder()
                        .success(true)
                        .message("Doctor record retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Get all Doctor Records of a Visit
     */
    @GetMapping("/records/visit/{visitId}")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getRecordsByVisit(
            @PathVariable Long visitId) {

        List<DoctorResponse> response =
                doctorService.getRecordsByVisit(visitId);

        return ResponseEntity.ok(
                ApiResponse.<List<DoctorResponse>>builder()
                        .success(true)
                        .message("Doctor records retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Get Latest Doctor Record by OPD Number
     */
    @GetMapping("/opd/{opdNumber}")
    public ResponseEntity<ApiResponse<DoctorResponse>> getLatestRecordByOpdNumber(
            @PathVariable String opdNumber) {

        DoctorResponse response =
                doctorService.getLatestRecordByOpdNumber(opdNumber);

        return ResponseEntity.ok(
                ApiResponse.<DoctorResponse>builder()
                        .success(true)
                        .message("Latest Doctor record retrieved successfully.")
                        .data(response)
                        .build()
        );
    }
}
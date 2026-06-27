
package com.eyehospital.controller;

import com.eyehospital.dto.request.VisitRequest;
import com.eyehospital.dto.response.ApiResponse;
import com.eyehospital.dto.response.VisitResponse;
import com.eyehospital.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reception/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    /**
     * Main Reception Flow
     *
     * This is the ONLY endpoint used by Reception
     * for creating or reusing a Visit.
     *
     * Logic:
     * 1. Search patient by mobile
     * 2. Check active visit
     * 3. Return existing visit if ACTIVE/EXTENDED
     * 4. Throw VISIT_EXPIRED if expired
     * 5. Create new visit if none exists
     */
    @PostMapping("/process/{mobile}")
    public ResponseEntity<ApiResponse<VisitResponse>> processReceptionFlow(
            @PathVariable String mobile,
            @Valid @RequestBody VisitRequest request) {

        VisitResponse response =
                visitService.processReceptionFlow(mobile, request);

        return ResponseEntity.ok(
                ApiResponse.<VisitResponse>builder()
                        .success(true)
                        .message("Visit processed successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Search Visit by OPD Number
     */
    @GetMapping("/opd/{opdNumber}")
    public ResponseEntity<ApiResponse<VisitResponse>> getVisitByOpdNumber(
            @PathVariable String opdNumber) {

        VisitResponse response =
                visitService.getVisitByOpdNumber(opdNumber);

        return ResponseEntity.ok(
                ApiResponse.<VisitResponse>builder()
                        .success(true)
                        .message("Visit retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Get all Visits of a Patient
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<ApiResponse<List<VisitResponse>>> getVisitsByPatient(
            @PathVariable Long patientId) {

        List<VisitResponse> response =
                visitService.getVisitsByPatient(patientId);

        return ResponseEntity.ok(
                ApiResponse.<List<VisitResponse>>builder()
                        .success(true)
                        .message("Patient visit history retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Extend Existing Visit
     */
    @PutMapping("/extend/{visitId}")
    public ResponseEntity<ApiResponse<VisitResponse>> extendVisit(
            @PathVariable Long visitId) {

        VisitResponse response =
                visitService.extendVisit(visitId);

        return ResponseEntity.ok(
                ApiResponse.<VisitResponse>builder()
                        .success(true)
                        .message("Visit extended successfully.")
                        .data(response)
                        .build()
        );
    }
}


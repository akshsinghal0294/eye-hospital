package com.eyehospital.controller;

import com.eyehospital.dto.request.OptometristRequest;
import com.eyehospital.dto.response.ApiResponse;
import com.eyehospital.dto.response.OptometristResponse;
import com.eyehospital.service.OptometristService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/optometrist")
@RequiredArgsConstructor
public class OptometristController {

    private final OptometristService optometristService;

    /**
     * Add Optometrist Record
     */
    @PostMapping("/records")
    public ResponseEntity<ApiResponse<OptometristResponse>> addRecord(
            @Valid @RequestBody OptometristRequest request) {

        OptometristResponse response =
                optometristService.addRecord(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<OptometristResponse>builder()
                                .success(true)
                                .message("Optometrist record saved successfully.")
                                .data(response)
                                .build()
                );
    }

    /**
     * Get all Optometrist Records of a Visit
     */
    @GetMapping("/records/visit/{visitId}")
    public ResponseEntity<ApiResponse<List<OptometristResponse>>> getRecordsByVisit(
            @PathVariable Long visitId) {

        List<OptometristResponse> response =
                optometristService.getRecordsByVisit(visitId);

        return ResponseEntity.ok(
                ApiResponse.<List<OptometristResponse>>builder()
                        .success(true)
                        .message("Optometrist records retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Get Optometrist Record by Id
     */
    @GetMapping("/records/{id}")
    public ResponseEntity<ApiResponse<OptometristResponse>> getRecordById(
            @PathVariable Long id) {

        OptometristResponse response =
                optometristService.getRecordById(id);

        return ResponseEntity.ok(
                ApiResponse.<OptometristResponse>builder()
                        .success(true)
                        .message("Optometrist record retrieved successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Get Latest Optometrist Record by OPD Number
     */
    @GetMapping("/opd/{opdNumber}")
    public ResponseEntity<ApiResponse<OptometristResponse>> getLatestRecordByOpdNumber(
            @PathVariable String opdNumber) {

        OptometristResponse response =
                optometristService.getLatestRecordByOpdNumber(opdNumber);

        return ResponseEntity.ok(
                ApiResponse.<OptometristResponse>builder()
                        .success(true)
                        .message("Latest Optometrist record retrieved successfully.")
                        .data(response)
                        .build()
        );
    }
}
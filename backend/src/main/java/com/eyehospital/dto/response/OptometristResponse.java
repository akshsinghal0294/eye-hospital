package com.eyehospital.dto.response;

import com.eyehospital.enums.ColourVision;
import com.eyehospital.enums.EyeAffected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptometristResponse {

    private Long id;

   private String opdNumber;

    private LocalDateTime recordDate;

    private String chiefComplaint;

    private String complaintDuration;

    private EyeAffected eyeAffected;

    private String visualAcuityUnaidedRE;
    private String visualAcuityUnaidedLE;

    private String visualAcuityAidedRE;
    private String visualAcuityAidedLE;

    private String nearVision;

    private Double iopRE;
    private Double iopLE;

    private ColourVision colourVision;

    private Double sphereRE;
    private Double sphereLE;

    private Double cylinderRE;
    private Double cylinderLE;

    private Integer axisRE;
    private Integer axisLE;

    private Double addRE;
    private Double addLE;

    private String refractionPhotoPath;

    private String pgTestResult;

    private String pgReportPath;

    private String createdBy;

    private LocalDateTime createdAt;
}
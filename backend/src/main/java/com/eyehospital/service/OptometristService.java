package com.eyehospital.service;

import com.eyehospital.dto.request.OptometristRequest;
import com.eyehospital.dto.response.OptometristResponse;
import com.eyehospital.entity.OptometristRecord;
import com.eyehospital.entity.Visit;
import com.eyehospital.enums.VisitStatus;
import com.eyehospital.exception.BusinessException;
import com.eyehospital.exception.ResourceNotFoundException;
import com.eyehospital.repository.OptometristRecordRepository;
import com.eyehospital.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OptometristService {

    private final VisitRepository visitRepository;
    private final OptometristRecordRepository optometristRecordRepository;

    /**
     * Add a new Optometrist Record.
     */
    public OptometristResponse addRecord(OptometristRequest request) {

        Visit visit = visitRepository.findByOpdNumber(
                        request.getOpdNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with OPD Number: "
                                        + request.getOpdNumber()));

        if (visit.getStatus() != VisitStatus.ACTIVE
                && visit.getStatus() != VisitStatus.EXTENDED) {

            throw new BusinessException("Visit expired");
        }

        if (visit.getExpiryDate().isBefore(LocalDate.now())) {
            throw new BusinessException("Visit expired");
        }

        String currentUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        OptometristRecord record = OptometristRecord.builder()
                .visit(visit)
                .chiefComplaint(request.getChiefComplaint())
                .complaintDuration(request.getComplaintDuration())
                .eyeAffected(request.getEyeAffected())
                .visualAcuityUnaidedRE(request.getVisualAcuityUnaidedRE())
                .visualAcuityUnaidedLE(request.getVisualAcuityUnaidedLE())
                .visualAcuityAidedRE(request.getVisualAcuityAidedRE())
                .visualAcuityAidedLE(request.getVisualAcuityAidedLE())
                .nearVision(request.getNearVision())
                .iopRE(request.getIopRE())
                .iopLE(request.getIopLE())
                .colourVision(request.getColourVision())
                .sphereRE(request.getSphereRE())
                .sphereLE(request.getSphereLE())
                .cylinderRE(request.getCylinderRE())
                .cylinderLE(request.getCylinderLE())
                .axisRE(request.getAxisRE())
                .axisLE(request.getAxisLE())
                .addRE(request.getAddRE())
                .addLE(request.getAddLE())
                .refractionPhotoPath(request.getRefractionPhotoPath())
                .pgTestResult(request.getPgTestResult())
                .pgReportPath(request.getPgReportPath())
                .createdBy(currentUser)
                .build();

        record = optometristRecordRepository.save(record);

        return mapToResponse(record);
    }

    /**
     * Get all Optometrist Records for a Visit.
     */
    @Transactional(readOnly = true)
    public List<OptometristResponse> getRecordsByVisit(Long visitId) {

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with id: " + visitId));

        return optometristRecordRepository
                .findByVisitOrderByRecordDateDesc(visit)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get Optometrist Record by Id.
     */
    @Transactional(readOnly = true)
    public OptometristResponse getRecordById(Long id) {

        OptometristRecord record =
                optometristRecordRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "OPT record not found with id: " + id));

        return mapToResponse(record);
    }

        /**
     * Get Latest Optometrist Record by OPD Number.
     */
    @Transactional(readOnly = true)
    public OptometristResponse getLatestRecordByOpdNumber(
            String opdNumber) {

        Visit visit = visitRepository.findByOpdNumber(opdNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with OPD Number: "
                                        + opdNumber));

        OptometristRecord record = optometristRecordRepository
                .findTopByVisitOrderByRecordDateDesc(visit)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No Optometrist Record found for this Visit"));

        return mapToResponse(record);
    }

    /**
     * Entity -> Response DTO Mapper.
     */
    private OptometristResponse mapToResponse(
            OptometristRecord record) {

        return OptometristResponse.builder()
                .id(record.getId())
                .opdNumber(record.getVisit().getOpdNumber())
                .recordDate(record.getRecordDate())
                .chiefComplaint(record.getChiefComplaint())
                .complaintDuration(record.getComplaintDuration())
                .eyeAffected(record.getEyeAffected())
                .visualAcuityUnaidedRE(record.getVisualAcuityUnaidedRE())
                .visualAcuityUnaidedLE(record.getVisualAcuityUnaidedLE())
                .visualAcuityAidedRE(record.getVisualAcuityAidedRE())
                .visualAcuityAidedLE(record.getVisualAcuityAidedLE())
                .nearVision(record.getNearVision())
                .iopRE(record.getIopRE())
                .iopLE(record.getIopLE())
                .colourVision(record.getColourVision())
                .sphereRE(record.getSphereRE())
                .sphereLE(record.getSphereLE())
                .cylinderRE(record.getCylinderRE())
                .cylinderLE(record.getCylinderLE())
                .axisRE(record.getAxisRE())
                .axisLE(record.getAxisLE())
                .addRE(record.getAddRE())
                .addLE(record.getAddLE())
                .refractionPhotoPath(record.getRefractionPhotoPath())
                .pgTestResult(record.getPgTestResult())
                .pgReportPath(record.getPgReportPath())
                .createdBy(record.getCreatedBy())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
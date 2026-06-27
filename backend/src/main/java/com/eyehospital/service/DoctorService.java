package com.eyehospital.service;

import com.eyehospital.dto.request.DoctorRequest;
import com.eyehospital.dto.response.DoctorResponse;
import com.eyehospital.dto.response.MedicineResponse;
import com.eyehospital.entity.DoctorRecord;
import com.eyehospital.entity.Medicine;
import com.eyehospital.entity.OptometristRecord;
import com.eyehospital.entity.Visit;
import com.eyehospital.enums.VisitStatus;
import com.eyehospital.exception.BusinessException;
import com.eyehospital.exception.ResourceNotFoundException;
import com.eyehospital.repository.DoctorRecordRepository;
import com.eyehospital.repository.OptometristRecordRepository;
import com.eyehospital.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {

    private final VisitRepository visitRepository;
    private final DoctorRecordRepository doctorRecordRepository;
    private final OptometristRecordRepository optometristRecordRepository;

    /**
     * Add Doctor Record
     */
    public DoctorResponse addRecord(DoctorRequest request) {

        OptometristRecord optometristRecord =
                optometristRecordRepository.findById(
                                request.getOptometristRecordId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Optometrist Record not found"));

        Visit visit = optometristRecord.getVisit();

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

        DoctorRecord doctorRecord = DoctorRecord.builder()
                .visit(visit)
                .optometristRecord(optometristRecord)
                .anteriorSegment(request.getAnteriorSegment())
                .posteriorSegment(request.getPosteriorSegment())
                .photoPath(request.getPhotoPath())
                .generalInvestigation(request.getGeneralInvestigation())
                .ophthalmicInvestigation(request.getOphthalmicInvestigation())
                .provisionalDiagnosis(request.getProvisionalDiagnosis())
                .finalDiagnosis(request.getFinalDiagnosis())
                .eyeInvolved(request.getEyeInvolved())
                .surgeryAdvised(request.getSurgeryAdvised())
                .surgeryType(request.getSurgeryType())
                .surgeryDate(request.getSurgeryDate())
                .otNotes(request.getOtNotes())
                .reviewRequired(request.getReviewRequired())
                .reviewAfter(request.getReviewAfter())
                .reviewDate(request.getReviewDate())
                .reviewNotes(request.getReviewNotes())
                .createdBy(currentUser)
                .medicines(new ArrayList<>())
                .build();

        if (request.getMedicines() != null) {

            request.getMedicines().forEach(medicineRequest -> {

                Medicine medicine = Medicine.builder()
                        .doctorRecord(doctorRecord)
                        .drugName(medicineRequest.getDrugName())
                        .dose(medicineRequest.getDose())
                        .duration(medicineRequest.getDuration())
                        .route(medicineRequest.getRoute())
                        .build();

                doctorRecord.getMedicines().add(medicine);
            });
        }

        DoctorRecord savedDoctorRecord = doctorRecordRepository.save(doctorRecord);

        return mapToResponse(savedDoctorRecord);
    }

    /**
     * Get all Doctor Records for a Visit
     */
    @Transactional(readOnly = true)
    public List<DoctorResponse> getRecordsByVisit(Long visitId) {

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with id : " + visitId));

        return doctorRecordRepository
                .findByVisitOrderByRecordDateDesc(visit)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Get Doctor Record by Id
     */
    @Transactional(readOnly = true)
    public DoctorResponse getRecordById(Long id) {

        DoctorRecord doctorRecord = doctorRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor Record not found with id : " + id));

        return mapToResponse(doctorRecord);
    }

    /**
     * Get Latest Doctor Record by OPD Number
     */
    @Transactional(readOnly = true)
    public DoctorResponse getLatestRecordByOpdNumber(String opdNumber) {

        Visit visit = visitRepository.findByOpdNumber(opdNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with OPD Number: " + opdNumber));

        DoctorRecord doctorRecord = doctorRecordRepository
                .findTopByVisitOrderByRecordDateDesc(visit)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "No Doctor Record found for this Visit"));

        return mapToResponse(doctorRecord);
    }
        /**
     * Entity -> Response DTO Mapper.
     */
    private DoctorResponse mapToResponse(DoctorRecord doctorRecord) {

        return DoctorResponse.builder()
                .id(doctorRecord.getId())
                .opdNumber(doctorRecord.getVisit().getOpdNumber())
                .optometristRecordId(
                        doctorRecord.getOptometristRecord().getId())
                .recordDate(doctorRecord.getRecordDate())
                .anteriorSegment(doctorRecord.getAnteriorSegment())
                .posteriorSegment(doctorRecord.getPosteriorSegment())
                .photoPath(doctorRecord.getPhotoPath())
                .generalInvestigation(
                        doctorRecord.getGeneralInvestigation())
                .ophthalmicInvestigation(
                        doctorRecord.getOphthalmicInvestigation())
                .provisionalDiagnosis(
                        doctorRecord.getProvisionalDiagnosis())
                .finalDiagnosis(doctorRecord.getFinalDiagnosis())
                .eyeInvolved(doctorRecord.getEyeInvolved())
                .surgeryAdvised(doctorRecord.getSurgeryAdvised())
                .surgeryType(doctorRecord.getSurgeryType())
                .surgeryDate(doctorRecord.getSurgeryDate())
                .otNotes(doctorRecord.getOtNotes())
                .reviewRequired(doctorRecord.getReviewRequired())
                .reviewAfter(doctorRecord.getReviewAfter())
                .reviewDate(doctorRecord.getReviewDate())
                .reviewNotes(doctorRecord.getReviewNotes())
                .createdBy(doctorRecord.getCreatedBy())
                .createdAt(doctorRecord.getCreatedAt())
                .medicines(
                        doctorRecord.getMedicines()
                                .stream()
                                .map(this::mapMedicineResponse)
                                .toList())
                .build();
    }

    /**
     * Medicine Entity -> MedicineResponse DTO.
     */
    private MedicineResponse mapMedicineResponse(
            Medicine medicine) {

        return MedicineResponse.builder()
                .id(medicine.getId())
                .drugName(medicine.getDrugName())
                .dose(medicine.getDose())
                .duration(medicine.getDuration())
                .route(medicine.getRoute())
                .build();
    }
}
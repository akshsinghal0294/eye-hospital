
package com.eyehospital.service;

import com.eyehospital.dto.request.VisitRequest;
import com.eyehospital.dto.response.VisitResponse;
import com.eyehospital.entity.Patient;
import com.eyehospital.entity.Visit;
import com.eyehospital.enums.VisitStatus;
import com.eyehospital.exception.BusinessException;
import com.eyehospital.exception.ResourceNotFoundException;
import com.eyehospital.repository.PatientRepository;
import com.eyehospital.repository.VisitRepository;
// import com.eyehospital.util.OpdNumberGenerator;
// import com.eyehospital.util.VisitCodeGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final CodeGeneratorService codeGeneratorService;
    // private final VisitCodeGenerator visitCodeGenerator;
    // private final OpdNumberGenerator opdNumberGenerator;

    /**
     * Create a brand new Visit.
     */
    public VisitResponse createVisit(VisitRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + request.getPatientId()));

        String currentUser = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Visit visit = Visit.builder()
                .patient(patient)
                .opdNumber(codeGeneratorService.generateOpdNumber())
                .patientType(request.getPatientType())
                .paymentMode(request.getPaymentMode())
                .status(VisitStatus.ACTIVE)
                .expiryDate(LocalDate.now().plusDays(7))
                .createdBy(currentUser)
                .visitDate(LocalDate.now())
                .build();

        visit = visitRepository.save(visit);

        return mapToResponse(visit);
    }

    /**
     * Returns latest ACTIVE / EXTENDED / EXPIRED visit.
     * Returns null if patient has no visit.
     */
    @Transactional(readOnly = true)
    public VisitResponse checkActiveVisit(String mobile) {

        Patient patient = patientRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with mobile: " + mobile));

        Visit latestVisit = visitRepository
                .findTopByPatientOrderByVisitDateDesc(patient)
                .orElse(null);

        if (latestVisit == null) {
            return null;
        }

        if (latestVisit.getExpiryDate().isBefore(LocalDate.now())
                && latestVisit.getStatus() != VisitStatus.EXPIRED) {

            latestVisit.setStatus(VisitStatus.EXPIRED);
            latestVisit = visitRepository.save(latestVisit);
        }

        return mapToResponse(latestVisit);
    }

    /**
     * Extend existing visit for another 7 days.
     */
    public VisitResponse extendVisit(Long visitId) {

        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with id: " + visitId));

        visit.setExpiryDate(LocalDate.now().plusDays(7));
        visit.setStatus(VisitStatus.EXTENDED);

        visit = visitRepository.save(visit);

        return mapToResponse(visit);
    }


    /**
     * Find visit by Visit Code.
     */
    @Transactional(readOnly = true)
    public VisitResponse getVisitByOpdNumber(String opdNumber) {

        Visit visit = visitRepository.findByOpdNumber(opdNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Visit not found with  OPD Number: " + opdNumber));

        return mapToResponse(visit);
    }

    /**
     * Get all visits of a patient.
     */
    @Transactional(readOnly = true)
    public List<VisitResponse> getVisitsByPatient(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: " + patientId));

        return visitRepository.findByPatientOrderByVisitDateDesc(patient)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /**
     * Reception flow.
     *
     * 1. Search patient by mobile.
     * 2. Check latest visit.
     * 3. Return active visit if available.
     * 4. Throw VISIT_EXPIRED if latest visit is expired.
     * 5. Create new visit if no previous visit exists.
     */
    public VisitResponse processReceptionFlow(
            String mobile,
            VisitRequest request) {

        Patient patient = patientRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found"));

        // Validate request consistency
        if (!patient.getId().equals(request.getPatientId())) {
            throw new BusinessException(
                    "Patient ID does not match the provided mobile number");
        }

        VisitResponse visit = checkActiveVisit(mobile);

        if (visit == null) {
            return createVisit(request);
        }

        if (visit.getStatus() == VisitStatus.ACTIVE
                || visit.getStatus() == VisitStatus.EXTENDED) {

            return visit;
        }

        throw new BusinessException("VISIT_EXPIRED", visit);
    }


    public VisitResponse createNewVisit(
        String mobile,
        VisitRequest request) {

    Patient patient = patientRepository.findByMobile(mobile)
            .orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Patient not found with mobile: " + mobile));

    if (!patient.getId().equals(request.getPatientId())) {
        throw new BusinessException(
                "Patient ID does not match the provided mobile number");
    }

    return createVisit(request);
}



    /**
     * Entity → DTO Mapper.
     */
    private VisitResponse mapToResponse(Visit visit) {

        return VisitResponse.builder()
                .id(visit.getId())
                .patientId(visit.getPatient().getId())
                .patientName(visit.getPatient().getName())
                .mobile(visit.getPatient().getMobile())
                .patientType(visit.getPatientType())
                .opdNumber(visit.getOpdNumber())
                .paymentMode(visit.getPaymentMode())
                .visitDate(visit.getVisitDate())
                .expiryDate(visit.getExpiryDate())
                .status(visit.getStatus())
                .createdBy(visit.getCreatedBy())
                .createdAt(visit.getCreatedAt())
                .updatedAt(visit.getUpdatedAt())
                .build();
    }
}



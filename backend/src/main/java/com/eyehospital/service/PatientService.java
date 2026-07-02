
package com.eyehospital.service;

import com.eyehospital.dto.request.PatientRequest;
import com.eyehospital.dto.response.PatientResponse;
import com.eyehospital.entity.Patient;
import com.eyehospital.exception.BusinessException;
import com.eyehospital.exception.ResourceNotFoundException;
import com.eyehospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.eyehospital.specification.PatientSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientResponse registerPatient(PatientRequest request) {

        if (patientRepository.existsByMobile(request.getMobile())) {
            throw new BusinessException("Patient already registered");
        }

        Patient patient = Patient.builder()
                .name(request.getName())
                .dateOfBirth(request.getDateOfBirth())
                .fatherOrHusbandName(request.getFatherOrHusbandName())
                .mobile(request.getMobile())
                .address(request.getAddress())
                .gender(request.getGender())
                .build();

        patient = patientRepository.save(patient);

        return mapToResponse(patient);
    }

    @Transactional(readOnly = true)
    public PatientResponse findByMobile(String mobile) {

        Patient patient = patientRepository.findByMobile(mobile)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with mobile: " + mobile));

        return mapToResponse(patient);
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        return mapToResponse(patient);
    }

    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(int pageNumber, int pageSize ,String  mobile, LocalDate fromDate,
        LocalDate toDate) {

            LocalDateTime fromDateTime = null;
            LocalDateTime toDateTime = null;
            if (fromDate != null) {
                fromDateTime = fromDate.atStartOfDay();
            }
            
            if (toDate != null) {
                toDateTime = toDate.atTime(LocalTime.MAX);
            }
            

            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));


            Specification<Patient> specification =
        PatientSpecification.searchPatients(
                mobile,
                fromDateTime,
                toDateTime);

                Page<Patient> patientPage =
        patientRepository.findAll(specification, pageable);

         
         return patientPage.map(this::mapToResponse);
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id));

        if (!patient.getMobile().equals(request.getMobile())
                && patientRepository.existsByMobile(request.getMobile())) {

            throw new BusinessException("Mobile number already registered");
        }

        patient.setName(request.getName());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setFatherOrHusbandName(request.getFatherOrHusbandName());
        patient.setMobile(request.getMobile());
        patient.setAddress(request.getAddress());
        patient.setGender(request.getGender());

        patient = patientRepository.save(patient);

        return mapToResponse(patient);
    }

    private PatientResponse mapToResponse(Patient patient) {

        return PatientResponse.builder()
                .id(patient.getId())
                .name(patient.getName())
                .dateOfBirth(patient.getDateOfBirth())
                .fatherOrHusbandName(patient.getFatherOrHusbandName())
                .mobile(patient.getMobile())
                .address(patient.getAddress())
                .gender(patient.getGender())
                .createdAt(patient.getCreatedAt())
                .updatedAt(patient.getUpdatedAt())
                .build();
    }
}


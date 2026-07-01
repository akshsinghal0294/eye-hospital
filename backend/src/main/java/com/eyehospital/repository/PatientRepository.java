package com.eyehospital.repository;

import com.eyehospital.entity.Patient;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByMobile(String mobile);

    boolean existsByMobile(String mobile);

    Optional<Patient> findByIdAndMobile(Long id, String mobile);

    Page<Patient> findByCreatedAtBetweenOrderByCreatedAtDesc(
        LocalDateTime fromDate,
        LocalDateTime toDate,
        Pageable pageable);
}
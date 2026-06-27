package com.eyehospital.repository;

import com.eyehospital.entity.Patient;
import com.eyehospital.entity.Visit;
import com.eyehospital.enums.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    Optional<Visit> findByOpdNumber(String opdNumber);

    List<Visit> findByPatient(Patient patient);

    List<Visit> findByPatientOrderByVisitDateDesc(Patient patient);

    Optional<Visit> findTopByPatientOrderByVisitDateDesc(
            Patient patient);

    List<Visit> findByStatus(VisitStatus status);

    List<Visit> findByExpiryDateBefore(LocalDate date);

    boolean existsByOpdNumber(String opdNumber);

    long countByVisitDate(LocalDate visitDate);
}
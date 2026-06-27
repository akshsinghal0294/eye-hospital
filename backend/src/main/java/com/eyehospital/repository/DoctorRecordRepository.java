package com.eyehospital.repository;

import com.eyehospital.entity.DoctorRecord;
import com.eyehospital.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorRecordRepository extends JpaRepository<DoctorRecord, Long> {

    List<DoctorRecord> findByVisitOrderByRecordDateDesc(Visit visit);

    List<DoctorRecord> findBySurgeryAdvisedTrue();

    List<DoctorRecord> findByReviewRequiredTrue();

    List<DoctorRecord> findBySurgeryDate(LocalDate surgeryDate);

    long countByVisit(Visit visit);

    Optional<DoctorRecord> findTopByVisitOrderByRecordDateDesc(
        Visit visit);
}
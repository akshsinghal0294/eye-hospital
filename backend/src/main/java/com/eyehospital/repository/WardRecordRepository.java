package com.eyehospital.repository;

import com.eyehospital.entity.Visit;
import com.eyehospital.entity.WardRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WardRecordRepository extends JpaRepository<WardRecord, Long> {

    Optional<WardRecord> findByVisit(Visit visit);

    List<WardRecord> findByDischargeDateIsNull();

    List<WardRecord> findByDischargeDate(LocalDate dischargeDate);

    boolean existsByVisit(Visit visit);
}
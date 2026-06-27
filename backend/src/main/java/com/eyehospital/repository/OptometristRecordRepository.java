package com.eyehospital.repository;

import com.eyehospital.entity.OptometristRecord;
import com.eyehospital.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptometristRecordRepository extends JpaRepository<OptometristRecord, Long> {

    List<OptometristRecord> findByVisitOrderByRecordDateDesc(Visit visit);

    Optional<OptometristRecord> findTopByVisitOrderByRecordDateDesc(Visit visit);

    long countByVisit(Visit visit);

    
}
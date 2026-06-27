package com.eyehospital.repository;

import com.eyehospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByMobile(String mobile);

    boolean existsByMobile(String mobile);

    Optional<Patient> findByIdAndMobile(Long id, String mobile);
}
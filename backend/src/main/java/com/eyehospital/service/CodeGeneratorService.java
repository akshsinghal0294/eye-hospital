package com.eyehospital.service;

import com.eyehospital.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CodeGeneratorService {

    private final VisitRepository visitRepository;

    /**
     * Generates OPD Number in format:
     * OPD-YYYYMMDD-XXXX
     * Example: OPD-20260625-0001
     */
    public String generateOpdNumber() {

        LocalDate today = LocalDate.now();

        long todayCount = visitRepository.countByVisitDate(today);

        return String.format(
                "OPD-%s-%04d",
                today.format(DateTimeFormatter.BASIC_ISO_DATE),
                todayCount + 1
        );
    }
}
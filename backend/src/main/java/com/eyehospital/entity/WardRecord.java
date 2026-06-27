package com.eyehospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ward_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WardRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false, unique = true)
    private Visit visit;

    private String wardNumber;

    private String bedNumber;

    @CreationTimestamp
    private LocalDateTime admissionDate;

    private String admittingDoctor;

    private String admissionDiagnosis;

    @Column(columnDefinition = "TEXT")
    private String vitalsOnAdmission;

    @Column(columnDefinition = "TEXT")
    private String nursingNotes;

    private String surgeryPerformed;

    private LocalDate surgeryDate;

    private LocalDate dischargeDate;

    @Column(columnDefinition = "TEXT")
    private String dischargeSummary;

    @Column(columnDefinition = "TEXT")
    private String followUpInstructions;

    @JsonIgnore
    @OneToMany(mappedBy = "wardRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyProgress> dailyProgressList;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
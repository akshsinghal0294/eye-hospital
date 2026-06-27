package com.eyehospital.entity;

import com.eyehospital.enums.PatientType;
import com.eyehospital.enums.PaymentMode;
import com.eyehospital.enums.VisitStatus;
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
@Table(name = "visits")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(unique = true)
    // private String visitCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private PatientType patientType;

    @Column(unique = true)
    private String opdNumber;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @CreationTimestamp
    private LocalDateTime visitDate;

    private LocalDate expiryDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private VisitStatus status = VisitStatus.ACTIVE;

    private String createdBy;

    @JsonIgnore
    @OneToMany(mappedBy = "visit")
    private List<OptometristRecord> optometristRecords;

    @JsonIgnore
    @OneToMany(mappedBy = "visit")
    private List<DoctorRecord> doctorRecords;

    @JsonIgnore
    @OneToOne(mappedBy = "visit")
    private WardRecord wardRecord;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
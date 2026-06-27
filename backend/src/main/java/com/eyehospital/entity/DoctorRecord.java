package com.eyehospital.entity;

import com.eyehospital.enums.EyeAffected;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "doctor_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "optometrist_record_id")
    private OptometristRecord optometristRecord;

    @CreationTimestamp
    private LocalDateTime recordDate;

    @Column(columnDefinition = "TEXT")
    private String anteriorSegment;

    @Column(columnDefinition = "TEXT")
    private String posteriorSegment;

    private String photoPath;

    @Column(columnDefinition = "TEXT")
    private String generalInvestigation;

    @Column(columnDefinition = "TEXT")
    private String ophthalmicInvestigation;

    @Column(columnDefinition = "TEXT")
    private String provisionalDiagnosis;

    @Column(columnDefinition = "TEXT")
    private String finalDiagnosis;

    @Enumerated(EnumType.STRING)
    private EyeAffected eyeInvolved;

    private Boolean surgeryAdvised;

    private String surgeryType;

    private LocalDate surgeryDate;

    @Column(columnDefinition = "TEXT")
    private String otNotes;

    private Boolean reviewRequired;

    private String reviewAfter;

    private LocalDate reviewDate;

    @Column(columnDefinition = "TEXT")
    private String reviewNotes;

    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "doctorRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medicine> medicines;
}
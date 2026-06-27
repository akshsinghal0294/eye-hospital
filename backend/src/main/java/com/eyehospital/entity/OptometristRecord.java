package com.eyehospital.entity;

import com.eyehospital.enums.ColourVision;
import com.eyehospital.enums.EyeAffected;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "optometrist_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptometristRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @CreationTimestamp
    private LocalDateTime recordDate;

    private String chiefComplaint;

    private String complaintDuration;

    @Enumerated(EnumType.STRING)
    private EyeAffected eyeAffected;

    private String visualAcuityUnaidedRE;
    private String visualAcuityUnaidedLE;

    private String visualAcuityAidedRE;
    private String visualAcuityAidedLE;

    private String nearVision;

    private Double iopRE;
    private Double iopLE;

    @Enumerated(EnumType.STRING)
    private ColourVision colourVision;

    private Double sphereRE;
    private Double sphereLE;

    private Double cylinderRE;
    private Double cylinderLE;

    private Integer axisRE;
    private Integer axisLE;

    private Double addRE;
    private Double addLE;

    private String refractionPhotoPath;

    private String pgTestResult;

    private String pgReportPath;

    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "optometristRecord")
    private List<DoctorRecord> doctorRecords;
}
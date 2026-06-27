package com.eyehospital.entity;

import com.eyehospital.enums.MedicineRoute;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medicines")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_record_id", nullable = false)
    private DoctorRecord doctorRecord;

    private String drugName;

    private String dose;

    private String duration;

    @Enumerated(EnumType.STRING)
    private MedicineRoute route;
}
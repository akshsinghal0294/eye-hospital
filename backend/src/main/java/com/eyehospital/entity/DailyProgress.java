package com.eyehospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "daily_progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_record_id", nullable = false)
    private WardRecord wardRecord;

    @CreationTimestamp
    private LocalDateTime progressDate;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private String recordedBy;
}
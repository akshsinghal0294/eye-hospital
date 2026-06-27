package com.eyehospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyProgressResponse {

    private Long id;

    private LocalDateTime progressDate;

    private String notes;

    private String recordedBy;
}
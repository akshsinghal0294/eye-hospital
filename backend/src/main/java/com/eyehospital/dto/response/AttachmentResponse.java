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
public class AttachmentResponse {

    private Long id;

    private String fileName;

    private String filePath;

    private String fileType;

    private String uploadedBy;

    private Long referenceId;

    private String referenceType;

    private LocalDateTime uploadedAt;
}
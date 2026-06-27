package com.eyehospital.service;

import com.eyehospital.dto.response.ApiResponse;
import com.eyehospital.dto.response.AttachmentResponse;
import com.eyehospital.entity.Attachment;
import com.eyehospital.exception.BusinessException;
import com.eyehospital.exception.ResourceNotFoundException;
import com.eyehospital.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    public AttachmentResponse uploadFile(
            MultipartFile file,
            Long referenceId,
            String referenceType) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new BusinessException("File is required.");
        }

        String originalName = file.getOriginalFilename();

        String extension = originalName.substring(
                originalName.lastIndexOf('.') + 1)
                .toLowerCase();

        if (!List.of("jpg", "jpeg", "png", "pdf")
                .contains(extension)) {

            throw new BusinessException(
                    "Only jpg, jpeg, png and pdf files are allowed.");
        }

        String uniqueFileName =
                UUID.randomUUID() + "." + extension;

        Path uploadPath = Paths.get(uploadDirectory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path destination =
                uploadPath.resolve(uniqueFileName);

        file.transferTo(destination);

        String currentUser = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Attachment attachment = Attachment.builder()
                .fileName(uniqueFileName)
                .filePath(destination.toString())
                .fileType(file.getContentType())
                .referenceId(referenceId)
                .referenceType(referenceType)
                .uploadedBy(currentUser)
                .build();

        attachment = attachmentRepository.save(attachment);

        return mapToResponse(attachment);
    }

    @Transactional(readOnly = true)
    public List<AttachmentResponse> getAttachmentsByReference(
            Long referenceId,
            String referenceType) {

        return attachmentRepository
                .findByReferenceIdAndReferenceType(
                        referenceId,
                        referenceType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ApiResponse<String> deleteAttachment(Long id) {

        Attachment attachment =
                attachmentRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attachment not found"));

        try {
            Files.deleteIfExists(
                    Paths.get(attachment.getFilePath()));
        } catch (IOException ex) {
            throw new BusinessException(
                    "Unable to delete physical file.");
        }

        attachmentRepository.delete(attachment);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Attachment deleted successfully.")
                .data("Deleted")
                .build();
    }

    private AttachmentResponse mapToResponse(
            Attachment attachment) {

        return AttachmentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .filePath(attachment.getFilePath())
                .fileType(attachment.getFileType())
                .referenceId(attachment.getReferenceId())
                .referenceType(attachment.getReferenceType())
                .uploadedBy(attachment.getUploadedBy())
                .uploadedAt(attachment.getUploadedAt())
                .build();
    }
}
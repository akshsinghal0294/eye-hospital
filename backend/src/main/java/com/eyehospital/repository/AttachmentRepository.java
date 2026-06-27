package com.eyehospital.repository;

import com.eyehospital.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByReferenceId(Long referenceId);

    List<Attachment> findByReferenceType(String referenceType);

    List<Attachment> findByReferenceIdAndReferenceType(
            Long referenceId,
            String referenceType
    );

    List<Attachment> findByUploadedBy(String uploadedBy);
}
package com.apsi.modules.document.dto;

import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.domain.DocumentStatus;
import com.apsi.modules.file.dto.FileDTO;
import com.apsi.modules.user.domain.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DocumentDataDTO {

    private Long id;

    private String name;

    private String description;

    private String creator;

    private String documentVersion;

    private DocumentStatus status;

    private String author;

    private Long controllerId;

    private Long reviewerId;

    private Long approverId;

    private Long receiverId;

    private List<FileDTO> files;

    public DocumentDataDTO(DocumentData documentData) {
        User controller = documentData.getController();
        User reviewer = documentData.getReviewer();
        User approver = documentData.getApprover();
        User receiver = documentData.getReceiver();
        this.id = documentData.getDocument().getId();
        this.name = documentData.getDocument().getName();
        this.description = documentData.getDocument().getDescription();
        this.creator = documentData.getDocument().getAuthor().getFullName();
        this.documentVersion = documentData.getDocumentVersion();
        this.status = documentData.getStatus();
        this.author = documentData.getAuthor().getFullName();
        this.controllerId = controller != null ? controller.getId() : null;
        this.reviewerId = reviewer != null ? reviewer.getId() : null;
        this.approverId = approver != null ? approver.getId() : null;
        this.receiverId = receiver != null ? receiver.getId() : null;
        this.files = documentData.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
    }
}

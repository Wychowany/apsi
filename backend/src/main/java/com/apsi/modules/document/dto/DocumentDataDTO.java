package com.apsi.modules.document.dto;

import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.domain.DocumentStatus;
import com.apsi.modules.file.dto.FileDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.System.*;

@Data
public class DocumentDataDTO {

    private Long id;

    private String name;

    private String description;

    private String creator;

    private long dataId;

    private String documentVersion;

    private DocumentStatus status;

    private String author;

    private List<DocumentUserNameDTO> documentUsers;

    private List<FileDTO> files;

    //private LocalDateTime creationDate;

    private String creationDate;

    public DocumentDataDTO(DocumentData documentData) {
        this.id = documentData.getDocument().getId();
        this.name = documentData.getDocument().getName();
        this.description = documentData.getDocument().getDescription();
        this.creator = documentData.getDocument().getAuthor().getFullName();
        this.dataId = documentData.getId();
        this.documentVersion = documentData.getDocumentVersion();
        this.status = documentData.getStatus();
        this.author = documentData.getAuthor().getFullName();
        this.documentUsers = documentData.getDocument().getDocumentUsers().stream().map(DocumentUserNameDTO::new).collect(Collectors.toList());
        this.files = documentData.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //System.out.println(documentData.getCreationDate().toString());
        this.creationDate =documentData.getCreationDate().format(formatter);
    }
}

package com.apsi.modules.document.dto;

import com.apsi.modules.document.domain.Document;
import com.apsi.modules.documentAccess.domain.DocumentAccess;
import com.apsi.modules.documentAccess.domain.DocumentAccessType;
import lombok.Data;

@Data
public class DocumentDTO {

    private Long id;

    private String name;

    private String author;

    private Boolean isAuthor;

    private DocumentAccessType accessType;

    public DocumentDTO(Document document, boolean isAuthor) {
        this.id = document.getId();
        this.name = document.getName();
        this.author = document.getAuthor().getFullName();
        this.isAuthor = isAuthor;
    }

    public DocumentDTO(DocumentAccess documentAccess, boolean isAuthor) {
        Document document = documentAccess.getDocument();
        this.id = document.getId();
        this.name = document.getName();
        this.author = document.getAuthor().getFullName();
        this.isAuthor = isAuthor;
        this.accessType = documentAccess.getAccessType();
    }
}

package com.apsi.modules.document.dto;

import com.apsi.modules.document.domain.Document;
import lombok.Data;

@Data
public class DocumentDataDTO {

    private Long id;

    private String name;

    private String description;

    private String author;

    public DocumentDataDTO(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.description = document.getDescription();
        this.author = document.getAuthor().getName() + " " + document.getAuthor().getSurname();
    }
}

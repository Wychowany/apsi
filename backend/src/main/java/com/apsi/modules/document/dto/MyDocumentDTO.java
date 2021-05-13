package com.apsi.modules.document.dto;

import com.apsi.modules.document.domain.Document;
import lombok.Data;

@Data
public class MyDocumentDTO {

    private Long id;

    private String name;

    private String author;

    public MyDocumentDTO(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.author = document.getAuthor().getFullName();
    }
}

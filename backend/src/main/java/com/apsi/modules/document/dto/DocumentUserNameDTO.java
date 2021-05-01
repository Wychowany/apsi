package com.apsi.modules.document.dto;

import com.apsi.modules.document.domain.DocumentUser;
import lombok.Data;

@Data
public class DocumentUserNameDTO {

    private String role;

    private String fullName;

    DocumentUserNameDTO(DocumentUser documentUser) {
        this.role = documentUser.getDocumentRole().getName();
        this.fullName = documentUser.getUser().getFullName();
    }
}

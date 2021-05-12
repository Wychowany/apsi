package com.apsi.modules.documentAccess.dto;

import com.apsi.modules.documentAccess.domain.DocumentAccess;
import com.apsi.modules.documentAccess.domain.DocumentAccessType;
import lombok.Data;

@Data
public class DocumentAccessDTO {

    private Long userId;

    private String fullName;

    private DocumentAccessType accessType;

    public DocumentAccessDTO(DocumentAccess documentAccess) {
        this.userId = documentAccess.getUser().getId();
        this.fullName = documentAccess.getUser().getFullName();
        this.accessType = documentAccess.getAccessType();
    }
}

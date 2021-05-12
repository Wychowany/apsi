package com.apsi.modules.documentAccess.dto;

import com.apsi.modules.documentAccess.domain.DocumentAccessType;
import lombok.Data;

@Data
public class AddDocumentAccessDTO {

    private Long documentId;

    private Long userId;

    private DocumentAccessType accessType;
}

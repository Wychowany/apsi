package com.apsi.modules.documentRole.dto;

import com.apsi.modules.documentRole.domain.DocumentRoleAccessType;
import lombok.Data;

@Data
public class CreateDocumentRoleDTO {

    private String name;
    private DocumentRoleAccessType accesstype;
}

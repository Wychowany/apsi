package com.apsi.modules.documentRole.dto;

import com.apsi.modules.documentRole.domain.DocumentRoleAccessType;
import lombok.Data;

@Data
public class EditDocumentRoleDTO {

    private Long id;

    private String name;

    private DocumentRoleAccessType accessType;
}

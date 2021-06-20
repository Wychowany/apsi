package com.apsi.modules.documentRole.dto;

import com.apsi.modules.documentRole.domain.DocumentRole;
import com.apsi.modules.documentRole.domain.DocumentRoleAccessType;
import lombok.Data;

@Data
public class DocumentRoleDTO {

    private Long id;

    private String name;

    private DocumentRoleAccessType accesstype;

    public DocumentRoleDTO(DocumentRole documentRole) {
        this.id = documentRole.getId();
        this.name = documentRole.getName();
        this.accesstype = documentRole.getAccesstype();
    }
}

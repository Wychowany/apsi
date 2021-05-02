package com.apsi.modules.documentRole.dto;

import com.apsi.modules.documentRole.domain.DocumentRole;
import lombok.Data;

@Data
public class DocumentRoleDTO {

    private Long id;

    private String name;

    public DocumentRoleDTO(DocumentRole documentRole) {
        this.id = documentRole.getId();
        this.name = documentRole.getName();
    }
}

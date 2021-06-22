package com.apsi.modules.documentRole.dto;

import com.apsi.modules.documentRole.domain.DocumentRole;
import com.apsi.modules.documentRole.domain.DocumentRoleAccessType;
import lombok.Data;

@Data
public class DocumentRoleDTO {

    private Long id;

    private String name;

    private DocumentRoleAccessType accesstype;

    private Long doc_id;
    public DocumentRoleDTO(DocumentRole documentRole) {
        this.id = documentRole.getId();
        this.name = documentRole.getName();
        this.accesstype = documentRole.getAccesstype();
    }
    public DocumentRoleDTO(DocumentRole documentRole, Long doc_id) {
        this.id = documentRole.getId();
        this.name = documentRole.getName();
        this.accesstype = documentRole.getAccesstype();
        this.doc_id = doc_id;
    }

}

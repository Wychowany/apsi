package com.apsi.modules.document.dto;

import com.apsi.modules.file.dto.FileDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateDocumentDTO {

    private String name;

    private String description;

    private String documentVersion;

    private List<DocumentUserDTO> documentUsers;

    private List<FileDTO> files;
}

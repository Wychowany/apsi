package com.apsi.modules.document.dto;

import com.apsi.modules.file.dto.FileDTO;
import lombok.Data;

import java.util.List;

@Data
public class EditDocumentDTO {

    private Long id;

    private String documentVersion;

    private Long controllerId;

    private Long reviewerId;

    private Long approverId;

    private Long receiverId;

    private List<FileDTO> files;
}

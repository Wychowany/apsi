package com.apsi.modules.series.dto;

import com.apsi.modules.series.domain.SeriesDocument;
import lombok.Data;

@Data
public class SeriesDocumentDTO {

    private Long documentDataId;

    private Long documentId;

    private String version;

    public SeriesDocumentDTO(SeriesDocument seriesDocument) {
        this.documentDataId = seriesDocument.getDocumentData().getId();
        this.documentId = seriesDocument.getDocumentData().getDocument().getId();
        this.version = seriesDocument.getDocumentData().getDocumentVersion();
    }
}

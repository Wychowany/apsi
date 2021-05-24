package com.apsi.modules.series.dto;

import lombok.Data;

import java.util.List;

@Data
public class EditSeriesDTO {

    private Long id;

    private String seriesVersion;

    private List<CreateSeriesDocumentDTO> documents;
}

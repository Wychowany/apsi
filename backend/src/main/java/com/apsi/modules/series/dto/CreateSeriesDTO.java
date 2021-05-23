package com.apsi.modules.series.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateSeriesDTO {

    private String name;

    private String description;

    private String seriesVersion;

    private List<SeriesDocumentDTO> documents;
}

package com.apsi.modules.series.dto;

import com.apsi.modules.series.domain.SeriesData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class SeriesDataDTO {

    private Long id;

    private String name;

    private String description;

    private String creator;

    private long dataId;

    private String seriesVersion;

    private String author;

    private List<SeriesDocumentDTO> seriesDocuments;

    public SeriesDataDTO(SeriesData seriesData) {
        this.id = seriesData.getSeries().getId();
        this.name = seriesData.getSeries().getName();
        this.description = seriesData.getSeries().getDescription();
        this.creator = seriesData.getSeries().getAuthor().getFullName();
        this.dataId = seriesData.getId();
        this.seriesVersion = seriesData.getSeriesVersion();
        this.author = seriesData.getAuthor().getFullName();
        this.seriesDocuments = seriesData.getDocumentsInSeries().stream().map(SeriesDocumentDTO::new).collect(Collectors.toList());

    }
}

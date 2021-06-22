package com.apsi.modules.series.dto;

import com.apsi.modules.series.domain.SeriesData;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private String creationDate;

    public SeriesDataDTO(SeriesData seriesData) {
        this.id = seriesData.getSeries().getId();
        this.name = seriesData.getSeries().getName();
        this.description = seriesData.getSeries().getDescription();
        this.creator = seriesData.getSeries().getAuthor().getFullName();
        this.dataId = seriesData.getId();
        this.seriesVersion = seriesData.getSeriesVersion();
        this.author = seriesData.getAuthor().getFullName();
        this.seriesDocuments = seriesData.getDocuments().stream().map(SeriesDocumentDTO::new).collect(Collectors.toList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.creationDate = seriesData.getCreationDate().format(formatter);
    }
}

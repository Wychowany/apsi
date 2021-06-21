package com.apsi.modules.series.dto;

import com.apsi.modules.document.domain.Document;
import com.apsi.modules.seriesAccess.domain.SeriesAccess;
import com.apsi.modules.seriesAccess.domain.SeriesAccessType;
import com.apsi.modules.series.domain.Series;
import lombok.Data;

@Data
public class SeriesDTO {

    private Long id;

    private String name;

    private String author;

    private Boolean isAuthor;

    private SeriesAccessType accessType;

    public SeriesDTO(Series series) {
        this.id = series.getId();
        this.name = series.getName();
        this.author = series.getAuthor().getFullName();
    }

    public SeriesDTO(Series series, boolean isAuthor) {
        this.id = series.getId();
        this.name = series.getName();
        this.author = series.getAuthor().getFullName();
        this.isAuthor = isAuthor;
    }
    public SeriesDTO(SeriesAccess seriesAccess, boolean isAuthor) {
        Series series = seriesAccess.getSeries();
        this.id = series.getId();
        this.name = series.getName();
        this.author = series.getAuthor().getFullName();
        this.accessType = seriesAccess.getAccessType();
    }



}

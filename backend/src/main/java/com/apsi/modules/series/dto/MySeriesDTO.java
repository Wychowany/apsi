package com.apsi.modules.series.dto;

import com.apsi.modules.series.domain.Series;
import lombok.Data;

@Data
public class MySeriesDTO {

    private Long id;

    private String name;

    private String author;

    public MySeriesDTO(Series series) {
        this.id = series.getId();
        this.name = series.getName();
        this.author = series.getAuthor().getFullName();
    }
}

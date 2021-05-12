package com.apsi.modules.series.dto;

import com.apsi.modules.series.domain.Series;
import lombok.Data;

@Data
public class SeriesDTO {

    private Long id;

    private String name;

    public SeriesDTO(Series series) {
        this.id = series.getId();
        this.name = series.getName();
    }
}

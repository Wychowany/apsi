package com.apsi.modules.seriesAccess.dto;
import com.apsi.modules.documentAccess.domain.DocumentAccessType;
import com.apsi.modules.seriesAccess.domain.SeriesAccessType;
import lombok.Data;

@Data
public class AddSeriesAccessDTO {
    private Long seriesId;

    private Long userId;

    private SeriesAccessType accessType;

}

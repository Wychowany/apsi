package com.apsi.modules.seriesAccess.dto;

import com.apsi.modules.documentAccess.domain.DocumentAccess;
import com.apsi.modules.documentAccess.domain.DocumentAccessType;
import com.apsi.modules.seriesAccess.domain.SeriesAccess;
import com.apsi.modules.seriesAccess.domain.SeriesAccessType;
import lombok.Data;

@Data
public class SeriesAccessDTO {
    private Long userId;

    private String fullName;

    private SeriesAccessType accessType;

    public SeriesAccessDTO(SeriesAccess seriesAccess) {
        this.userId = seriesAccess.getUser().getId();
        this.fullName = seriesAccess.getUser().getFullName();
        this.accessType = seriesAccess.getAccessType();
    }

}

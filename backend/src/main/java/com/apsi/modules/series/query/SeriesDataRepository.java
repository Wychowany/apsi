package com.apsi.modules.series.query;

import com.apsi.generic.AbstractEntityRepository;
import com.apsi.modules.series.domain.SeriesData;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeriesDataRepository extends AbstractEntityRepository<SeriesData, Long> {

    @Query("SELECT s.seriesVersion AS version FROM SeriesData s WHERE s.series.id = :seriesId")
    List<SeriesVersionProjection> findVersionsBySeriesId(Long seriesId);
}

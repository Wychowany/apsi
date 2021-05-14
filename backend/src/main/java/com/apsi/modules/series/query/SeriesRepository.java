package com.apsi.modules.series.query;

import com.apsi.generic.AbstractEntityRepository;
import com.apsi.modules.series.domain.Series;

import java.util.List;

public interface SeriesRepository extends AbstractEntityRepository<Series, Long> {

    List<Series> findAllByAuthorId(Long authorId);
}

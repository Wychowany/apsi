package com.apsi.modules.seriesAccess.query;

import com.apsi.modules.seriesAccess.domain.SeriesAccess;
import com.apsi.modules.seriesAccess.domain.SeriesAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesAccessRepository extends JpaRepository<SeriesAccess, SeriesAccess.SeriesAccessId> {
    List<SeriesAccess> findAllBySeriesId(Long seriesId);

    List<SeriesAccess> findAllByUserIdAndSeriesAuthorIdIsNot(long userId, long authorId);

    void deleteBySeriesId(Long id);

    void deleteByUserIdAndSeriesId(Long userId, Long seriesId);

    Optional<SeriesAccess> findBySeriesIdAndUserId(Long seriesId, Long userId);


}


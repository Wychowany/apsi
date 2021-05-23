package com.apsi.modules.series.domain;

import com.apsi.generic.AbstractIdEntity;
import com.apsi.modules.document.domain.Document;
import com.apsi.modules.document.domain.DocumentStatus;
import com.apsi.modules.file.domain.DatabaseFile;
import com.apsi.modules.user.domain.User;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "series_data", uniqueConstraints = @UniqueConstraint(columnNames = {"series_id", "seriesVersion"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class SeriesData extends AbstractIdEntity {

    public SeriesData(Series series, String seriesVersion, User author) {
        series.setSeriesDataList(new ArrayList<>());
        series.getSeriesDataList().add(this);
        this.series = series;
        this.seriesVersion = seriesVersion;
        this.author = author;
    }

    private String seriesVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("creationDate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SeriesDocument> documentsInSeries;
}
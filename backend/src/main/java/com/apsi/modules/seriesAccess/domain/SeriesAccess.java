package com.apsi.modules.seriesAccess.domain;
import com.apsi.modules.document.domain.Document;
import com.apsi.modules.series.domain.Series;
import com.apsi.modules.documentAccess.domain.DocumentAccess;
import com.apsi.modules.documentAccess.domain.DocumentAccessType;
import com.apsi.modules.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "series_accesses", uniqueConstraints = @UniqueConstraint(columnNames = {"series_id", "user_id"}))
public class SeriesAccess {

    @EmbeddedId
    public SeriesAccess.SeriesAccessId id = new SeriesAccess.SeriesAccessId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("seriesId")
    @JoinColumn(name = "series_id", insertable = false, updatable = false)
    private Series series;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false, columnDefinition = "VARCHAR(64) DEFAULT 'READ'")
    private SeriesAccessType accessType;

    @Embeddable
    @EqualsAndHashCode
    @Getter
    @Setter
    @NoArgsConstructor
    static public class SeriesAccessId implements Serializable {

        @Column(name = "series_id")
        private long seriesId;

        @Column(name = "user_id")
        private long userId;

        public SeriesAccessId(Long seriesId, Long userId) {
            this.seriesId = seriesId;
            this.userId = userId;
        }
    }
}

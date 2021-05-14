package com.apsi.modules.series.domain;

import com.apsi.generic.AbstractIdEntity;
import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.domain.DocumentUser;
import com.apsi.modules.user.domain.User;
import lombok.*;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "series")
@Inheritance(strategy = InheritanceType.JOINED)
public class Series extends AbstractIdEntity {

    @Column(nullable = false)
    private String name;

    @Lob
    @Type(type = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "series")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<SeriesData> seriesDataList = new ArrayList<>();
}

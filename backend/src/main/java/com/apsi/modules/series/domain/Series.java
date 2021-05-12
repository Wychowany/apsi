package com.apsi.modules.series.domain;

import com.apsi.generic.AbstractIdEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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
}

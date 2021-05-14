package com.apsi.modules.series.domain;

import com.apsi.generic.AbstractIdEntity;
import com.apsi.modules.document.domain.Document;
import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.dto.DocumentUserDTO;
import com.apsi.modules.documentRole.domain.DocumentRole;
import com.apsi.modules.user.domain.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "series_documents", uniqueConstraints = @UniqueConstraint(columnNames = {"document_data_id", "series_data_id"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class SeriesDocument extends AbstractIdEntity {

    @ManyToOne
    @JoinColumn(name = "document_data_id")
    private DocumentData documentData;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "series_data_id")
    private SeriesData seriesData;
}

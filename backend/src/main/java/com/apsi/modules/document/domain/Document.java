package com.apsi.modules.document.domain;

import com.apsi.generic.AbstractIdEntity;
import com.apsi.modules.user.domain.User;
import lombok.*;
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
@Table(name = "documents")
@Inheritance(strategy = InheritanceType.JOINED)
public class Document extends AbstractIdEntity {

    @Column(nullable = false)
    private String name;

    @Lob
    @Type(type = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "document")
    private List<DocumentData> documentDataList = new ArrayList<>();
}

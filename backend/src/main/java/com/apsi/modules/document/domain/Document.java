package com.apsi.modules.document.domain;

import com.apsi.generic.AbstractIdEntity;
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
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DocumentUser> documentUsers = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "document")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DocumentData> documentDataList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinFormula("(" +
            "SELECT dd.id " +
            "FROM documents_data dd " +
            "WHERE dd.document_id = id " +
            "ORDER BY dd.creation_date " +
            "DESC LIMIT 1)")
    private DocumentData documentData;
}

package com.apsi.modules.document.domain;

import com.apsi.generic.AbstractIdEntity;
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
@Table(name = "documents_data", uniqueConstraints = @UniqueConstraint(columnNames = {"document_id", "documentVersion"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class DocumentData extends AbstractIdEntity {

    public DocumentData(Document document, String documentVersion, List<DatabaseFile> files, User author) {
        document.setDocumentDataList(new ArrayList<>());
        document.getDocumentDataList().add(this);
        this.document = document;
        this.status = DocumentStatus.NEW;
        this.documentVersion = documentVersion;
        this.author = author;
        this.files = files;
    }

    private String documentVersion;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false, columnDefinition = "VARCHAR(64) DEFAULT 'NEW'")
    private DocumentStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("creationDate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DatabaseFile> files;
}

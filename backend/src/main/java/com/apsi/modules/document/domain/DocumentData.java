package com.apsi.modules.document.domain;

import com.apsi.generic.AbstractIdEntity;
import com.apsi.modules.document.dto.CreateDocumentDTO;
import com.apsi.modules.file.domain.DatabaseFile;
import com.apsi.modules.user.domain.User;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.beans.factory.annotation.Autowired;

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

    public DocumentData(Document document, String documentVersion, List<DatabaseFile> files,
                        User author, User controller, User reviewer, User approver, User receiver) {
        document.setDocumentDataList(new ArrayList<>());
        document.getDocumentDataList().add(this);
        this.document = document;
        this.status = DocumentStatus.NEW;
        this.documentVersion = documentVersion;
        this.author = author;
        this.controller = controller;
        this.reviewer = reviewer;
        this.approver = approver;
        this.receiver = receiver;
        this.files = files;
    }

    private String documentVersion;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false, columnDefinition = "VARCHAR(64) DEFAULT 'NEW'")
    private DocumentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "controller_id")
    private User controller;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approver_id")
    private User approver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("creationDate")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DatabaseFile> files;
}

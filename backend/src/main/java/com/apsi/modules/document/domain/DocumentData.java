package com.apsi.modules.document.domain;

import com.apsi.generic.AbstractIdEntity;
import com.apsi.modules.file.domain.DatabaseFile;
import com.apsi.modules.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "documents_data", uniqueConstraints = @UniqueConstraint(columnNames = {"document_id", "documentVersion"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class DocumentData extends AbstractIdEntity {

    public DocumentData() {
        this.documentVersion = "1.0";
        this.status = DocumentStatus.NEW;
    }

    public DocumentData(Document document) {
        document.getDocumentDataList().add(this);
        this.document = document;
    }

    private String documentVersion;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false, columnDefinition = "VARCHAR(64) DEFAULT 'NEW'")
    private DocumentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "controller_id")
    private User controller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private User approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @OneToMany
    @OrderBy("creationDate")
    private List<DatabaseFile> files;
}

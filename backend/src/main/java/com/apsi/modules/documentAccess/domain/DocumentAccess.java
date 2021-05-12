package com.apsi.modules.documentAccess.domain;

import com.apsi.modules.document.domain.Document;
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
@Table(name = "document_accesses", uniqueConstraints = @UniqueConstraint(columnNames = {"document_id", "user_id"}))
public class DocumentAccess {

    @EmbeddedId
    public DocumentAccessId id = new DocumentAccessId();

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("documentId")
    @JoinColumn(name = "document_id", insertable = false, updatable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false, columnDefinition = "VARCHAR(64) DEFAULT 'READ'")
    private DocumentAccessType accessType;

    @Embeddable
    @EqualsAndHashCode
    @Getter
    @Setter
    @NoArgsConstructor
    static public class DocumentAccessId implements Serializable {

        @Column(name = "document_id")
        private long documentId;

        @Column(name = "user_id")
        private long userId;

        public DocumentAccessId(Long documentId, Long userId) {
            this.documentId = documentId;
            this.userId = userId;
        }
    }
}

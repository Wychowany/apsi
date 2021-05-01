package com.apsi.modules.document.domain;

import com.apsi.generic.AbstractIdEntity;
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
@Table(name = "document_users", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "document_id", "document_role_id"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class DocumentUser extends AbstractIdEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "document_role_id")
    private DocumentRole documentRole;
}

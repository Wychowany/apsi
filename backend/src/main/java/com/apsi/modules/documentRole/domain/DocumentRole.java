package com.apsi.modules.documentRole.domain;

import com.apsi.generic.AbstractIdEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "document_roles")
@Inheritance(strategy = InheritanceType.JOINED)
public class DocumentRole extends AbstractIdEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 64, nullable = false, columnDefinition = "VARCHAR(64) DEFAULT 'READ'")
    private DocumentRoleAccessType accesstype;
}

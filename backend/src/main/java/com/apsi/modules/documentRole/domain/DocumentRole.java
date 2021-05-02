package com.apsi.modules.documentRole.domain;

import com.apsi.generic.AbstractIdEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

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
}

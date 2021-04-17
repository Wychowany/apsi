package com.apsi.modules.file.domain;

import com.apsi.generic.AbstractIdEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseFile extends AbstractIdEntity {

    private String name;

    private String format;

    private long length;

    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] bytes;
}

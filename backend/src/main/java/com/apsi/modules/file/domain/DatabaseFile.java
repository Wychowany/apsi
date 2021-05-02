package com.apsi.modules.file.domain;

import com.apsi.generic.AbstractIdEntity;
import com.apsi.modules.file.dto.FileDTO;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "database_files")
public class DatabaseFile extends AbstractIdEntity {

    private String name;

    private String format;

    private long length;

    @Lob
    @Type(type = "text")
    private String encodedData;

    public DatabaseFile(FileDTO fileDTO) {
        this.name = fileDTO.getName();
        this.format = fileDTO.getFormat();
        this.length = fileDTO.getLength();
        this.encodedData = fileDTO.getEncodedData();
    }
}

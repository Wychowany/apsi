package com.apsi.modules.file.dto;

import com.apsi.modules.file.domain.DatabaseFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = {"encodedData"})
public class FileDTO {

    private Long id;

    private String name;

    private String format;

    private long length;

    private String encodedData;

    public FileDTO(DatabaseFile databaseFile) {
        this.id = databaseFile.getId();
        this.name = databaseFile.getName();
        this.format = databaseFile.getFormat();
        this.length = databaseFile.getLength();
        this.encodedData = databaseFile.getEncodedData();
    }
}

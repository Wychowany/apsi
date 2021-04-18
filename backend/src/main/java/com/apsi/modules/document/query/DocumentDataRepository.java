package com.apsi.modules.document.query;

import com.apsi.generic.AbstractEntityRepository;
import com.apsi.modules.document.domain.DocumentData;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DocumentDataRepository extends AbstractEntityRepository<DocumentData, Long> {

    @Query("SELECT dd.documentVersion AS version FROM DocumentData dd WHERE dd.document.id = :documentId")
    List<DocumentVersionProjection> findVersionsByDocumentId(Long documentId);
}

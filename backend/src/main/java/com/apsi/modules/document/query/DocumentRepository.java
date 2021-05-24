package com.apsi.modules.document.query;

import com.apsi.generic.AbstractEntityRepository;
import com.apsi.modules.document.domain.Document;

import java.util.List;

public interface DocumentRepository extends AbstractEntityRepository<Document, Long> {

    List<Document> findAllByAuthorId(Long authorId);

    boolean existsByIdAndAuthorId(Long id, Long authorId);
}

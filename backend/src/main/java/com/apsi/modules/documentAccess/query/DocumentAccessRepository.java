package com.apsi.modules.documentAccess.query;

import com.apsi.modules.documentAccess.domain.DocumentAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentAccessRepository extends JpaRepository<DocumentAccess, DocumentAccess.DocumentAccessId> {

    List<DocumentAccess> findAllByDocumentId(Long documentId);

    void deleteByUserIdAndDocumentId(Long userId, Long documentId);
}

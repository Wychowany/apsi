package com.apsi.modules.documentAccess.query;

import com.apsi.modules.documentAccess.domain.DocumentAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentAccessRepository extends JpaRepository<DocumentAccess, DocumentAccess.DocumentAccessId> {

    List<DocumentAccess> findAllByDocumentId(Long documentId);

    List<DocumentAccess> findAllByUserIdAndDocumentAuthorIdIsNot(long userId, long authorId);

    void deleteByUserIdAndDocumentId(Long userId, Long documentId);

    Optional<DocumentAccess> findByDocumentIdAndUserId(Long documentId, Long userId);
}

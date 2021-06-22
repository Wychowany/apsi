package com.apsi.modules.document.query;

import com.apsi.generic.AbstractEntityRepository;
import com.apsi.modules.document.domain.DocumentUser;

import java.util.List;

public interface DocumentUserRepository extends AbstractEntityRepository<DocumentUser, Long> {

    List<DocumentUser> findAllByUserIdAndDocumentAuthorIdIsNot(long userId, long authorId);

    List<DocumentUser> findAllByDocumentIdAndUserId(long documentId, long userId);
}

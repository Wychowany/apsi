package com.apsi.modules.document;

import com.apsi.global.IdResponse;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.modules.document.domain.Document;
import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.domain.DocumentUser;
import com.apsi.modules.document.dto.*;
import com.apsi.modules.document.query.DocumentDataRepository;
import com.apsi.modules.document.query.DocumentRepository;
import com.apsi.modules.documentAccess.domain.DocumentAccess;
import com.apsi.modules.documentAccess.query.DocumentAccessRepository;
import com.apsi.modules.documentRole.domain.DocumentRole;
import com.apsi.modules.documentRole.query.DocumentRoleRepository;
import com.apsi.modules.file.domain.DatabaseFile;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.query.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor
public class DocumentController {

    @Autowired
    private final Identity identity;

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final DocumentRoleRepository documentRoleRepository;

    @Autowired
    private final DocumentDataRepository documentDataRepository;

    @Autowired
    private final DocumentAccessRepository documentAccessRepository;

    @Autowired
    private final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(DocumentController.class);

    @GetMapping("/list")
    public ResponseEntity<?> getDocuments() {
        List<Document> documents = documentRepository.findAllByAuthorId(identity.getRawId());
        List<DocumentAccess> accesses = documentAccessRepository.findAllByUserIdAndDocumentAuthorIdIsNot(identity.getRawId(), identity.getRawId());
        return ResponseEntity.ok(
                    Stream.concat(
                        documents.stream().map(document -> new DocumentDTO(document, true)),
                        accesses.stream().map(access -> new DocumentDTO(access, false))
                ).collect(Collectors.toList()));
    }

    @GetMapping("/my-list")
    public ResponseEntity<?> getMyDocuments() {
        List<Document> documents = documentRepository.findAllByAuthorId(identity.getRawId());
        List<MyDocumentDTO> response = documents.stream().map(MyDocumentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getDocument(@RequestParam Long id, @RequestParam(required = false) String version) {
        Document document = documentRepository.findById(id).orElseThrow();
        DocumentData documentData = version == null ? document.getDocumentData() : getDocumentVersionData(document, version);
        DocumentDataDTO response = new DocumentDataDTO(documentData);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/versions")
    public ResponseEntity<?> getDocumentVersions(@RequestParam Long id) {
        return ResponseEntity.ok(documentDataRepository.findVersionsByDocumentId(id));
    }

    @PostMapping
    public ResponseEntity<?> createDocument(@RequestBody CreateDocumentDTO createDocumentDTO) {
        logger.info("User with id {} requested new document creation with data: {}", identity.getRawId(), createDocumentDTO.toString());
        User author = userRepository.findById(identity.getRawId()).orElseThrow();
        Document document = Document.builder()
                .name(createDocumentDTO.getName())
                .description(createDocumentDTO.getDescription())
                .author(author)
                .build();
        document.setDocumentUsers(prepareDocumentUserList(document, createDocumentDTO.getDocumentUsers()));
        document.setDocumentDataList(prepareNewDocumentDataList(document, createDocumentDTO, author));
        Document savedDocument = documentRepository.save(document);
        return ResponseEntity.ok(new IdResponse(savedDocument.getId()));
    }

    @PutMapping
    public ResponseEntity<?> editDocument(@RequestBody EditDocumentDTO editDocumentDTO) {
        logger.info("User with id {} created new document version with data: {}", identity.getRawId(), editDocumentDTO.toString());
        Document document = documentRepository.findById(editDocumentDTO.getId()).orElseThrow();
        User author = userRepository.findById(identity.getRawId()).orElseThrow();
        document.getDocumentDataList().add(prepareDocumentDataListExtension(document, editDocumentDTO, author));
        documentRepository.save(document);
        return ResponseEntity.ok(new OkResponse());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deleteDocument(@RequestParam Long id) {
        logger.info("User with id {} requested document with id: {} removal", identity.getRawId(), id);
        documentAccessRepository.deleteByDocumentId(id);
        documentRepository.deleteById(id);
        return ResponseEntity.ok(new OkResponse());
    }

    private User getUserFromDatabase(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    private DocumentRole getDocumentRoleFromDatabase(Long documentRoleId) {
        return documentRoleRepository.findById(documentRoleId).orElseThrow();
    }

    private DocumentData getDocumentVersionData(Document document, String version) {
        Optional<DocumentData> documentData = document.getDocumentDataList().stream().filter(dd -> dd.getDocumentVersion().equals(version)).findFirst();
        return documentData.orElseGet(document::getDocumentData);
    }

    private List<DocumentUser> prepareDocumentUserList(Document document, List<DocumentUserDTO> documentUsers) {
        return documentUsers.stream().map(userDTO -> new DocumentUser(
                getUserFromDatabase(userDTO.getUserId()),
                document,
                getDocumentRoleFromDatabase(userDTO.getRoleId())
        )).collect(Collectors.toList());
    }

    private List<DocumentData> prepareNewDocumentDataList(Document document, CreateDocumentDTO createDocumentDTO, User author) {
        return List.of(new DocumentData(document, createDocumentDTO.getDocumentVersion(),
                createDocumentDTO.getFiles().stream().map(DatabaseFile::new).collect(Collectors.toList()), author));
    }

    private DocumentData prepareDocumentDataListExtension(Document document, EditDocumentDTO editDocumentDTO, User author) {
        return new DocumentData(document, editDocumentDTO.getDocumentVersion(),
                editDocumentDTO.getFiles().stream().map(DatabaseFile::new).collect(Collectors.toList()), author);
    }
}

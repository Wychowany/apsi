package com.apsi.modules.document;

import com.apsi.global.IdResponse;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.global.StringResponse;
import com.apsi.modules.document.domain.Document;
import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.dto.CreateDocumentDTO;
import com.apsi.modules.document.dto.DocumentDTO;
import com.apsi.modules.document.dto.DocumentDataDTO;
import com.apsi.modules.document.dto.EditDocumentDTO;
import com.apsi.modules.document.query.DocumentDataRepository;
import com.apsi.modules.document.query.DocumentRepository;
import com.apsi.modules.file.domain.DatabaseFile;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.query.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/documents")
@AllArgsConstructor
public class DocumentController {

    @Autowired
    private final Identity identity;

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final DocumentDataRepository documentDataRepository;

    @Autowired
    private final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(DocumentController.class);

    @GetMapping("/list")
    public ResponseEntity<?> getDocuments() {
        List<Document> documents = documentRepository.findAll();
        List<DocumentDTO> response = documents.stream().map(DocumentDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-list")
    public ResponseEntity<?> getMyDocuments() {
        List<Document> documents = documentRepository.findAllByAuthorId(identity.getRawId());
        List<DocumentDTO> response = documents.stream().map(DocumentDTO::new).collect(Collectors.toList());
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
        User controller = getUserFromDatabase(createDocumentDTO.getControllerId());
        User reviewer = getUserFromDatabase(createDocumentDTO.getReviewerId());
        User approver = getUserFromDatabase(createDocumentDTO.getApproverId());
        User receiver = getUserFromDatabase(createDocumentDTO.getReceiverId());
        Document document = Document.builder()
                .name(createDocumentDTO.getName())
                .description(createDocumentDTO.getDescription())
                .author(author)
                .build();
        document.setDocumentDataList(List.of(new DocumentData(document, createDocumentDTO.getDocumentVersion(),
                createDocumentDTO.getFiles().stream().map(DatabaseFile::new).collect(Collectors.toList()),
                author, controller, reviewer, approver, receiver)));
        Document savedDocument = documentRepository.save(document);
        return ResponseEntity.ok(new IdResponse(savedDocument.getId()));
    }

    @PutMapping
    public ResponseEntity<?> editDocument(@RequestBody EditDocumentDTO editDocumentDTO) {
        logger.info("User with id {} created new document version with data: {}", identity.getRawId(), editDocumentDTO.toString());
        Document document = documentRepository.findById(editDocumentDTO.getId()).orElseThrow();
        User author = userRepository.findById(identity.getRawId()).orElseThrow();
        User controller = getUserFromDatabase(editDocumentDTO.getControllerId());
        User reviewer = getUserFromDatabase(editDocumentDTO.getReviewerId());
        User approver = getUserFromDatabase(editDocumentDTO.getApproverId());
        User receiver = getUserFromDatabase(editDocumentDTO.getReceiverId());
        document.getDocumentDataList().add(new DocumentData(document, editDocumentDTO.getDocumentVersion(),
                editDocumentDTO.getFiles().stream().map(DatabaseFile::new).collect(Collectors.toList()),
                author, controller, reviewer, approver, receiver));
        documentRepository.save(document);
        return ResponseEntity.ok(new OkResponse());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDocument(@RequestParam Long id) {
        logger.info("User with id {} requested document with id: {} removal", identity.getRawId(), id);
        documentRepository.deleteById(id);
        return ResponseEntity.ok(new OkResponse());
    }

    private User getUserFromDatabase(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    private DocumentData getDocumentVersionData(Document document, String version) {
        Optional<DocumentData> documentData = document.getDocumentDataList().stream().filter(dd -> dd.getDocumentVersion().equals(version)).findFirst();
        return documentData.orElseGet(document::getDocumentData);
    }
}

package com.apsi.modules.document;

import com.apsi.global.IdResponse;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.modules.document.domain.Document;
import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.dto.CreateDocumentDTO;
import com.apsi.modules.document.dto.DocumentDTO;
import com.apsi.modules.document.dto.DocumentDataDTO;
import com.apsi.modules.document.dto.EditDocumentDTO;
import com.apsi.modules.document.query.DocumentRepository;
import com.apsi.modules.user.domain.User;
import com.apsi.modules.user.dto.UserDTO;
import com.apsi.modules.user.query.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> getDocument(@RequestParam Long id) {
        Document document = documentRepository.findById(id).orElseThrow();
        DocumentDataDTO response = new DocumentDataDTO(document);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createDocument(@RequestBody CreateDocumentDTO createDocumentDTO) {
        logger.info("User with id {} requested new document creation with data: {}", identity.getRawId(), createDocumentDTO.toString());
        User author = userRepository.findById(identity.getRawId()).orElseThrow();
        Document document = Document.builder()
                .name(createDocumentDTO.getName())
                .description(createDocumentDTO.getDescription())
                .author(author)
                .documentDataList(List.of(new DocumentData()))
                .build();
        Document savedDocument = documentRepository.save(document);
        return ResponseEntity.ok(new IdResponse(savedDocument.getId()));
    }

    @PutMapping
    public ResponseEntity<?> editDocument(@RequestBody EditDocumentDTO editDocumentDTO) {
        logger.info("User with id {} requested document update with data: {}", identity.getRawId(), editDocumentDTO.toString());
        Document document = documentRepository.findById(editDocumentDTO.getId()).orElseThrow();
        document.setName(editDocumentDTO.getName());
        document.setDescription(editDocumentDTO.getDescription());
        documentRepository.save(document);
        return ResponseEntity.ok(new OkResponse());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDocument(@RequestParam Long id) {
        logger.info("User with id {} requested document with id: {} removal", identity.getRawId(), id);
        documentRepository.deleteById(id);
        return ResponseEntity.ok(new OkResponse());
    }
}

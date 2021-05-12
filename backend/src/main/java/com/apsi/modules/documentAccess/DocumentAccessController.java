package com.apsi.modules.documentAccess;

import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.global.StringResponse;
import com.apsi.modules.document.query.DocumentRepository;
import com.apsi.modules.documentAccess.domain.DocumentAccess;
import com.apsi.modules.documentAccess.dto.AddDocumentAccessDTO;
import com.apsi.modules.documentAccess.dto.DocumentAccessDTO;
import com.apsi.modules.documentAccess.query.DocumentAccessRepository;
import com.apsi.modules.user.query.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/document-access")
@AllArgsConstructor
public class DocumentAccessController {

    @Autowired
    private final Identity identity;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DocumentRepository documentRepository;

    @Autowired
    private final DocumentAccessRepository documentAccessRepository;

    @PostMapping
    public ResponseEntity<?> addDocumentAccess(@RequestBody AddDocumentAccessDTO addDocumentAccessDTO) {
        DocumentAccess documentAccess = DocumentAccess.builder()
                .id(new DocumentAccess.DocumentAccessId(addDocumentAccessDTO.getDocumentId(), addDocumentAccessDTO.getUserId()))
                .document(documentRepository.findById(addDocumentAccessDTO.getDocumentId()).orElseThrow())
                .user(userRepository.findById(addDocumentAccessDTO.getUserId()).orElseThrow())
                .accessType(addDocumentAccessDTO.getAccessType())
                .build();
        documentAccessRepository.save(documentAccess);
        return ResponseEntity.ok(new OkResponse());
    }

    @GetMapping
    public ResponseEntity<?> getDocumentAccessList(@RequestParam Long id) {
        List<DocumentAccess> documentAccessList = documentAccessRepository.findAllByDocumentId(id);
        List<DocumentAccessDTO> response = documentAccessList.stream().map(DocumentAccessDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type")
    public ResponseEntity<?> getDocumentAccess(@RequestParam Long id) {
        Optional<DocumentAccess> documentAccess = documentAccessRepository.findByDocumentIdAndUserId(id, identity.getRawId());
        if (documentAccess.isPresent()) {
            if (documentAccess.get().getDocument().getAuthor().getId().equals(identity.getRawId())) {
                return ResponseEntity.ok(new StringResponse("UPDATE"));
            }
            return ResponseEntity.ok(new StringResponse(documentAccess.get().getAccessType().toString()));
        } else {
            return ResponseEntity.ok(new StringResponse(""));
        }
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> removeDocumentAccess(@RequestParam Long userId, @RequestParam Long documentId) {
        documentAccessRepository.deleteByUserIdAndDocumentId(userId, documentId);
        return ResponseEntity.ok(new OkResponse());
    }
}

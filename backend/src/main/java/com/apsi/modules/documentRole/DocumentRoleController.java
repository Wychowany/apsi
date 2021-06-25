package com.apsi.modules.documentRole;

import com.apsi.global.IdResponse;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.modules.documentRole.domain.DocumentRole;
import com.apsi.modules.documentRole.dto.CreateDocumentRoleDTO;
import com.apsi.modules.documentRole.dto.DocumentRoleDTO;
import com.apsi.modules.documentRole.dto.EditDocumentRoleDTO;
import com.apsi.modules.documentRole.query.DocumentRoleRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/document-roles")
@AllArgsConstructor
public class DocumentRoleController {

    @Autowired
    private final Identity identity;

    @Autowired
    private final DocumentRoleRepository documentRoleRepository;

    private static final Logger logger = LogManager.getLogger(DocumentRoleController.class);

    @GetMapping("/list")
    public ResponseEntity<?> getDocumentRoles() {
        List<DocumentRole> documentRoles = documentRoleRepository.findAll();
        List<DocumentRoleDTO> response = documentRoles.stream().map(DocumentRoleDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createDocumentRole(@RequestBody CreateDocumentRoleDTO createDocumentRoleDTO) {
        logger.info("User with id {} requested new document role creation with data: {}", identity.getRawId(), createDocumentRoleDTO.toString());
        DocumentRole documentRole = DocumentRole.builder()
                .name(createDocumentRoleDTO.getName())
                .accessType(createDocumentRoleDTO.getAccessType())
                .build();
        DocumentRole savedDocumentRole = documentRoleRepository.save(documentRole);
        return ResponseEntity.ok(new IdResponse(savedDocumentRole.getId()));
    }

    @PutMapping
    public ResponseEntity<?> editDocument(@RequestBody EditDocumentRoleDTO editDocumentRoleDTO) {
        logger.info("User with id {} requested document role edition with data: {}", identity.getRawId(), editDocumentRoleDTO.toString());
        DocumentRole documentRole = documentRoleRepository.findById(editDocumentRoleDTO.getId()).orElseThrow();
        documentRole.setName(editDocumentRoleDTO.getName());
        documentRole.setAccessType(editDocumentRoleDTO.getAccessType());
        documentRoleRepository.save(documentRole);
        return ResponseEntity.ok(new OkResponse());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteDocument(@RequestParam Long id) {
        logger.info("User with id {} requested document role with id: {} removal", identity.getRawId(), id);
        documentRoleRepository.deleteById(id);
        return ResponseEntity.ok(new OkResponse());
    }
}

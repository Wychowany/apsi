package com.apsi.modules.seriesAccess;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.global.StringResponse;
import com.apsi.modules.document.query.DocumentRepository;
import com.apsi.modules.documentAccess.domain.DocumentAccess;
import com.apsi.modules.documentAccess.domain.DocumentAccessType;
import com.apsi.modules.documentAccess.dto.AddDocumentAccessDTO;
import com.apsi.modules.documentAccess.dto.DocumentAccessDTO;
import com.apsi.modules.documentAccess.query.DocumentAccessRepository;
import com.apsi.modules.series.query.SeriesRepository;
import com.apsi.modules.seriesAccess.domain.SeriesAccess;
import com.apsi.modules.seriesAccess.domain.SeriesAccessType;
import com.apsi.modules.seriesAccess.dto.AddSeriesAccessDTO;
import com.apsi.modules.seriesAccess.dto.SeriesAccessDTO;
import com.apsi.modules.seriesAccess.query.SeriesAccessRepository;
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
@RequestMapping("/series-access")
@AllArgsConstructor
public class SeriesAccessController {

    @Autowired
    private final Identity identity;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final SeriesRepository seriesRepository;

    @Autowired
    private final SeriesAccessRepository seriesAccessRepository;

    @PostMapping
    public ResponseEntity<?> addSeriesAccess(@RequestBody AddSeriesAccessDTO addSeriesAccessDTO) {
        SeriesAccess seriesAccess = SeriesAccess.builder()
                .id(new SeriesAccess.SeriesAccessId(addSeriesAccessDTO.getSeriesId(), addSeriesAccessDTO.getUserId()))
                .series(seriesRepository.findById(addSeriesAccessDTO.getSeriesId()).orElseThrow())
                .user(userRepository.findById(addSeriesAccessDTO.getUserId()).orElseThrow())
                .accessType(addSeriesAccessDTO.getAccessType())
                .build();
        seriesAccessRepository.save(seriesAccess);
        return ResponseEntity.ok(new OkResponse());
    }

    @GetMapping
    public ResponseEntity<?> getSeriesAccessList(@RequestParam Long id) {
        List<SeriesAccess> seriesAccessList = seriesAccessRepository.findAllBySeriesId(id);
        List<SeriesAccessDTO> response = seriesAccessList.stream().map(SeriesAccessDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type")
    public ResponseEntity<?> getSeriesAccess(@RequestParam Long id) {
        if (seriesRepository.existsByIdAndAuthorId(id, identity.getRawId())) {
            return ResponseEntity.ok(new StringResponse(SeriesAccessType.UPDATE.toString()));
        }
        Optional<SeriesAccess> seriesAccess = seriesAccessRepository.findBySeriesIdAndUserId(id, identity.getRawId());
        return seriesAccess.map(access -> ResponseEntity.ok(new StringResponse(access.getAccessType().toString())))
                .orElseGet(() -> ResponseEntity.ok(new StringResponse("")));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> removeSeriesAccess(@RequestParam Long userId, @RequestParam Long seriesId) {
        seriesAccessRepository.deleteByUserIdAndSeriesId(userId, seriesId);
        return ResponseEntity.ok(new OkResponse());
    }



}



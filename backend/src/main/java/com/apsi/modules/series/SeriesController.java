package com.apsi.modules.series;

import com.apsi.global.IdResponse;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.modules.document.DocumentController;
import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.dto.DocumentDTO;
import com.apsi.modules.document.query.DocumentDataRepository;
import com.apsi.modules.seriesAccess.domain.SeriesAccess;
import com.apsi.modules.series.domain.Series;
import com.apsi.modules.series.domain.SeriesData;
import com.apsi.modules.series.domain.SeriesDocument;
import com.apsi.modules.series.dto.*;
import com.apsi.modules.series.query.SeriesDataRepository;
import com.apsi.modules.series.query.SeriesRepository;
import com.apsi.modules.seriesAccess.query.SeriesAccessRepository;
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
import java.lang.System.*;

@RestController
@RequestMapping("/series")
@AllArgsConstructor
class SeriesController {

    @Autowired
    private final Identity identity;

    @Autowired
    private final SeriesRepository seriesRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DocumentDataRepository documentDataRepository;

    @Autowired
    private final SeriesDataRepository seriesDataRepository;

    @Autowired
    private final SeriesAccessRepository seriesAccessRepository;

    private static final Logger logger = LogManager.getLogger(DocumentController.class);

    @GetMapping("/list")
    public ResponseEntity<?> getSeries() {
        List<Series> series = seriesRepository.findAllByAuthorId(identity.getRawId());
        List<SeriesAccess> accesses = seriesAccessRepository.findAllByUserIdAndSeriesAuthorIdIsNot(identity.getRawId(), identity.getRawId());

        List<SeriesDTO> response = series.stream().map(SeriesDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(Stream.concat(series.stream().map(serie -> new SeriesDTO(serie,true)),accesses.stream().map(access->new SeriesDTO(access,false))).collect(Collectors.toList()));
    }

    @GetMapping("/my-list")
    public ResponseEntity<?> getMySeries() {
        List<Series> series = seriesRepository.findAllByAuthorId(identity.getRawId());
        List<MySeriesDTO> response = series.stream().map(MySeriesDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getSeries(@RequestParam Long id, @RequestParam(required = false) String version) {
        Series series = seriesRepository.findById(id).orElseThrow();
        SeriesData seriesData = version == null ? series.getSeriesData() : getSeriesVersionData(series, version);
        SeriesDataDTO response = new SeriesDataDTO(seriesData);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/versions")
    public ResponseEntity<?> getSeriesVersions(@RequestParam Long id) {
        return ResponseEntity.ok(seriesDataRepository.findVersionsBySeriesId(id));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> deleteSeries(@RequestParam Long id) {
        logger.info("User with id {} requested series with id: {} removal", identity.getRawId(), id);
        seriesAccessRepository.deleteBySeriesId(id);
        seriesRepository.deleteById(id);
        return ResponseEntity.ok(new OkResponse());
    }

    @PostMapping
    public ResponseEntity<?> createSeries(@RequestBody CreateSeriesDTO createSeriesDTO) {
        logger.info("User with id {} requested new series creation with data: {}", identity.getRawId(), createSeriesDTO.toString());
        User author = userRepository.findById(identity.getRawId()).orElseThrow();
        Series series = Series.builder()
                .name(createSeriesDTO.getName())
                .description(createSeriesDTO.getDescription())
                .author(author)
                .build();
        series.setSeriesDataList(prepareNewSeriesDataList(series, createSeriesDTO, author));
        Series savedSeries = seriesRepository.save(series);
        return ResponseEntity.ok(new IdResponse(savedSeries.getId()));
    }

    @PutMapping
    public ResponseEntity<?> editSeries(@RequestBody EditSeriesDTO editSeriesDTO) {
        logger.info("User with id {} created new series version with data: {}", identity.getRawId(), editSeriesDTO.toString());
        Series series = seriesRepository.findById(editSeriesDTO.getId()).orElseThrow();
        User author = userRepository.findById(identity.getRawId()).orElseThrow();
        series.getSeriesDataList().add(prepareSeriesDataListExtension(series, editSeriesDTO, author));
        seriesRepository.save(series);
        return ResponseEntity.ok(new OkResponse());
    }

    private List<SeriesData> prepareNewSeriesDataList(Series series, CreateSeriesDTO createSeriesDTO, User author) {
        SeriesData seriesData = new SeriesData(series, createSeriesDTO.getSeriesVersion(), author);
        seriesData.setDocuments(prepareDocumentsInSeries(seriesData, createSeriesDTO.getDocuments()));

        return List.of(seriesData);
    }

    private List<SeriesDocument> prepareDocumentsInSeries(SeriesData seriesData, List<CreateSeriesDocumentDTO> documentsInSeries) {
        return documentsInSeries.stream().map(documentDTO -> new SeriesDocument(
                getDocumentDataFromDatabase(documentDTO.getDocumentDataId()),
                seriesData
        )).collect(Collectors.toList());
    }

    private DocumentData getDocumentDataFromDatabase(Long documentDataId) {
        return documentDataRepository.findById(documentDataId).orElseThrow();
    }

    private SeriesData getSeriesVersionData(Series series, String version) {
        Optional<SeriesData> seriesData = series.getSeriesDataList().stream().filter(s -> s.getSeriesVersion().equals(version)).findFirst();
        return seriesData.orElseGet(series::getSeriesData);
    }

    private SeriesData prepareSeriesDataListExtension(Series series, EditSeriesDTO editSeriesDTO, User author) {
        SeriesData seriesData = new SeriesData(series, editSeriesDTO.getSeriesVersion(), author);
        seriesData.setDocuments(prepareDocumentsInSeries(seriesData, editSeriesDTO.getDocuments()));
        return seriesData;
    }
}
package com.apsi.modules.series;

import com.apsi.global.IdResponse;
import com.apsi.global.Identity;
import com.apsi.global.OkResponse;
import com.apsi.modules.document.DocumentController;
import com.apsi.modules.document.domain.Document;
import com.apsi.modules.document.domain.DocumentData;
import com.apsi.modules.document.domain.DocumentUser;
import com.apsi.modules.document.dto.CreateDocumentDTO;
import com.apsi.modules.document.dto.DocumentUserDTO;
import com.apsi.modules.document.query.DocumentDataRepository;
import com.apsi.modules.documentRole.domain.DocumentRole;
import com.apsi.modules.file.domain.DatabaseFile;
import com.apsi.modules.series.domain.Series;
import com.apsi.modules.series.domain.SeriesData;
import com.apsi.modules.series.domain.SeriesDocument;
import com.apsi.modules.series.dto.CreateSeriesDTO;
import com.apsi.modules.series.dto.SeriesDTO;
import com.apsi.modules.series.dto.MySeriesDTO;
import com.apsi.modules.series.dto.SeriesDocumentDTO;
import com.apsi.modules.series.query.SeriesRepository;
import com.apsi.modules.user.domain.User;
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

    private static final Logger logger = LogManager.getLogger(DocumentController.class);

    @GetMapping("/list")
    public ResponseEntity<?> getSeries() {
        List<Series> series = seriesRepository.findAll();
        List<SeriesDTO> response = series.stream().map(SeriesDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-list")
    public ResponseEntity<?> getMySeries() {
        List<Series> series = seriesRepository.findAllByAuthorId(identity.getRawId());
        List<MySeriesDTO> response = series.stream().map(MySeriesDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteSeries(@RequestParam Long id) {
        logger.info("User with id {} requested series with id: {} removal", identity.getRawId(), id);
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

    private List<SeriesData> prepareNewSeriesDataList(Series series, CreateSeriesDTO createSeriesDTO, User author) {
        SeriesData seriesData = new SeriesData(series, createSeriesDTO.getSeriesVersion(), author);
        seriesData.setDocumentsInSeries(prepareDocumentsInSeries(seriesData, createSeriesDTO.getDocuments()));

        return List.of(seriesData);
    }

    private List<SeriesDocument> prepareDocumentsInSeries(SeriesData seriesData, List<SeriesDocumentDTO> documentsInSeries) {
        return documentsInSeries.stream().map(documentDTO -> new SeriesDocument(
                getDocumentDataFromDatabase(documentDTO.getDocumentDataId()),
                seriesData
        )).collect(Collectors.toList());
    }

    private DocumentData getDocumentDataFromDatabase(Long documentDataId) {
        return documentDataRepository.findById(documentDataId).orElseThrow();
    }
}
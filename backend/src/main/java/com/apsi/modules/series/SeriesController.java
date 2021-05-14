package com.apsi.modules.series;

import com.apsi.global.Identity;
import com.apsi.modules.series.domain.Series;
import com.apsi.modules.series.dto.SeriesDTO;
import com.apsi.modules.series.dto.MySeriesDTO;
import com.apsi.modules.series.query.SeriesRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
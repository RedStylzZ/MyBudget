package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.DepositSeries;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.DepositSeriesDTO;
import com.github.redstylzz.backend.service.DepositSeriesService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series/deposit")
@RequiredArgsConstructor
@ExtensionMethod(DepositSeriesController.class)
public class DepositSeriesController {
    private static final Log LOG = LogFactory.getLog(DepositSeriesController.class);
    private final DepositSeriesService service;

    public List<DepositSeriesDTO> convertToDTO(List<DepositSeries> series) {
        LOG.info("Get series");
        return series.stream().map(DepositSeriesDTO::mapSeriesToDTO).toList();
    }

    @GetMapping
    public List<DepositSeriesDTO> getSeries(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return convertToDTO(service.getSeries(user.getId()));
    }

    @PostMapping
    public List<DepositSeriesDTO> addSeries(UsernamePasswordAuthenticationToken principal, @RequestBody DepositSeriesDTO dto) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return convertToDTO(service.addSeries(user.getId(), dto));
    }

    @DeleteMapping
    public List<DepositSeriesDTO> deleteSeries(UsernamePasswordAuthenticationToken principal, @RequestParam String seriesId) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return convertToDTO(service.deleteSeries(user.getId(), seriesId));
    }

    @PutMapping
    public List<DepositSeriesDTO> changeSeries(UsernamePasswordAuthenticationToken principal, @RequestBody DepositSeriesDTO dto) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return convertToDTO(service.changeSeries(user.getId(), dto));
    }
}

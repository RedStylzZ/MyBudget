package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.PaymentSeries;
import com.github.redstylzz.backend.model.dto.PaymentSeriesDTO;
import com.github.redstylzz.backend.service.PaymentSeriesService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/series/payment")
@RequiredArgsConstructor
public class PaymentSeriesController {
    private static final Log LOG = LogFactory.getLog(PaymentSeriesController.class);
    private final PaymentSeriesService service;

    private MongoUser getUser(UsernamePasswordAuthenticationToken principal) throws ResponseStatusException {
        try {
            return (MongoUser) principal.getPrincipal();
        } catch (Exception e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    public List<PaymentSeriesDTO> convertToDTO(List<PaymentSeries> series) {
        LOG.info("Get series");
        return series.stream().map(PaymentSeriesDTO::mapSeriesToDTO).toList();
    }

    @GetMapping
    public List<PaymentSeriesDTO> getSeries(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = getUser(principal);
        return convertToDTO(service.getSeries(user.getId()));
    }

    @PostMapping
    public List<PaymentSeriesDTO> addSeries(UsernamePasswordAuthenticationToken principal, @RequestBody PaymentSeriesDTO dto) {
        MongoUser user = getUser(principal);
        return convertToDTO(service.addSeries(user.getId(), dto));
    }

    @DeleteMapping
    public List<PaymentSeriesDTO> deleteSeries(UsernamePasswordAuthenticationToken principal, @RequestParam String seriesId) {
        MongoUser user = getUser(principal);
        return convertToDTO(service.deleteSeries(user.getId(), seriesId));
    }

    @PutMapping
    public List<PaymentSeriesDTO> changeSeries(UsernamePasswordAuthenticationToken principal, @RequestBody PaymentSeriesDTO dto) {
        MongoUser user = getUser(principal);
        return convertToDTO(service.changeSeries(user.getId(), dto));
    }
}

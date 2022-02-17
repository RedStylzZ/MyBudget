package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.PaymentSeriesDTO;
import com.github.redstylzz.backend.service.PaymentSeriesService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series/payment")
@RequiredArgsConstructor
public class PaymentSeriesController {
    private static final Log LOG = LogFactory.getLog(PaymentSeriesController.class);
    private final PaymentSeriesService service;

    @GetMapping
    public List<PaymentSeriesDTO> getSeries(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.getSeries(user.getId());
    }

    @PostMapping
    public List<PaymentSeriesDTO> addSeries(UsernamePasswordAuthenticationToken principal, @RequestBody PaymentSeriesDTO dto) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.addSeries(user.getId(), dto);
    }

    @DeleteMapping
    public List<PaymentSeriesDTO> deleteSeries(UsernamePasswordAuthenticationToken principal, @RequestParam String seriesId) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.deleteSeries(user.getId(), seriesId);
    }

    @PutMapping
    public List<PaymentSeriesDTO> changeSeries(UsernamePasswordAuthenticationToken principal, @RequestBody PaymentSeriesDTO dto) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.changeSeries(user.getId(), dto);
    }
}

package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.PaymentSeriesDTO;
import com.github.redstylzz.backend.service.MongoUserService;
import com.github.redstylzz.backend.service.PaymentSeriesService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class PaymentSeriesController {
    private static final Log LOG = LogFactory.getLog(PaymentSeriesController.class);

    private final MongoUserService userService;
    private final PaymentSeriesService service;

    private MongoUser getUser(Principal principal) throws ResponseStatusException {
        try {
            return userService.getUserByPrincipal(principal);
        } catch (UsernameNotFoundException e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    @GetMapping
    public List<PaymentSeriesDTO> getSeries(Principal principal) {
        MongoUser user = getUser(principal);
        return service.getSeries(user.getId());
    }

    @PostMapping
    public List<PaymentSeriesDTO> addSeries(Principal principal, @RequestBody PaymentSeriesDTO dto) {
        MongoUser user = getUser(principal);
        return service.addSeries(user.getId(), dto);
    }

    @DeleteMapping
    public List<PaymentSeriesDTO> deleteSeries(Principal principal, @RequestParam String seriesId) {
        MongoUser user = getUser(principal);
        return service.deleteSeries(user.getId(), seriesId);
    }

    @PutMapping
    public List<PaymentSeriesDTO> changeSeries(Principal principal, @RequestBody PaymentSeriesDTO dto) {
        MongoUser user = getUser(principal);
        return service.changeSeries(user.getId(), dto);
    }
}

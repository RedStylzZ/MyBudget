package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.service.MongoUserService;
import com.github.redstylzz.backend.service.PaymentService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private static final Log LOG = LogFactory.getLog(PaymentController.class);

    private final PaymentService service;
    private final MongoUserService userService;


    public PaymentController(PaymentService service, MongoUserService userService) {
        this.service = service;
        this.userService = userService;
    }

    private MongoUser getUser(Principal principal) {
        try {
            return userService.getUserByPrincipal(principal);
        } catch (UsernameNotFoundException e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    @GetMapping
    public List<Payment> getPayments(Principal principal, @RequestParam String categoryID) {
        MongoUser user = getUser(principal);
        return service.getPayments(user.getId(), categoryID);
    }

    @PutMapping
    public List<Payment> addPayment(Principal principal, @RequestBody PaymentDTO dto) {
        MongoUser user = getUser(principal);
        Payment payment = Payment.convertDTOtoPayment(dto);
        if (payment == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No payment given");
        return service.addPayment(user.getId(), payment);
    }

    @DeleteMapping
    public List<Payment> deletePayment(Principal principal, @RequestBody PaymentDTO dto) {
        MongoUser user = getUser(principal);
        Payment payment = Payment.convertDTOtoPayment(dto);
        if (payment == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No payment given");
        return service.deletePayment(user.getId(), payment);
    }


}

package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.model.dto.RequestPaymentDTO;
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

    private MongoUser getUser(Principal principal) throws ResponseStatusException {
        try {
            return userService.getUserByPrincipal(principal);
        } catch (UsernameNotFoundException e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    @GetMapping("/")
    public PaymentDTO getPayment(Principal principal,
                                 @RequestParam String categoryID,
                                 @RequestParam String paymentID) {
        MongoUser user = getUser(principal);
        return service.getPayment(user.getId(), categoryID, paymentID);
    }

    @GetMapping("{categoryID}")
    public List<PaymentDTO> getPayments(Principal principal, @PathVariable String categoryID) {
        MongoUser user = getUser(principal);
        return service.getPayments(user.getId(), categoryID);
    }

    @GetMapping
    public List<PaymentDTO> getLastPayments(Principal principal) {
        MongoUser user = getUser(principal);
        return service.getLastPayments(user.getId());
    }

    @PutMapping
    public List<PaymentDTO> addPayment(Principal principal, @RequestBody RequestPaymentDTO dto) {
        String userID = getUser(principal).getId();
        Payment payment = Payment.convertDTOtoPayment(dto);
        return service.addPayment(userID, payment);
    }

    @DeleteMapping
    public List<PaymentDTO> deletePayment(Principal principal, @RequestParam String categoryID, @RequestParam String paymentID) {
        String userID = getUser(principal).getId();
        return service.deletePayment(userID, categoryID, paymentID);
    }

    @PostMapping
    public List<PaymentDTO> changePayment(Principal principal, @RequestBody PaymentDTO dto) {
        String userID = getUser(principal).getId();
        Payment payment = Payment.convertDTOtoPayment(dto);
        return service.changePayment(userID, payment);
    }

}

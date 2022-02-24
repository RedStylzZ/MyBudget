package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.model.dto.RequestPaymentDTO;
import com.github.redstylzz.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private static final Log LOG = LogFactory.getLog(PaymentController.class);

    private final PaymentService service;

    private MongoUser getUser(UsernamePasswordAuthenticationToken principal) throws ResponseStatusException {
        try {
            return (MongoUser) principal.getPrincipal();
        } catch (Exception e) {
            LOG.warn("No user found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No user found");
        }
    }

    private List<PaymentDTO> getPaymentsAsDTO(List<Payment> payments) {
        return payments.stream().map(Payment::convertPaymentToDTO).toList();
    }

    @GetMapping("/")
    public PaymentDTO getPayment(UsernamePasswordAuthenticationToken principal,
                                 @RequestParam String categoryId,
                                 @RequestParam String paymentId) {
        MongoUser user = getUser(principal);
        return Payment.convertPaymentToDTO(service.getPayment(user.getId(), categoryId, paymentId));
    }

    @GetMapping("{categoryId}")
    public List<PaymentDTO> getPayments(UsernamePasswordAuthenticationToken principal,
                                        @PathVariable String categoryId) {
        MongoUser user = getUser(principal);
        return getPaymentsAsDTO(service.getPayments(user.getId(), categoryId));
    }

    @GetMapping
    public List<PaymentDTO> getLastPayments(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = getUser(principal);
        return getPaymentsAsDTO(service.getLastPayments(user.getId()));
    }

    @PostMapping
    public List<PaymentDTO> addPayment(UsernamePasswordAuthenticationToken principal,
                                       @RequestBody RequestPaymentDTO dto) {
        String userId = getUser(principal).getId();
        Payment payment = Payment.convertDTOtoPayment(dto);
        return getPaymentsAsDTO(service.addPayment(userId, payment));
    }

    @DeleteMapping
    public List<PaymentDTO> deletePayment(UsernamePasswordAuthenticationToken principal,
                                          @RequestParam String categoryId,
                                          @RequestParam String paymentId) {
        String userId = getUser(principal).getId();
        return getPaymentsAsDTO(service.deletePayment(userId, categoryId, paymentId));
    }

    @PutMapping
    public List<PaymentDTO> changePayment(UsernamePasswordAuthenticationToken principal,
                                          @RequestBody PaymentDTO dto) {
        String userId = getUser(principal).getId();
        Payment payment = Payment.convertDTOtoPayment(dto);
        return getPaymentsAsDTO(service.changePayment(userId, payment));
    }

}

package com.github.redstylzz.backend.controller;

import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.Payment;
import com.github.redstylzz.backend.model.dto.PaymentDTO;
import com.github.redstylzz.backend.model.dto.RequestPaymentDTO;
import com.github.redstylzz.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private static final Log LOG = LogFactory.getLog(PaymentController.class);

    private final PaymentService service;

    @GetMapping("/")
    public PaymentDTO getPayment(UsernamePasswordAuthenticationToken principal,
                                 @RequestParam String categoryID,
                                 @RequestParam String paymentID) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.getPayment(user.getId(), categoryID, paymentID);
    }

    @GetMapping("{categoryID}")
    public List<PaymentDTO> getPayments(UsernamePasswordAuthenticationToken principal,
                                        @PathVariable String categoryID) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.getPayments(user.getId(), categoryID);
    }

    @GetMapping
    public List<PaymentDTO> getLastPayments(UsernamePasswordAuthenticationToken principal) {
        MongoUser user = MongoUser.getUser(principal, LOG);
        return service.getLastPayments(user.getId());
    }

    @PutMapping
    public List<PaymentDTO> addPayment(UsernamePasswordAuthenticationToken principal,
                                       @RequestBody RequestPaymentDTO dto) {
        String userID = MongoUser.getUser(principal, LOG).getId();
        Payment payment = Payment.convertDTOtoPayment(dto);
        return service.addPayment(userID, payment);
    }

    @DeleteMapping
    public List<PaymentDTO> deletePayment(UsernamePasswordAuthenticationToken principal,
                                          @RequestParam String categoryID,
                                          @RequestParam String paymentID) {
        String userID = MongoUser.getUser(principal, LOG).getId();
        return service.deletePayment(userID, categoryID, paymentID);
    }

    @PostMapping
    public List<PaymentDTO> changePayment(UsernamePasswordAuthenticationToken principal,
                                          @RequestBody PaymentDTO dto) {
        String userID = MongoUser.getUser(principal, LOG).getId();
        Payment payment = Payment.convertDTOtoPayment(dto);
        return service.changePayment(userID, payment);
    }

}

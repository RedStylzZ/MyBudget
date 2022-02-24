package com.github.redstylzz.backend.model;

import com.github.redstylzz.backend.model.dto.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class TestDataProvider {
    public static MongoUser testUser(String id,
                                     String username,
                                     String password,
                                     List<String> rights,
                                     boolean accountNonExpired,
                                     boolean accountNonLocked,
                                     boolean credentialsNonExpired,
                                     boolean enabled
    ) {
        return MongoUser.builder()
                .id(id)
                .username(username)
                .password(password)
                .rights(rights)
                .accountNonExpired(accountNonExpired)
                .accountNonLocked(accountNonLocked)
                .credentialsNonExpired(credentialsNonExpired)
                .enabled(enabled)
                .build();
    }

    public static MongoUser testUser() {
        return testUser("24",
                "Test",
                "TestPassword",
                List.of(),
                true,
                true,
                true,
                true);
    }

    public static MongoUserDTO testUserDTO(String username, String password, List<String> rights) {
        return MongoUserDTO.builder()
                .username(username)
                .password(password)
                .rights(rights)
                .build();
    }

    public static MongoUserDTO testUserDTO() {
        return testUserDTO("Test", "TestPassword", List.of());
    }

    public static Category testCategory(String id, String userID, String name) {
        return Category.builder()
                .categoryID(id)
                .userID(userID)
                .categoryName(name)
                .build();
    }

    public static Category testCategory() {
        return testCategory("44", "24", "Test");
    }

    public static Payment testPayment() {
        return testPayment("36", "24", "44", "PayPal", new BigDecimal("10.0"), Instant.EPOCH, Instant.EPOCH);
    }

    public static Payment testPayment(String paymentID,
                                      String userID,
                                      String categoryID,
                                      String description,
                                      BigDecimal amount,
                                      Instant saveDate,
                                      Instant payDate) {
        return Payment.builder()
                .paymentID(paymentID)
                .userID(userID)
                .categoryID(categoryID)
                .description(description)
                .amount(amount)
                .saveDate(saveDate)
                .payDate(payDate)
                .build();
    }

    public static PaymentSeries testPaymentSeries() {
        return testPaymentSeries("44",
                "24",
                Instant.EPOCH,
                Instant.EPOCH,
                Payment.convertPaymentToDTO(testPayment()),
                1);
    }

    public static PaymentSeries testPaymentSeries(String seriesId,
                                                  String userId,
                                                  Instant startDate,
                                                  Instant endDate,
                                                  PaymentDTO payment,
                                                  int scheduledDate) {
        return PaymentSeries.builder()
                .seriesId(seriesId)
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .payment(payment)
                .scheduledDate(scheduledDate)
                .build();
    }

    public static Deposit testDeposit() {
        return Deposit.builder()
                .depositId("24")
                .userId("24")
                .description("PayPal")
                .amount(new BigDecimal(("10.0")))
                .saveDate(Instant.EPOCH)
                .depositDate(Instant.EPOCH)
                .build();
    }

    public static DepositDTO testDepositDTO() {
        return DepositDTO.builder()
                .depositId("24")
                .description("PayPal")
                .amount(new BigDecimal(("10.0")))
                .depositDate(Instant.EPOCH)
                .build();
    }

    public static DepositSeries testDepositSeries() {
        return testDepositSeries("44",
                "24",
                Instant.EPOCH,
                Instant.EPOCH,
                testDepositDTO(),
                1);
    }

    public static DepositSeries testDepositSeries(String seriesId,
                                                  String userId,
                                                  Instant startDate,
                                                  Instant endDate,
                                                  DepositDTO deposit,
                                                  int scheduledDate) {
        return DepositSeries.builder()
                .seriesId(seriesId)
                .userId(userId)
                .startDate(startDate)
                .endDate(endDate)
                .deposit(deposit)
                .scheduledDate(scheduledDate)
                .build();
    }

    public static DepositSeriesDTO testDepositSeriesDTO() {
        return DepositSeriesDTO.builder()
                .seriesId("DepositSeriesDTOId")
                .startDate(Instant.EPOCH)
                .endDate(Instant.EPOCH)
                .deposit(testDepositDTO())
                .scheduledDate(24)
                .build();
    }

    public static PaymentSeriesDTO testPaymentSeriesDTO() {
        return PaymentSeriesDTO.builder()
                .seriesId("DepositSeriesDTOId")
                .startDate(Instant.EPOCH)
                .endDate(Instant.EPOCH)
                .payment(Payment.convertPaymentToDTO(testPayment()))
                .scheduledDate(24)
                .build();
    }

}

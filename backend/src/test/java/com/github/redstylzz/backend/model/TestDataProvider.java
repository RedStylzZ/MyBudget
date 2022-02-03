package com.github.redstylzz.backend.model;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mockStatic;

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
                "Tizian",
                "Turtle",
                List.of(),
                true,
                true,
                true,
                true);
    }


    public static Category testCategory(String id, String userID, String name, BigDecimal paymentSum) {
        return Category.builder()
                .categoryID(id)
                .userID(userID)
                .categoryName(name)
                .paymentSum(paymentSum)
                .build();
    }

    public static Category testCategory() {
        return testCategory("44", "24", "Tizian", new BigDecimal("0"));
    }

    public static Payment testPayment() {
        return testPayment("36", "24", "44", "PayPal", new BigDecimal("10.0"), new Date(), new Date());
    }

    public static Payment testPayment(String paymentID,
                              String userID,
                              String categoryID,
                              String description,
                              BigDecimal amount,
                              Date saveDate,
                              Date payDate) {
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

    public static String UUID_STRING = "06f1eb01-cdaf-46e4-a3c8-eff2e4b300dd";

    public static void mockUUID() {
        UUID uuid = UUID.fromString(UUID_STRING);
        MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
        uuidMock.when(UUID::randomUUID).thenReturn(uuid);
    }

}

package com.github.redstylzz.backend.model;

import org.mockito.MockedStatic;

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


    public static Category testCategory(String id, String userID, String name, double paymentSum) {
        return Category.builder()
                .id(id)
                .userID(userID)
                .name(name)
                .paymentSum(paymentSum)
                .build();
    }

    public static Category testCategory() {
        return testCategory("44", "24", "Tizian", 0.0);
    }

    public static UUID mockUUID() {
        UUID randUUID = UUID.randomUUID();
        MockedStatic<UUID> uuidMock = mockStatic(UUID.class);
        uuidMock.when(UUID::randomUUID).thenReturn(randUUID);
        return randUUID;
    }
}

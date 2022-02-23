package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.DepositAlreadyExistException;
import com.github.redstylzz.backend.exception.DepositDoesNotExistException;
import com.github.redstylzz.backend.model.Deposit;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.repository.IDepositRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static com.github.redstylzz.backend.model.TestDataProvider.testDeposit;
import static com.github.redstylzz.backend.model.TestDataProvider.testUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DepositServiceTest {

    private final IDepositRepository repository = mock(IDepositRepository.class);
    private final DepositService underTest = new DepositService(repository);
    private final MongoUser user = testUser();

    @Test
    void shouldReturnAllDepositsFromUser() {
        String userId = user.getId();
        List<Deposit> actualDeposits = List.of(testDeposit());
        when(repository.getAllByUserId(anyString())).thenReturn(actualDeposits);

        List<Deposit> deposits = underTest.getDepositsFrom(userId);

        assertEquals(actualDeposits, deposits);
    }

    @Test
    void shouldSpecificDepositsFromUserAndDepositId() {
        Deposit actualDeposit = testDeposit();
        String depositId = actualDeposit.getDepositId();
        String userId = user.getId();
        when(repository.getByUserIdAndDepositId(anyString(), anyString())).thenReturn(actualDeposit);

        Deposit deposit = underTest.getDepositFrom(userId, depositId);

        assertEquals(actualDeposit, deposit);
    }

    @Test
    void shouldReturnAllDepositsOfCurrentMonthFromUser() {
        String userId = user.getId();
        List<Deposit> actualDeposits = List.of(testDeposit());
        when(repository.getAllByUserIdAndDepositDateAfterOrderByDepositDateDesc(anyString(), any(Instant.class))).thenReturn(actualDeposits);

        List<Deposit> deposits = underTest.getLatestDeposits(userId);

        assertEquals(actualDeposits, deposits);
    }

    @Test
    void shouldAddDeposit() {
        Deposit newDeposit = testDeposit();
        String userId = user.getId();
        when(repository.existsByUserIdAndDepositId(anyString(), anyString())).thenReturn(false);
        when(repository.getAllByUserId(anyString())).thenReturn(List.of(newDeposit));

        List<Deposit> deposits = underTest.addDeposit(userId, newDeposit);

        verify(repository).save(any(Deposit.class));
        assertEquals(List.of(newDeposit), deposits);
    }

    @Test
    void shouldThrowExceptionIfDepositAlreadyExist() {
        Deposit newDeposit = testDeposit();
        String userId = user.getId();
        when(repository.existsByUserIdAndDepositId(anyString(), anyString())).thenReturn(true);

        assertThrows(DepositAlreadyExistException.class, () -> underTest.addDeposit(userId, newDeposit));
    }

    @Test
    void shouldThrowExceptionIfNewDepositIfInvalid() {
        Deposit newDeposit = testDeposit();
        newDeposit.setDescription(null);
        String userId = user.getId();
        when(repository.existsByUserIdAndDepositId(anyString(), anyString())).thenReturn(false);

        assertThrows(NullPointerException.class, () -> underTest.addDeposit(userId, newDeposit));
    }

    @Test
    void shouldChangeDeposit() {
        Deposit newDeposit = testDeposit();
        String userId = user.getId();
        when(repository.existsByUserIdAndDepositId(anyString(), anyString())).thenReturn(true);
        when(repository.getAllByUserId(anyString())).thenReturn(List.of(newDeposit));

        List<Deposit> deposits = underTest.changeDeposit(userId, newDeposit);

        verify(repository).save(any(Deposit.class));
        assertEquals(List.of(newDeposit), deposits);
    }

    @Test
    void shouldThrowExceptionIfDepositDoesNotExist() {
        Deposit newDeposit = testDeposit();
        String userId = user.getId();
        when(repository.existsByUserIdAndDepositId(anyString(), anyString())).thenReturn(false);

        assertThrows(DepositDoesNotExistException.class, () -> underTest.changeDeposit(userId, newDeposit));
    }

    @Test
    void shouldThrowExceptionIfNewDepositIfInvalid_OnChange() {
        Deposit newDeposit = testDeposit();
        newDeposit.setDescription(null);
        String userId = user.getId();
        when(repository.existsByUserIdAndDepositId(anyString(), anyString())).thenReturn(true);

        assertThrows(NullPointerException.class, () -> underTest.changeDeposit(userId, newDeposit));
    }

    @Test
    void shouldDeleteDepositFromUserAndDepositId() {
        Deposit actualDeposit = testDeposit();
        String depositId = actualDeposit.getDepositId();
        String userId = user.getId();
        when(repository.getAllByUserId(anyString())).thenReturn(List.of(actualDeposit));

        List<Deposit> deposits = underTest.deleteDeposit(userId, depositId);

        assertEquals(List.of(actualDeposit), deposits);
    }


}
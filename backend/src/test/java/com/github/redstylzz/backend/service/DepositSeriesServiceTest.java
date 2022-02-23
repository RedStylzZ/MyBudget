package com.github.redstylzz.backend.service;

import com.github.redstylzz.backend.exception.SeriesAlreadyExistException;
import com.github.redstylzz.backend.model.DepositSeries;
import com.github.redstylzz.backend.model.MongoUser;
import com.github.redstylzz.backend.model.dto.DepositSeriesDTO;
import com.github.redstylzz.backend.repository.IDepositSeriesRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.redstylzz.backend.model.TestDataProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DepositSeriesServiceTest {

    private final IDepositSeriesRepository depositSeriesRepository = mock(IDepositSeriesRepository.class);
    private final DepositSeriesService underTest = new DepositSeriesService(depositSeriesRepository);
    private final MongoUser user = testUser();

    @Test
    void shouldReturnAllSeriesForUser() {
        List<DepositSeries> actualSeries = List.of(testDepositSeries());
        when(depositSeriesRepository.getAllByUserId(anyString())).thenReturn(actualSeries);

        List<DepositSeries> series = underTest.getSeries(testUser().getId());

        assertEquals(actualSeries, series);
    }

    @Test
    void shouldSaveAndReturnDepositSeries() {
        List<DepositSeries> actualSeries = List.of(testDepositSeries());
        when(depositSeriesRepository.existsBySeriesId(anyString())).thenReturn(false);
        when(depositSeriesRepository.getAllByUserId(anyString())).thenReturn(actualSeries);

        List<DepositSeries> series = underTest.addSeries(user.getId(), testDepositSeriesDTO());

        verify(depositSeriesRepository).save(any(DepositSeries.class));
        assertEquals(actualSeries, series);
    }

    @Test
    void shouldThrowExceptionIfSeriesDoesNotExist() {
        String userId = user.getId();
        DepositSeriesDTO dto = testDepositSeriesDTO();
        when(depositSeriesRepository.existsBySeriesId(anyString())).thenReturn(true);

        assertThrows(SeriesAlreadyExistException.class, () -> underTest.addSeries(userId, dto));
        verify(depositSeriesRepository).existsBySeriesId(anyString());
        verifyNoMoreInteractions(depositSeriesRepository);
    }

    @Test
    void shouldDeleteAndReturnDepositSeriesForUser() {
        String userId = user.getId();
        List<DepositSeries> actualSeries = List.of(testDepositSeries());
        String seriesId = testDepositSeriesDTO().getSeriesId();
        when(depositSeriesRepository.getAllByUserId(anyString())).thenReturn(actualSeries);

        List<DepositSeries> series = underTest.deleteSeries(userId, seriesId);

        assertEquals(actualSeries, series);
        verify(depositSeriesRepository).deleteByUserIdAndSeriesId(anyString(), anyString());
    }

    @Test
    void shouldChangeSeries() {
        String userId = user.getId();
        DepositSeriesDTO dto = testDepositSeriesDTO();
        when(depositSeriesRepository.existsByUserIdAndSeriesId(anyString(), anyString())).thenReturn(true);

        underTest.changeSeries(userId, dto);

        verify(depositSeriesRepository).save(any(DepositSeries.class));
        verify(depositSeriesRepository).getAllByUserId(anyString());
    }

}
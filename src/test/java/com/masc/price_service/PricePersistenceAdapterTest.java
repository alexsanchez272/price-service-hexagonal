package com.masc.price_service;

import com.masc.price_service.domain.models.Price;
import com.masc.price_service.infrastructure.persistence.adapters.PricePersistenceAdapter;
import com.masc.price_service.infrastructure.persistence.entities.PriceEntity;
import com.masc.price_service.infrastructure.persistence.repositories.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PricePersistenceAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PricePersistenceAdapter pricePersistenceAdapter;

    private PriceEntity priceEntity;

    @BeforeEach
    void setUp() {
        priceEntity = new PriceEntity();
        priceEntity.setId(1L);
        priceEntity.setBrandId(1L);
        priceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0));
        priceEntity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        priceEntity.setPriceList(1);
        priceEntity.setProductId(35455L);
        priceEntity.setPriority(0);
        priceEntity.setPrice(new BigDecimal("35.50"));
        priceEntity.setCurrency("EUR");
    }

    @Test
    void shouldReturnPriceWhenFound() {
        // Given
        when(priceRepository.findAllMatchingPrices(1L, 35455L, priceEntity.getStartDate()))
                .thenReturn(List.of(priceEntity));

        // When
        Optional<Price> result = pricePersistenceAdapter.findPrice(1L, 35455L, priceEntity.getStartDate());

        // Then
        assertTrue(result.isPresent());
        assertEquals(priceEntity.getPrice(), result.get().getPrice());
        verify(priceRepository, times(1)).findAllMatchingPrices(1L, 35455L, priceEntity.getStartDate());
    }

    @Test
    void shouldReturnEmptyWhenNoPriceFound() {
        // Given
        when(priceRepository.findAllMatchingPrices(1L, 35455L, priceEntity.getStartDate()))
                .thenReturn(List.of());

        // When
        Optional<Price> result = pricePersistenceAdapter.findPrice(1L, 35455L, priceEntity.getStartDate());

        // Then
        assertFalse(result.isPresent());
        verify(priceRepository, times(1)).findAllMatchingPrices(1L, 35455L, priceEntity.getStartDate());
    }
}


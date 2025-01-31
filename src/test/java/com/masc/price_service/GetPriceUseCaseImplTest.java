package com.masc.price_service;

import com.masc.price_service.application.usecases.GetPriceUseCaseImpl;
import com.masc.price_service.domain.models.Price;
import com.masc.price_service.domain.ports.out.PriceRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPriceUseCaseImplTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private GetPriceUseCaseImpl getPriceUseCase;

    private Price price;

    @BeforeEach
    void setUp() {
        price = Price.builder()
                .id(1L)
                .brandId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
                .priceList(1)
                .productId(35455L)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();
    }

    @Test
    void shouldReturnPriceWhenFound() {
        // Given
        when(priceRepositoryPort.findPrice(1L, 35455L, price.getStartDate())).thenReturn(Optional.of(price));

        // When
        Optional<Price> result = getPriceUseCase.getPrice(1L, 35455L, price.getStartDate());

        // Then
        assertTrue(result.isPresent());
        assertEquals(price.getPrice(), result.get().getPrice());
        verify(priceRepositoryPort, times(1)).findPrice(1L, 35455L, price.getStartDate());
    }

    @Test
    void shouldReturnEmptyWhenPriceNotFound() {
        // Given
        when(priceRepositoryPort.findPrice(1L, 35455L, price.getStartDate())).thenReturn(Optional.empty());

        // When
        Optional<Price> result = getPriceUseCase.getPrice(1L, 35455L, price.getStartDate());

        // Then
        assertFalse(result.isPresent());
        verify(priceRepositoryPort, times(1)).findPrice(1L, 35455L, price.getStartDate());
    }
}

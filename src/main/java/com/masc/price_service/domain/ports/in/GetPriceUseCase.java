package com.masc.price_service.domain.ports.in;

import com.masc.price_service.domain.models.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GetPriceUseCase {

    Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime startDate);
}

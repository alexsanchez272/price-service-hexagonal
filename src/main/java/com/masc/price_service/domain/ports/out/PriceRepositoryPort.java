package com.masc.price_service.domain.ports.out;

import com.masc.price_service.domain.models.Price;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public interface PriceRepositoryPort {
    
    Optional<Price> findPrice(Long brandId, Long productId, LocalDateTime startDate);
}

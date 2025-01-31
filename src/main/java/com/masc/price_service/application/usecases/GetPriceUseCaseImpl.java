package com.masc.price_service.application.usecases;

import com.masc.price_service.domain.models.Price;
import com.masc.price_service.domain.ports.in.GetPriceUseCase;
import com.masc.price_service.domain.ports.out.PriceRepositoryPort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class GetPriceUseCaseImpl implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    public GetPriceUseCaseImpl(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public Optional<Price> getPrice(Long brandId, Long productId, LocalDateTime startDate) {
        return priceRepositoryPort.findPrice(brandId, productId, startDate);
    }
}

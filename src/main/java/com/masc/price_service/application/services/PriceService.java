package com.masc.price_service.application.services;

import com.masc.price_service.domain.exceptions.PriceNotFoundException;
import com.masc.price_service.domain.models.Price;
import com.masc.price_service.domain.ports.in.GetPriceUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class PriceService {

    private final GetPriceUseCase getPriceUseCase;

    public PriceService(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    public Price getPrice(Long brandId, Long productId, LocalDateTime startDate) {
        log.info("process = get_price, status=init,  brandId: {}, productId: {}, startDate: {}", brandId, productId, startDate);

        return getPriceUseCase.getPrice(brandId, productId, startDate)
                .orElseThrow(() -> {
                    log.error("process=get_price, status=not_found, brandId={}, productId={}, startDate={}",
                            brandId, productId, startDate);
                    return new PriceNotFoundException("Price not found for brandId: " + brandId +
                            ", productId: " + productId + ", date: " + startDate);
                });
    }

}

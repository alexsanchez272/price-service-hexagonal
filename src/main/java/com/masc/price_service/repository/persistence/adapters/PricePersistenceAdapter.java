package com.masc.price_service.repository.persistence.adapters;

import com.masc.price_service.domain.models.Price;
import com.masc.price_service.domain.ports.out.PriceRepositoryPort;
import com.masc.price_service.repository.persistence.entities.PriceEntity;
import com.masc.price_service.repository.persistence.repositories.PriceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Transactional
public class PricePersistenceAdapter implements PriceRepositoryPort {

    private final PriceRepository priceRepository;

    public PricePersistenceAdapter(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<Price> findPrice(Long brandId, Long productId, LocalDateTime startDate) {
        return priceRepository.findAllMatchingPrices(brandId, productId, startDate).stream()
                .findFirst()
                .map(PriceEntity::toDomainModel);
    }
}

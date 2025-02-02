package com.masc.price_service.infrastructure.persistence.repositories;

import com.masc.price_service.infrastructure.persistence.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {

    @Query("""
    SELECT p FROM PriceEntity p 
    WHERE p.brandId = :brandId 
      AND p.productId = :productId 
      AND :startDate BETWEEN p.startDate AND p.endDate 
    ORDER BY p.priority DESC 
    """)
    List<PriceEntity> findAllMatchingPrices(Long brandId, Long productId, LocalDateTime startDate);
}

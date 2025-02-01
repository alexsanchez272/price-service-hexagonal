package com.masc.price_service.repository.persistence.entities;

import com.masc.price_service.domain.models.Price;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICES")
@Data
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    private Integer priceList;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CURR")
    private String currency;

    public Price toDomainModel() {
        Price price = new Price();
        price.setId(this.id);
        price.setBrandId(this.brandId);
        price.setStartDate(this.startDate);
        price.setEndDate(this.endDate);
        price.setPriceList(this.priceList);
        price.setProductId(this.productId);
        price.setPriority(this.priority);
        price.setPrice(this.price);
        price.setCurrency(this.currency);
        return price;
    }
}

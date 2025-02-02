package com.masc.price_service.infrastructure.web;

import com.masc.price_service.application.services.PriceService;
import com.masc.price_service.domain.models.Price;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Price", description = "The Price API")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(summary = "Get applicable price",
            description = "Retrieves the applicable price for a product under a specific brand at a given date based on priority rules.")
    @GetMapping
    public ResponseEntity<Price> getPrice(
            @Parameter(description = "ID of the brand (e.g., 1 for ZARA)", example = "1")
            @RequestParam @NotNull @Min(1) Long brandId,

            @Parameter(description = "ID of the product to retrieve the price for", example = "35455")
            @RequestParam @NotNull @Min(1) Long productId,

            @Parameter(description = "Application date (format: yyyy-MM-dd HH:mm:ss)", example = "2020-06-14 10:00:00")
            @RequestParam @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate) {

        Price response = priceService.getPrice(brandId, productId, startDate);
        return ResponseEntity.ok(response);
    }
}

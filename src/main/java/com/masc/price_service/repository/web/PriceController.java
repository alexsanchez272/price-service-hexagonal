package com.masc.price_service.repository.web;

import com.masc.price_service.application.services.PriceService;
import com.masc.price_service.domain.models.Price;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
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

            @Parameter(description = "Application date for the price retrieval (format: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {

        Price response = priceService.getPrice(brandId, productId, startDate);
        return ResponseEntity.ok(response);
    }
}

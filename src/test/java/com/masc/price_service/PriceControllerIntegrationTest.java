package com.masc.price_service;

import com.masc.price_service.repository.persistence.entities.PriceEntity;
import com.masc.price_service.repository.persistence.repositories.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    @BeforeEach
    void setUp() {
        priceRepository.deleteAll();

        PriceEntity price = new PriceEntity();
        price.setBrandId(1L);
        price.setStartDate(LocalDateTime.of(2023, 6, 14, 0, 0));
        price.setEndDate(LocalDateTime.of(2023, 6, 14, 23, 59));
        price.setPriceList(1);
        price.setProductId(35455L);
        price.setPriority(0);
        price.setPrice(new BigDecimal("35.50"));
        price.setCurrency("EUR");

        priceRepository.save(price);
    }

    @Test
    void getPriceTest() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("startDate", "2023-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @Test
    void getPriceNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("startDate", "2023-06-15T10:00:00"))
                .andExpect(status().isNotFound());
    }
}

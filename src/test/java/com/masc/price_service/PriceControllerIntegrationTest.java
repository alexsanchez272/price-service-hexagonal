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

        priceRepository.save(createPriceEntity(1L, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35455L, 0, new BigDecimal("35.50")));

        priceRepository.save(createPriceEntity(1L, 2,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                35455L, 1, new BigDecimal("25.45")));

        priceRepository.save(createPriceEntity(1L, 3,
                LocalDateTime.of(2020, 6, 15, 0, 0),
                LocalDateTime.of(2020, 6, 15, 11, 0),
                35455L, 1, new BigDecimal("30.50")));

        priceRepository.save(createPriceEntity(1L, 4,
                LocalDateTime.of(2020, 6, 15, 16, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                35455L, 1, new BigDecimal("38.95")));
    }

    private PriceEntity createPriceEntity(Long brandId, Integer priceList, LocalDateTime startDate, LocalDateTime endDate,
                                          Long productId, Integer priority, BigDecimal price) {
        PriceEntity entity = new PriceEntity();
        entity.setBrandId(brandId);
        entity.setPriceList(priceList);
        entity.setStartDate(startDate);
        entity.setEndDate(endDate);
        entity.setProductId(productId);
        entity.setPriority(priority);
        entity.setPrice(price);
        entity.setCurrency("EUR");
        return entity;
    }

    @Test
    void shouldReturnNotFoundStatusWhenProductIdIsInvalid() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35456")
                        .param("startDate", "2020-06-16 21:00:00"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldReturnPriceList1When_RequestedAt10AMOnJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("startDate", "2020-06-14 10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void shouldReturnPriceList2When_RequestedAt4PMOnJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("startDate", "2020-06-14 16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    void shouldReturnPriceList1When_RequestedAt9PMOnJune14() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("startDate", "2020-06-14 21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void shouldReturnPriceList3When_RequestedAt9PMOnJune16() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("startDate", "2020-06-15 10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    void shouldReturnPriceList4When_RequestedAt9PMJune16() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("startDate", "2020-06-16 21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }
}

package com.viewnext.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.viewnext.application.port.in.GetAllPricesQuery;
import com.viewnext.domain.model.Price;

public class PriceControllerTest {

    @Mock
    private GetAllPricesQuery getAllPricesQuery;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPrices() {
        // Given
    	Price price = new Price.Builder()
                .id(1L)
                .brandId(1L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1)
                .productId(35455L)
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        List<Price> prices = Arrays.asList(price);

        // When
        when(getAllPricesQuery.getAllPrices()).thenReturn(prices);

        List<Price> result = priceController.getAllPrices();

        // Then
        assertEquals(prices, result);
    }
}

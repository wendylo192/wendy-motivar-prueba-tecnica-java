package com.viewnext.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.viewnext.application.port.out.LoadPricesPort;
import com.viewnext.application.port.out.PriceRepositoryPort;
import com.viewnext.domain.exception.PriceNotFoundException;
import com.viewnext.domain.model.Price;

class PriceServiceTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @Mock
    private LoadPricesPort loadPricesPort;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPriceById_whenPriceExists() {
        Price price = new Price.Builder()
				.id(1L)
				.brandId(1L)
				.startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
				.endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
				.priceList(1)
				.productId(35455L)
				.priority(0)
				.price(BigDecimal.valueOf(35.50))
				.currency("EUR")
				.build();	 
        when(priceRepositoryPort.findById(1L)).thenReturn(Optional.of(price));

        Price result = priceService.getPriceById(1L);

        assertNotNull(result);
        assertEquals(price, result);
        verify(priceRepositoryPort).findById(1L);
    }

    @Test
    void testGetPriceById_whenPriceDoesNotExist() {
        when(priceRepositoryPort.findById(1L)).thenReturn(Optional.empty());

        Price result = priceService.getPriceById(1L);

        assertNull(result);
        verify(priceRepositoryPort).findById(1L);
    }

    @Test
    void testGetAllPrices() {
        Price price3550 = new Price.Builder()
				.id(1L)
				.brandId(1L)
				.startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
				.endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
				.priceList(1)
				.productId(35455L)
				.priority(0)
				.price(BigDecimal.valueOf(35.50))
				.currency("EUR")
				.build();	
        Price price2550 = new Price.Builder()
				.id(2L)
				.brandId(1L)
				.startDate(LocalDateTime.of(2020, 6, 14, 15, 0, 0))
				.endDate(LocalDateTime.of(2020, 6, 14, 18, 30, 0))
				.priceList(2)
				.productId(35455L)
				.priority(1)
				.price(BigDecimal.valueOf(25.45))
				.currency("EUR")
				.build();	 
        List<Price> prices = List.of(price3550, price2550);
        when(loadPricesPort.findAll()).thenReturn(prices);

        List<Price> result = priceService.getAllPrices();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(price3550));
        assertTrue(result.contains(price2550));
        verify(loadPricesPort).findAll();
    }

    @Test
    void testGetPriceForApplication_whenPriceExists() {
        Price price = new Price.Builder()
				.id(1L)
				.brandId(1L)
				.startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
				.endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
				.priceList(1)
				.productId(35455L)
				.priority(0)
				.price(BigDecimal.valueOf(35.50))
				.currency("EUR")
				.build();
        
        when(priceRepositoryPort.findApplicablePrice(35455L, 1L, LocalDateTime.of(2020, 6, 14, 10, 0, 0)))
            .thenReturn(Optional.of(price));

        Price result = priceService.getPriceForApplication(35455L, 1L, LocalDateTime.of(2020, 6, 14, 10, 0, 0));

        assertNotNull(result);
        assertEquals(price, result);
        verify(priceRepositoryPort).findApplicablePrice(35455L, 1L, LocalDateTime.of(2020, 6, 14, 10, 0, 0));
    }

    @Test
    void testGetPriceForApplication_whenPriceDoesNotExist() {
        when(priceRepositoryPort.findApplicablePrice(35455L, 1L, LocalDateTime.of(2024, 6, 14, 10, 0, 0)))
            .thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () ->
            priceService.getPriceForApplication(35455L, 1L, LocalDateTime.of(2024, 6, 14, 10, 0, 0))
        );
        verify(priceRepositoryPort).findApplicablePrice(35455L, 1L, LocalDateTime.of(2024, 6, 14, 10, 0, 0));
    }
}

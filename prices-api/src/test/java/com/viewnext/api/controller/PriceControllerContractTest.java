package com.viewnext.api.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewnext.api.dto.PriceRequest;
import com.viewnext.api.dto.PriceResponse;
import com.viewnext.application.port.in.GetAllPricesQuery;
import com.viewnext.application.port.in.GetPriceForApplicationQuery;
import com.viewnext.domain.model.Price;
import com.viewnext.mapper.PriceResponseMapper;

@WebMvcTest(PriceController.class)
public class PriceControllerContractTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetAllPricesQuery getAllPricesQuery;

    @MockBean
    private GetPriceForApplicationQuery getPriceForApplicationQuery;

    @MockBean
    private PriceResponseMapper priceResponseMapper;

    @BeforeEach
    void setup() {
        Price mockPrice = new Price.Builder()
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

        PriceResponse mockResponse = new PriceResponse(35455L, 1L, 1,
            LocalDateTime.of(2020, 6, 14, 0, 0, 0),
            LocalDateTime.of(2020, 12, 31, 23, 59, 59), new BigDecimal("35.50"));

        when(getPriceForApplicationQuery.getPriceForApplication(any(Long.class), any(Long.class), any(LocalDateTime.class)))
            .thenReturn(mockPrice);

        when(priceResponseMapper.toPriceResponse(mockPrice))
            .thenReturn(mockResponse);

        when(getAllPricesQuery.getAllPrices())
            .thenReturn(Collections.singletonList(mockPrice));
    }

    @Test
    public void testGetPriceForApplication() throws Exception {
        PriceRequest request = new PriceRequest();
        request.setProductId(35455L);
        request.setBrandId(1L);
        request.setApplicationDate(LocalDateTime.of(2020, 6, 14, 10, 0, 0));
        
        mockMvc.perform(post("/prices/v1/api/price")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId", is(1)))
                .andExpect(jsonPath("$.priceList", is(1)))
                .andExpect(jsonPath("$.productId", is(35455)))
                .andExpect(jsonPath("$.startDate", is("2020-06-14T00:00:00")))
                .andExpect(jsonPath("$.endDate", is("2020-12-31T23:59:59")))
                .andExpect(jsonPath("$.price", is(35.50)));
    }

    @Test
    public void testGetAllPrices() throws Exception {
        mockMvc.perform(get("/prices/v1/api/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].brandId", is(1)))
                .andExpect(jsonPath("$[0].priceList", is(1)))
                .andExpect(jsonPath("$[0].productId", is(35455)))
                .andExpect(jsonPath("$[0].startDate", is("2020-06-14T00:00:00")))
                .andExpect(jsonPath("$[0].endDate", is("2020-12-31T23:59:59")))
                .andExpect(jsonPath("$[0].price", is(35.50)))
                .andExpect(jsonPath("$[0].currency", is("EUR")));
    }
}

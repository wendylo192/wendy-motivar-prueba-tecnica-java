package com.viewnext.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.api.dto.PriceRequest;
import com.viewnext.api.dto.PriceResponse;
import com.viewnext.application.port.in.GetAllPricesQuery;
import com.viewnext.application.port.in.GetPriceForApplicationQuery;
import com.viewnext.domain.model.Price;
import com.viewnext.mapper.PriceResponseMapper;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final GetAllPricesQuery getAllPricesQuery;
    private final GetPriceForApplicationQuery getPriceForApplicationQuery;
    private final PriceResponseMapper priceResponseMapper;

    public PriceController(GetAllPricesQuery getAllPricesQuery, GetPriceForApplicationQuery getPriceForApplicationQuery, PriceResponseMapper priceResponseMapper) {
        this.getAllPricesQuery = getAllPricesQuery;
        this.getPriceForApplicationQuery = getPriceForApplicationQuery;
        this.priceResponseMapper = priceResponseMapper;
    }
    
	@GetMapping
	public ResponseEntity<String> hello(){
		return ResponseEntity.ok("Welcome to Prices Service!");
	}

    @GetMapping("/v1/api/all")
    public List<Price> getAllPrices() {
        return getAllPricesQuery.getAllPrices();
    }
    
	@PostMapping("/v1/api/price")
	public ResponseEntity<PriceResponse> getPriceForApplication(@RequestBody PriceRequest request) {
	    Price price = getPriceForApplicationQuery.getPriceForApplication(
	        request.getProductId(), request.getBrandId(), request.getApplicationDate()
	    );

	    PriceResponse response = priceResponseMapper.toPriceResponse(price);
	    return ResponseEntity.ok(response);
	}

}
package com.viewnext.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.viewnext.application.port.in.GetAllPricesQuery;
import com.viewnext.application.port.in.GetPriceForApplicationQuery;
import com.viewnext.application.port.in.GetPriceQuery;
import com.viewnext.application.port.out.LoadPricesPort;
import com.viewnext.application.port.out.PriceRepositoryPort;
import com.viewnext.domain.exception.PriceNotFoundException;
import com.viewnext.domain.model.Price;

@Service
public class PriceService implements GetPriceQuery, GetAllPricesQuery, GetPriceForApplicationQuery {

    private final PriceRepositoryPort priceRepositoryPort;
    private final LoadPricesPort loadPricesPort;

    public PriceService(PriceRepositoryPort priceRepositoryPort, LoadPricesPort loadPricesPort) {
        this.priceRepositoryPort = priceRepositoryPort;
        this.loadPricesPort = loadPricesPort;
    }

    @Override
    public Price getPriceById(Long id) {
        return priceRepositoryPort.findById(id).orElse(null);
    }

	@Override
	public List<Price> getAllPrices() {
        return loadPricesPort.findAll();
    }
	
    @Override
    public Price getPriceForApplication(Long productId, Long brandId, LocalDateTime applicationDate) {
        Optional<Price> price = priceRepositoryPort.findApplicablePrice(productId, brandId, applicationDate);
        
        return price.orElseThrow(() -> new PriceNotFoundException());
    }
}
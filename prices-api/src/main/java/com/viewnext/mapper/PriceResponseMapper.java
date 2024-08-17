package com.viewnext.mapper;

import org.springframework.stereotype.Component;

import com.viewnext.api.dto.PriceResponse;
import com.viewnext.domain.model.Price;

@Component
public class PriceResponseMapper {

    public PriceResponse toPriceResponse(Price price) {
        return new PriceResponse(
            price.getProductId(),
            price.getBrandId(),
            price.getPriceList(),	
            price.getStartDate(),
            price.getEndDate(),
            price.getPrice()
        );
    }
}
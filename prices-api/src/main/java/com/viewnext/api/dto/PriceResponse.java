package com.viewnext.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceResponse {

    private final Long productId;
    private final Long brandId;
    private final Integer priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final BigDecimal price;

    public PriceResponse(Long productId, Long brandId, Integer priceList, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price) {
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

	public Long getProductId() {
		return productId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public Integer getPriceList() {
		return priceList;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public BigDecimal getPrice() {
		return price;
	}
    
    
}

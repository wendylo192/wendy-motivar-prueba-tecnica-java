package com.viewnext.application.port.in;

import com.viewnext.domain.model.Price;

public interface GetPriceQuery {
	Price getPriceById(Long id);
}

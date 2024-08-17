package com.viewnext.application.port.in;

import java.time.LocalDateTime;
import com.viewnext.domain.model.Price;

public interface GetPriceForApplicationQuery {
    Price getPriceForApplication(Long productId, Long brandId, LocalDateTime applicationDate);
}
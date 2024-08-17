package com.viewnext.application.port.out;

import java.time.LocalDateTime;
import java.util.Optional;

import com.viewnext.domain.model.Price;

public interface PriceRepositoryPort {
    Optional<Price> findById(Long id);
    Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate);
}

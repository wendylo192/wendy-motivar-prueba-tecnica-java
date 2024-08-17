package com.viewnext.infrastructure.mapper;

import com.viewnext.domain.model.Price;
import com.viewnext.infrastructure.entity.PriceEntity;

public interface PriceMapper {

    PriceEntity toEntity(Price price);

    Price toDomain(PriceEntity priceEntity);
}
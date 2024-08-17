package com.viewnext.application.port.in;

import com.viewnext.domain.model.Price;

import java.util.List;

public interface GetAllPricesQuery {
    List<Price> getAllPrices();
}
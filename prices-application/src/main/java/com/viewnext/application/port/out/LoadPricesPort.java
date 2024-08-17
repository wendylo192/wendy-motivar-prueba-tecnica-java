package com.viewnext.application.port.out;

import com.viewnext.domain.model.Price;

import java.util.List;

public interface LoadPricesPort {
    List<Price> findAll();
}
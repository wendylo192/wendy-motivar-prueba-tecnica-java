package com.viewnext.api.controller;

import static com.viewnext.prices.utils.ErrorCatalog.GENERIC_ERROR;
import static com.viewnext.prices.utils.ErrorCatalog.INVALID_SEARCH_INPUT;
import static com.viewnext.prices.utils.ErrorCatalog.PRICE_NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.viewnext.domain.exception.PriceNotFoundException;
import com.viewnext.domain.model.PriceErrorResponse;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseBody
    public PriceErrorResponse handlePriceNotFoundException() {
        return new PriceErrorResponse(PRICE_NOT_FOUND.getCode(), PRICE_NOT_FOUND.getMessage(), LocalDateTime.now());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public PriceErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        
        List<String> errorDetails = result.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new PriceErrorResponse(INVALID_SEARCH_INPUT.getCode(),INVALID_SEARCH_INPUT.getMessage(), Optional.of(errorDetails), LocalDateTime.now());
    }

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PriceErrorResponse handleGenericError(Exception exception) {
        return new PriceErrorResponse(GENERIC_ERROR.getCode(), GENERIC_ERROR.getMessage(), Optional.of(List.of(exception.getMessage())), LocalDateTime.now());
    }
}

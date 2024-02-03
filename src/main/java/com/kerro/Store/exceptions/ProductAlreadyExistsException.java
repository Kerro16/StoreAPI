package com.kerro.Store.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String message){
        super(message);
    }
}

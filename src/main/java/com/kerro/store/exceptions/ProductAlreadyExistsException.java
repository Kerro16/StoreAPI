package com.kerro.store.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String message){
        super(message);
    }
}

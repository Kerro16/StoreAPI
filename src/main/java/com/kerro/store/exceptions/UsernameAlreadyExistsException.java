package com.kerro.store.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{

    public UsernameAlreadyExistsException (String message){
        super(message);
    }
}

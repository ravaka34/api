package com.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST )
public class WrongValueException extends Exception{

    public WrongValueException(String message) {
        super(message);
    }
    
}

package com.auction.exception;

public class AuthorizationException extends Exception{
    public AuthorizationException(){
        super("You don't have authorization");
    }
}

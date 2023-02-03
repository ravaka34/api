package com.auction.exception;

public class AuthenticationException extends Exception{
    public AuthenticationException(){
        super("You are not connnected");
    }
}

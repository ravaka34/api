package com.auction.exception;

public class BetOnSelfException extends Exception {

    public BetOnSelfException(){
        super("You can not bet your own auction");
    }
}

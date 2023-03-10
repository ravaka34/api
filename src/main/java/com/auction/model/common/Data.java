package com.auction.model.common;

import org.springframework.http.HttpStatus;

public class Data {
    private Object data;

    public Data(Object data) {
        this.data = data;
    }

    public Data(Object data, HttpStatus status){
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

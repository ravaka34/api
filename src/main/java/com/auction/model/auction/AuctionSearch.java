package com.auction.model.auction;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionSearch {

    private String motCle;
    private Date minDate;
    private Date maxDate;
    private Double minAmount;
    private Double maxAmount;
    private Integer status;
    private Integer category;
    
}

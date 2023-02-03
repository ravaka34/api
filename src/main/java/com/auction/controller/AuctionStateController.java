package com.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auction.model.auction.AuctionSearch;
import com.auction.model.common.Data;
import com.auction.service.AuctionStateService;

@RestController
@CrossOrigin
public class AuctionStateController {

    @Autowired
    AuctionStateService service;


    @GetMapping("/client/{clientId}/auction-state")
    public Data findByClientId(@PathVariable Integer clientId){
        System.out.println("client id ==> " + clientId);
        return new Data(service.findByClientId(clientId)) ;
    }

    @GetMapping("/auction-state/category/{categoryId}")
    public Data findByCategoryId(@PathVariable Integer categoryId) {
        return new Data(service.findByCategoryId(categoryId), HttpStatus.OK);
    }

    @PostMapping("/auction-state/search")
    public Data searchAuction(@RequestBody AuctionSearch auctionSearch) {
        return new Data(service.search(auctionSearch));
    }
}

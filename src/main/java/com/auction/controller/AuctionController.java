package com.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auction.exception.AuthenticationException;
import com.auction.exception.BetOnSelfException;
import com.auction.exception.WrongValueException;
import com.auction.model.Client;
import com.auction.model.auction.AuctionBet;
import com.auction.model.auction.AuctionSearch;
import com.auction.model.common.Data;
import com.auction.model.auction.Auction;
import com.auction.service.AuctionService;
import com.auction.service.login.FoTokenService;

@RestController
@CrossOrigin
public class AuctionController {

    @Autowired
    AuctionService service;

    @Autowired
    protected FoTokenService fs;

    @GetMapping("/auction/search")
    public Data searchAuction(@RequestBody AuctionSearch auctionSearch) {
        return new Data(service.search(auctionSearch));
    }

    @PostMapping("/auction/{id}/bet")
    public Data betOn(@PathVariable Integer id, @RequestParam String token,
            @RequestParam Integer clientId, @RequestBody AuctionBet auctionBet) throws AuthenticationException, BetOnSelfException, WrongValueException {
        if (!fs.isConnected(token, clientId)) {
            throw new AuthenticationException();
        }
        // TODO implement token verification
        auctionBet.setAuctionId(id);
        Client client = new Client();
        client.setId(clientId);
        auctionBet.setClient(client);
        return new Data (service.betOn(auctionBet), HttpStatus.CREATED);
    }

    @GetMapping("/auctions")
    public Data findCurrentAuctions() {
        return new Data(service.findCurrentAuctions());
    }

    @GetMapping("/client/{clientId}/auction")
    public Data findByClientid(@PathVariable int clientId) {
        return new Data(service.findByIdClient(clientId));
    }

    @GetMapping("/auction/{id}")
    public Data findById(@PathVariable int id)
            throws AuthenticationException {
        return new Data(service.findById(id));
    }

    @PostMapping("/auction")
    public Data save(@RequestBody Auction auction, @RequestParam String token, @RequestParam Integer clientId) throws AuthenticationException, WrongValueException {
        if (!fs.isConnected(token, clientId)) {
            throw new AuthenticationException();
        }
        return new Data(service.save(auction), HttpStatus.CREATED);
    }

    @GetMapping("/auctions/category/{categoryId}")
    public Data findByCategoryId(@PathVariable int categoryId) {
        return new Data(service.findByCategoryId(categoryId), HttpStatus.OK);
    }
}

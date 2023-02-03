package com.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auction.model.balance.BalanceLoadRequest;
import com.auction.model.common.Data;
import com.auction.service.BalanceService;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@CrossOrigin
public class BalanceController {
    @Autowired
    private BalanceService balanceService;


    @GetMapping("/balance/load-request")
    public Data getAllBLRequest() {
        return new Data(balanceService.getAllBLRequest());
    }

    @PostMapping("/balance/treat-load-request")
    public Data treatBalanceRequest(@RequestBody BalanceLoadRequest balanceLoadRequest) {
        return new Data(balanceService.treatBalanceRequest(balanceLoadRequest.getId(), balanceLoadRequest.getState().getId()),
         HttpStatus.CREATED);
    }

    @PostMapping("/balance/reload-account")
    public Data createReloadAccount(@RequestBody BalanceLoadRequest balanceLoadRequest) {
        return new Data(balanceService.reloadAccount(balanceLoadRequest), HttpStatus.CREATED);
    }

    @GetMapping("/client/{id}/balance")
    public Data getBalance(@PathVariable Integer id) {
        return new Data(balanceService.getBalance(id), HttpStatus.CREATED);
    }

}

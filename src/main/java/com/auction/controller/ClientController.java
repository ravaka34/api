package com.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auction.exception.AuthenticationException;
import com.auction.model.Client;
import com.auction.model.ClientInscription;
import com.auction.model.common.Data;
import com.auction.repository.login.FoTokenAuthRepo;
import com.auction.service.login.ClientService;
import com.auction.service.login.FoTokenService;

@RestController
@CrossOrigin
@RequestMapping("/client")
public class ClientController {

    @Autowired
    protected ClientService cs;
    @Autowired
    protected FoTokenService fs;
    @Autowired
    protected FoTokenAuthRepo ftap;

    @PostMapping
    public Data insertClient(@RequestBody ClientInscription client){
        return new Data(cs.saveClient(client));
    }

    @PutMapping
    public void updateClient(@RequestParam Integer id,@RequestParam String token,@RequestBody Client client) throws AuthenticationException{
        if(!fs.isConnected(token, id)){
            throw new AuthenticationException();
        }
        cs.updateClient(id, client);
    }

    @DeleteMapping
    public void deleteClient(@RequestParam Integer id,@RequestParam String token) throws AuthenticationException{
        if(fs.isConnected(token, id)){
            throw new AuthenticationException();
        }
        ftap.deleteAllToken(id);
        cs.deleteClient(id);
    }

}

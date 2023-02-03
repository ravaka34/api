package com.auction.controller;

import org.springframework.web.bind.annotation.RestController;

import com.auction.exception.AuthenticationException;
import com.auction.exception.AuthorizationException;
import com.auction.model.common.Data;
import com.auction.model.login.Administrator;
import com.auction.service.login.AdministratorService;
import com.auction.service.login.BoTokenService;
import com.auction.service.login.RootService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    AdministratorService as;
    @Autowired
    BoTokenService bs;
    @Autowired
    RootService rs;

    @PostMapping
    public void insertAdministrator(@RequestParam String token,@RequestParam Integer id,@RequestBody Administrator admin) throws AuthenticationException, AuthorizationException{
        boolean controlOne=bs.isConnected(token, id);
        boolean controlTwo=rs.isRoot(id);
        if(controlOne){
            throw new AuthenticationException();
        }
        if(!controlTwo){
            throw new AuthorizationException();
        }
        as.insertAdministrator(admin);
    }

    @PutMapping("/{id}")
    public void updateAdministrator(@PathVariable("id") Integer id,@RequestParam Integer idO,@RequestParam String token,@RequestBody Administrator admin) throws AuthorizationException, AuthenticationException{
        if(id==idO){
            boolean controlOne=bs.isConnected(token, idO);
            if(controlOne){
                throw new AuthenticationException();
            }
            as.updateAdministrator(id, admin);
        }else{
            throw new AuthorizationException();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrator(@PathVariable("id") Integer id,@RequestParam Integer idO,@RequestParam String token) throws AuthenticationException, AuthorizationException{
        boolean controlOne=bs.isConnected(token, idO);
        boolean controlTwo=rs.isRoot(idO);
        if(controlOne){
            throw new AuthenticationException();
        }
        if(!controlTwo){
            throw new AuthorizationException();
        }
        as.deleteAdministrator(id);
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable("id") Integer id,@RequestParam Integer idO,@RequestParam String token) throws AuthenticationException{
        if(bs.isConnected(token, idO)){
            throw new AuthenticationException();
        }
        as.deleteClient(id);
    }

    @GetMapping("/dashboard")
    //tableau de bord
    public Data getDashboard(@RequestParam String token,@RequestParam Integer id) throws AuthenticationException{
        if(!bs.isConnected(token, id)){
            throw new AuthenticationException();
        }
        return new Data(as.getDashboard());
    }

}

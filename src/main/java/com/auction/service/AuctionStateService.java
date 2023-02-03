package com.auction.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.model.auction.AuctionSearch;
import com.auction.model.auction.AuctionState;
import com.auction.model.category.Category;
import com.auction.repository.auction.AuctionStateRepository;
import java.util.Collections;
import java.util.Comparator;
import com.auction.model.auction.AuctionBet;

@Service
public class AuctionStateService {

    @Autowired
    private AuctionStateRepository repo;

    public List<AuctionState> findByClientId(Integer clientId){
        return repo.findByClientId(clientId);
    }

    public List<AuctionState> findByCategoryId(int categoryId) {
        return repo.findByCategoryId(categoryId);
        
    }

    public AuctionState findById(Integer id) {
         AuctionState auction = repo.findById(id).get();
         Collections.sort(auction.getAuctionBetList() , Comparator.comparing(AuctionBet::getAmount).reversed());
         return auction;
    }

    public List<AuctionState> search(AuctionSearch search) {
        String motCle = (search.getMotCle() == null) ? "" : search.getMotCle();
        motCle = motCle.toUpperCase();
        Date minDate = (search.getMinDate() == null) ? Date.valueOf("2000-01-01") : search.getMinDate();
        Date maxDate = (search.getMaxDate() == null) ? Date.valueOf("2030-01-01") : search.getMaxDate();
        Double minAmount = (search.getMinAmount() == null) ? 0 : search.getMinAmount();
        Double maxAmount = (search.getMaxAmount() == null) ? Double.MAX_VALUE : search.getMaxAmount();
        Integer status1 = 1;
        Integer status2 = 0;
        if(search.getStatus() != null){
            status1 = search.getStatus();
            status2 = search.getStatus();
        }
        Category cat = new Category();
        cat.setId(search.getCategory());
        return repo.search(cat, motCle, minDate, maxDate, minAmount, maxAmount, status1, status2);
    }
    
}

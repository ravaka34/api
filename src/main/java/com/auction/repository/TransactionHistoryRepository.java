package com.auction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.auction.model.transaction.TransactionHistory;
import com.auction.model.Client;
import java.util.List;

public interface TransactionHistoryRepository extends MongoRepository<TransactionHistory, Integer> {

    public List<TransactionHistory> findByClient(Client client);
    
}

package com.auction.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.auction.model.transaction.TransactionHistory;

public interface TransactionHistoryRepository extends MongoRepository<TransactionHistory, Integer> {
    
}

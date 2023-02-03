package com.auction.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.model.transaction.TransactionHistory;
import com.auction.repository.TransactionHistoryRepository;
import com.auction.model.Client;

@Service
public class TransactionHistoryService {
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    public List<TransactionHistory> findByClientId(Integer clientId) {
        // Client client = new Client();
        // client.setId(clientId);
        // List<TransactionHistory> transactionsClient = transactionHistoryRepository.findByClient(client);   
        List<TransactionHistory> transactionsClient = new ArrayList<TransactionHistory>();
        List<TransactionHistory> transactions = transactionHistoryRepository.findAll();
        for (TransactionHistory transactionHistory: transactions) {
            if (transactionHistory.getClient() != null) {
                System.out.println(transactionHistory.getClient().getId() + " = client id history");
                System.out.println(clientId + " = client id");
                if (transactionHistory.getClient().getId() == clientId) {
                    transactionsClient.add(transactionHistory);
                }
            }
        }

        //Collections.sort(transactionsClient, Comparator.comparing(TransactionHistory::getDateTransaction).reversed());
        return transactionsClient;

    }
}

package com.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.model.Client;


public interface ClientRepository  extends JpaRepository<Client, Integer> {
}

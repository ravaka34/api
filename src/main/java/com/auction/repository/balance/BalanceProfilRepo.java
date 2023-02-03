package com.auction.repository.balance;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.model.balance.BalanceProfil;

public interface BalanceProfilRepo extends JpaRepository<BalanceProfil, Integer> {
    
    public BalanceProfil findByClientId(Integer id);
}

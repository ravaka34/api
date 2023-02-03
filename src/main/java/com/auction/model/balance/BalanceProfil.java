package com.auction.model.balance;

import javax.persistence.Entity;
import com.auction.model.common.HasId;
import javax.persistence.Column;

import lombok.Setter;
import lombok.Getter;

@Entity(name = "v_balance")
@Getter
@Setter
public class BalanceProfil extends HasId {
    @Column(name = "client_id")
    private Integer clientId;
    @Column(name = "amount")
    private Double amount;
}

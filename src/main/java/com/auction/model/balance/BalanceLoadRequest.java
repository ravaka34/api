package com.auction.model.balance;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.auction.exception.WrongValueException;
import com.auction.model.Client;
import com.auction.model.common.HasId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class BalanceLoadRequest extends HasId {
    @ManyToOne
    private Client client;

    @ManyToOne
    private BalanceState state;

    @Column(insertable = false)
    private Date requestDate;

    private Date treatmentDate;
    private String contact;
    private Double amount;

    public void setClient(Client client) {
        this.client = client;
    }
    public void setState(BalanceState state) {
        this.state = state;
    }
    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }
    public void setContact(String contact) throws WrongValueException {
        if(contact == ""){
            throw new WrongValueException("Check your contact");
        }
        this.contact = contact;
    }
    public void setAmount(Double amount) throws WrongValueException {
        if(amount <= 0){
            throw new WrongValueException("Amount can not be null or negative");
        }
        this.amount = amount;
    }
}
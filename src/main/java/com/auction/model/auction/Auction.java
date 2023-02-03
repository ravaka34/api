package com.auction.model.auction;

import java.beans.Transient;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


import com.auction.exception.WrongValueException;
import com.auction.model.Client;
import com.auction.model.ProductPicture;
import com.auction.model.category.Category;
import com.auction.model.common.HasId;

import lombok.Getter;

@Getter
@Entity
public class Auction extends HasId {

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "depository_date")
    private Timestamp depositoryDate;

    @Column(name = "duration")
    private Double duration;

    @Column(name = "amount_min")
    private Double amountMin;
    
    @Column(name = "comission")
    private Double comission;

    @Column(name = "is_notif_send")
    private Boolean isNotifSend;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "auction_id")
    private List<AuctionBet> auctionBetList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "auction_id")
    private List<ProductPicture> productPictureList;

    public AuctionBet retrieveLastAuctionBet() {
        List<AuctionBet> bets = getAuctionBetList();
        if(bets.size() == 0){
            return null;
        }
        Collections.sort(bets, Comparator.comparing(AuctionBet::getAmount).reversed());
        return bets.get(0);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public void setDepositoryDate(Timestamp depositoryDate) {
        this.depositoryDate = depositoryDate;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public void setAmountMin(Double amountMin) throws WrongValueException {
        if(amountMin < 0){
            throw new WrongValueException("Amount min can not be negative");
        }
        this.amountMin = amountMin;
    }

    public void setComission(Double comission) {
        this.comission = comission;
    }

    public void setAuctionBetList(List<AuctionBet> auctionBetList) {
        this.auctionBetList = auctionBetList;
    }

    public void setProductPictureList(List<ProductPicture> productPictureList) {
        this.productPictureList = productPictureList;
    }

    public void setIsNotifSend(Boolean isNotifSend) {
        this.isNotifSend = isNotifSend;
    }


    


}

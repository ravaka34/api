package com.auction.model.auction;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.auction.model.Client;
import com.auction.model.ProductPicture;
import com.auction.model.category.Category;
import com.auction.model.common.HasId;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "v_auction_state")
@Getter
@Setter
public class AuctionState extends HasId {

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

    @Column(name = "is_finished")
    private Integer isFinished;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "auction_id")
    private List<ProductPicture> productPictureList;   
}

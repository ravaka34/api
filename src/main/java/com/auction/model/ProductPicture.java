package com.auction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.auction.model.auction.Auction;
import com.auction.model.common.HasId;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ProductPicture extends HasId {

    @ManyToOne
    @JsonIgnore
    private Auction auction;

    @Column(name = "picture")
    private String picture;
}

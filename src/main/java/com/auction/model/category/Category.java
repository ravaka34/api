package com.auction.model.category;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.auction.exception.WrongValueException;
import com.auction.model.common.HasName;

import lombok.Getter;

@Getter
@Entity
public class Category extends HasName {
    private Double minDuration;
    private Double maxDuration;
    private String picture;
    private Double comission;

    @Transient
    private Double totalComission = 0.0;

    public void setMinDuration(Double minDuration) throws WrongValueException {
        if(minDuration == 0){
            throw new WrongValueException("Value of min duration is not accepted");
        }
        this.minDuration = minDuration;
    }

    public void setMaxDuration(Double maxDuration) throws WrongValueException {
        if(minDuration == 0){
            throw new WrongValueException("Value of max duration is not accepted");
        }
        this.maxDuration = maxDuration;
    }

    public void setPicture(String picture) throws WrongValueException {
        if(picture == null || picture.equals("")){
            throw new WrongValueException("Picture is required");
        }
        this.picture = picture;
    }

    public void setComission(Double comission) throws WrongValueException {
        if(comission <= 0 || comission > 100 ){
            throw new WrongValueException("Comission value is not accepted");
        }
        this.comission = comission;
    }

    public void setTotalComission(Double totalComission) {
        this.totalComission = totalComission;
    }

    
}

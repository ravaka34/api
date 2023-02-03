package com.auction.model;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.auction.exception.WrongValueException;
import com.auction.model.common.HasId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Client extends HasId {
    @Column
    private Integer genderId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @Column
    private String contact;

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBirthDate(Date birthDate) throws WrongValueException {
        if(birthDate.getTime() >= System.currentTimeMillis()){
            throw new WrongValueException("BirthDate value wrong");
        }
        this.birthDate = birthDate;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    
}

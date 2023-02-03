package com.auction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auction.exception.WrongValueException;
import com.auction.model.category.Category;
import com.auction.model.common.Data;
import com.auction.service.CategoryService;

@RestController
@CrossOrigin
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    Data create(@RequestBody Category category) throws WrongValueException {
        if (category.getMaxDuration() < category.getMinDuration()) {
            throw new WrongValueException("Maximum duration must be greather than the minimum duration");
        }
        return new Data(categoryService.create(category), HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}/duration")
    Data updateDuration(@RequestBody Category category, @PathVariable Integer id) throws WrongValueException {
        if (category.getMaxDuration() < category.getMinDuration()) {
            throw new WrongValueException("Maximum duration must be greather than the minimum duration");
        }
        category.setId(id);
        return new Data(categoryService.updateAuctionDuration(category));
    }

    @PutMapping("/category/{id}/comission")
    Data updateComission(@RequestBody Category category, @PathVariable Integer id) throws WrongValueException {
        category.setId(id);
        return new Data(categoryService.updateAuctionComission(category));
    }

    @GetMapping("/category")
    Data getAll() throws WrongValueException {    
        return new Data(categoryService.getAll());
    }

    @GetMapping("/category/{id}")
    Data findById(@PathVariable Integer id) throws WrongValueException {
        return new Data(categoryService.findById(id));
    }


    
}

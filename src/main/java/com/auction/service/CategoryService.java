package com.auction.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auction.exception.WrongValueException;
import com.auction.model.category.Category;
import com.auction.repository.category.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public boolean create(Category category) {
        categoryRepository.save(category);
        return true;
    }

    public boolean updateAuctionDuration(Category newCategory) throws WrongValueException {
        Category category = categoryRepository.findById(newCategory.getId()).get();
        category.setMaxDuration(newCategory.getMaxDuration());
        category.setMinDuration(newCategory.getMinDuration());
        categoryRepository.save(category);
        return true;
    }

    public boolean updateAuctionComission(Category newCategory) throws WrongValueException {
        Category category = categoryRepository.findById(newCategory.getId()).get();
        category.setComission(newCategory.getComission());
        categoryRepository.save(category);
        return true;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).get() ;
    }
}

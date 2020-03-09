/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.CategoryModel;
import com.hidetrace.admin.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repo;

    public List<CategoryModel> getAllCategories() {
        return repo.findAll();
    }

    @Transactional
    public CategoryModel save(CategoryModel model) {
        return repo.save(model);
    }

    @Transactional
    public void remove(CategoryModel catModel) {
        repo.delete(catModel);
    }

    public Optional<CategoryModel> getCategyById(int id) {
        return repo.findById(id);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.repository.HideTypeRepository;
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
public class HideTypeService {

    @Autowired
    private HideTypeRepository repo;

    @Transactional
    public List<HideTypeModel> getAllHideTypes() {
        return repo.findAll();
    }

    @Transactional
    public HideTypeModel save(HideTypeModel model) {
        return repo.save(model);
    }

    @Transactional
    public void remove(HideTypeModel hideTypeModel) {
        repo.delete(hideTypeModel);
    }

    public Optional<HideTypeModel> findById(int id) {
        return repo.findById(id);

    }
}

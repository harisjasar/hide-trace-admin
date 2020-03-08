/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeCategoryModel;
import com.hidetrace.admin.repository.IncomingInvoiceHideTypeCategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Service
public class IncomingInvoiceHideTypeCategoryService {

    @Autowired
    private IncomingInvoiceHideTypeCategoryRepository repo;

    @Transactional
    public List<IncomingInvoiceHideTypeCategoryModel> getAllIncomingInvoiceHideTypeCategory() {
        return repo.findAll();
    }

    @Transactional
    public IncomingInvoiceHideTypeCategoryModel saveIncomingInvoiceHideTypeCategory(IncomingInvoiceHideTypeCategoryModel model) {

        return repo.save(model);
    }

    @Transactional
    public List<IncomingInvoiceHideTypeCategoryModel> saveAll(List<IncomingInvoiceHideTypeCategoryModel> list) {
        try {
            repo.saveAll(list);
        } catch (Exception ex) {
            System.out.println(ex.getCause());
        }
        return null;
    }
}

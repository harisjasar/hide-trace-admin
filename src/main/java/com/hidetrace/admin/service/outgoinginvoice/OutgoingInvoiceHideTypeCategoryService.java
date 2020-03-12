/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.outgoinginvoice;

import com.hidetrace.admin.model.outgoinginvoice.OutgoingInvoiceHideTypeCategoryModel;
import com.hidetrace.admin.repository.outgoinginvoice.OutgoingInvoiceHideTypeCategoryRepository;
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
public class OutgoingInvoiceHideTypeCategoryService {

    @Autowired
    private OutgoingInvoiceHideTypeCategoryRepository repo;

    @Transactional
    public List<OutgoingInvoiceHideTypeCategoryModel> getAllOutgoingInvoiceHideTypeCategory() {
        return repo.findAll();
    }

    @Transactional
    public OutgoingInvoiceHideTypeCategoryModel saveOutgoingInvoiceHideTypeCategory(OutgoingInvoiceHideTypeCategoryModel model) {
        return repo.save(model);
    }

    @Transactional
    public List<OutgoingInvoiceHideTypeCategoryModel> saveAll(List<OutgoingInvoiceHideTypeCategoryModel> list) {
        return repo.saveAll(list);
    }

    @Transactional
    public List<OutgoingInvoiceHideTypeCategoryModel> findOutgoingInvoiceHideTypeCategoryByIncomingInvoiceId(int id) {
        return repo.findAllByOutgoingInvoiceId(id);
    }

    @Transactional
    public void removeSingle(OutgoingInvoiceHideTypeCategoryModel model) {
        repo.delete(model);
    }

    public Optional<OutgoingInvoiceHideTypeCategoryModel> findById(int id) {
        return repo.findById(id);
    }

}

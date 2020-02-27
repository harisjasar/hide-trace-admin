/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.incominginvoice;

import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeModel;
import com.hidetrace.admin.repository.incominginvoice.IncomingInvoiceHideTypeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Service
public class IncomingInvoiceHideTypeService {

    @Autowired
    IncomingInvoiceHideTypeRepository repo;

    @Transactional
    public IncomingInvoiceHideTypeModel saveIncomingInvoiceHideType(IncomingInvoiceHideTypeModel model) {
        return repo.save(model);
    }

    @Transactional
    public List<IncomingInvoiceHideTypeModel> findAllByIncomingInvoiceId(int id) {
        return repo.findAllByIncomingInvoiceId(id);
    }

    @Transactional
    public void removeHideTypes(List<IncomingInvoiceHideTypeModel> models) {
        repo.deleteAll(models);
    }
}

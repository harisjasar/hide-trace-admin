/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.IncomingInvoiceHideTypeModel;
import com.hidetrace.admin.repository.IncomingInvoiceHideTypeRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

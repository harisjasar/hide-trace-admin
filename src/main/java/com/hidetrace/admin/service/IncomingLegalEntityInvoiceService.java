/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.repository.IncomingLegalEntityInvoiceRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jashar
 */
@Service
public class IncomingLegalEntityInvoiceService {

    @Autowired
    IncomingLegalEntityInvoiceRepository repo;

    @Transactional
    public IncomingLegalEntityInvoiceModel saveIncomingInvoice(IncomingLegalEntityInvoiceModel model) {
        return repo.save(model);
    }
}

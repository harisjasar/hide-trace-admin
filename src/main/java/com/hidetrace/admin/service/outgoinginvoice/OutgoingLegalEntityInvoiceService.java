/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.outgoinginvoice;

import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.repository.outgoinginvoice.OutgoingLegalEntityInvoiceRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jashar
 */
@Service
public class OutgoingLegalEntityInvoiceService {

    @Autowired
    OutgoingLegalEntityInvoiceRepository repo;

    @Transactional
    public OutgoingLegalEntityInvoiceModel saveOutgoingInvoice(OutgoingLegalEntityInvoiceModel model) {
        return repo.save(model);
    }
}

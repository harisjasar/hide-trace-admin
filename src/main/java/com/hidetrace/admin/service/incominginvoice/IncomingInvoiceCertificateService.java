/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.incominginvoice;

import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.repository.incominginvoice.IncomingInvoiceCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Component
public class IncomingInvoiceCertificateService {

    @Autowired
    IncomingInvoiceCertificateRepository repo;

    @Transactional
    public IncomingInvoiceCertificateModel saveIncomingInvoiceCertificate(IncomingInvoiceCertificateModel model) {
        return repo.save(model);
    }

    @Transactional
    public IncomingInvoiceCertificateModel findByIncomingInvoiceId(int id) {
        return repo.findByIncomingInvoiceId(id);
    }

    @Transactional
    public void removeCertificate(IncomingInvoiceCertificateModel model) {
        repo.delete(model);
    }
}

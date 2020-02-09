/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.repository.IncomingInvoiceCertificateRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}

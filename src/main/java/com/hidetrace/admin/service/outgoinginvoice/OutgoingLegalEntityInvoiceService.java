/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.outgoinginvoice;

import com.hidetrace.admin.common.SaveException;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.repository.outgoinginvoice.OutgoingLegalEntityInvoiceRepository;
import javax.swing.JOptionPane;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

/**
 *
 * @author Jashar
 */
@Service
public class OutgoingLegalEntityInvoiceService {

    @Autowired
    OutgoingLegalEntityInvoiceRepository repo;

    @Autowired
    SaveException exception;

    @Transactional
    public OutgoingLegalEntityInvoiceModel saveOutgoingInvoice(OutgoingLegalEntityInvoiceModel model) {
        try {
            return repo.save(model);
        } catch (UnexpectedRollbackException | DataIntegrityViolationException e) {
            if (!exception.isRaised()) {
                JOptionPane.showMessageDialog(null, "Greška!\nFaktura sa istim imenom već postoji!", "Greška", JOptionPane.ERROR_MESSAGE);
                exception.setRaised(true);
            }
        }
        return null;
    }
}
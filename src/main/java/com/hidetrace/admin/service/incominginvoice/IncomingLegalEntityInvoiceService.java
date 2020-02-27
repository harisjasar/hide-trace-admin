/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.incominginvoice;

import com.hidetrace.admin.common.SaveException;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.repository.incominginvoice.IncomingLegalEntityInvoiceRepository;
import java.util.List;
import javax.swing.JOptionPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Service
public class IncomingLegalEntityInvoiceService {

    @Autowired
    IncomingLegalEntityInvoiceRepository repo;

    @Autowired
    SaveException exception;

    @Transactional
    public IncomingLegalEntityInvoiceModel saveIncomingInvoice(IncomingLegalEntityInvoiceModel model) {
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

    @Transactional
    public List<IncomingLegalEntityInvoiceModel> getAllInvoices() {
        return repo.findAll();
    }

    @Transactional
    public List<IncomingLegalEntityInvoiceModel> findByLegalEntityId(int id) {
        return repo.findAllByInvLegalEntityId(id);
    }

    @Transactional
    public void removeLegalEntityInvoice(IncomingLegalEntityInvoiceModel model) {
        repo.delete(model);
    }
}

package com.hidetrace.admin.service.outgoinginvoice;

import com.hidetrace.admin.common.SaveException;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.repository.outgoinginvoice.OutgoingLegalEntityInvoiceRepository;
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

    @Transactional
    public List<OutgoingLegalEntityInvoiceModel> findByLegalEntityId(int id) {
        return repo.findByLegalEntityId(id);
    }

    @Transactional
    public void removeInvoice(OutgoingLegalEntityInvoiceModel invModel) {
        repo.delete(invModel);
    }

    @Transactional
    public void removeLegalEntityInvoice(OutgoingLegalEntityInvoiceModel model) {
        repo.delete(model);
    }
}

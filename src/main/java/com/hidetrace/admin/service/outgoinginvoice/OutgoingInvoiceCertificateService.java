package com.hidetrace.admin.service.outgoinginvoice;

import com.hidetrace.admin.model.outgoinginvoice.OutgoingInvoiceCertificateModel;
import com.hidetrace.admin.repository.outgoinginvoice.OutgoingInvoiceCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Component
public class OutgoingInvoiceCertificateService {

    @Autowired
    OutgoingInvoiceCertificateRepository repo;

    @Transactional
    public OutgoingInvoiceCertificateModel saveOutgoingInvoiceCertificate(OutgoingInvoiceCertificateModel model) {
        return repo.save(model);
    }

    @Transactional
    public OutgoingInvoiceCertificateModel findByOutgoingInvoiceId(int id) {
        return repo.findByOutgoingInvoiceId(id);
    }

    @Transactional
    public void removeCertificate(OutgoingInvoiceCertificateModel model) {
        repo.delete(model);
    }
}

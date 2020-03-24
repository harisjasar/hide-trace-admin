package com.hidetrace.admin.service.certificate.certificatepreview;

import com.hidetrace.admin.model.certificate.certificatepreview.CertificatePreviewModel;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Component
public class CertificatePreviewService {

    @Autowired
    EntityManager entityManager;

    /**
     *
     * @return
     */
    @Transactional
    public List<CertificatePreviewModel> IncomingCertificatesPreview() {
        final String query_ = new StringBuilder()
                .append("SELECT d.Name as CertificateType, a.CertificateNumber as CertificateNumber, b.Name as InvoiceName, c.Name as LegalEntity\n"
                        + "from incominginvoice_has_certificate as a\n"
                        + "join incominginvoice as b on a.IncomingInvoiceID = b.IncomingInvoiceID\n"
                        + "join legalentity c on b.LegalEntityID = c.LegalEntityID\n"
                        + "join certificate d on a.CertificateID = d.CertificateID;")
                .toString();

        Query query = entityManager.createNativeQuery(query_, "IncomingCertificatePreviewMapping");
        return query.getResultList();

    }

    @Transactional
    public List<CertificatePreviewModel> OutgoingCertificatesPreview() {
        final String query_ = new StringBuilder()
                .append("SELECT d.Name as CertificateType, a.CertificateNumber as CertificateNumber, b.Name as InvoiceName, c.Name as LegalEntity\n"
                        + "from outgoinginvoice_has_certificate as a\n"
                        + "join outgoinginvoice as b on a.OutgoingInvoiceID = b.OutgoingInvoiceID\n"
                        + "join legalentity c on b.LegalEntityID = c.LegalEntityID\n"
                        + "join certificate d on a.CertificateID = d.CertificateID;")
                .toString();

        Query query = entityManager.createNativeQuery(query_, "IncomingCertificatePreviewMapping");
        return query.getResultList();

    }
}

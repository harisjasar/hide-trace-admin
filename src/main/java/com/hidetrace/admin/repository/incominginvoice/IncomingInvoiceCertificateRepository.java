/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.repository.incominginvoice;

import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jashar
 */
@Repository
public interface IncomingInvoiceCertificateRepository extends JpaRepository<IncomingInvoiceCertificateModel, Integer> {

    IncomingInvoiceCertificateModel findByIncomingInvoiceId(int id);
}

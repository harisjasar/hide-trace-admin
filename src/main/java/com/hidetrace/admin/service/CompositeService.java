/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceCertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceHideTypeService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Component
@Transactional
public class CompositeService {

    @Autowired
    private IncomingLegalEntityInvoiceService incomingLegalEntityInvoiceService;

    @Autowired
    private IncomingInvoiceCertificateService incomingInvoiceCertificateService;

    @Autowired
    private IncomingInvoiceHideTypeService incomingInvoiceHideTypeService;

    @Autowired
    private LegalEntityService legalEntityService;

    public void removeInvoiceAndCertAndHideTypes(IncomingLegalEntityInvoiceModel invModel, IncomingInvoiceCertificateModel certModel, List<IncomingInvoiceHideTypeModel> hideTypeModels) {

        incomingInvoiceHideTypeService.removeHideTypes(hideTypeModels);
        incomingInvoiceCertificateService.removeCertificate(certModel);
        incomingLegalEntityInvoiceService.removeLegalEntityInvoice(invModel);

    }

    public void removeLegalEntityAndLegalEntityTable(LegalEntityModel model, String tableName) {
        legalEntityService.removeLegalEntity(model);
        legalEntityService.deleteLegalEntityTable(tableName);
    }

}

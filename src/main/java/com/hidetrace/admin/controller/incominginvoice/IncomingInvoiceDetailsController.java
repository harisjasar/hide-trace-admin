/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.model.CertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.CertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceCertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceHideTypeService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceDetailsView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class IncomingInvoiceDetailsController {

    @Autowired
    private IncomingInvoiceDetailsView view;

    @Autowired
    private IncomingLegalEntityInvoiceService incomingLegalEntityInvoiceService;

    @Autowired
    private IncomingInvoiceCertificateService incomingInvoiceCertificateService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private IncomingInvoiceHideTypeService incomingInvoiceHideTypeService;

    private int invoiceId;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    private void initListeners() {

    }

    private void initData() {
        populateInvoiceId();
    }

    public void start() {
        initListeners();
        initData();
        initView();

    }

    public void passInvoiceId(int id) {
        this.invoiceId = id;
    }

    private void populateInvoiceId() {
        IncomingLegalEntityInvoiceModel invoice = incomingLegalEntityInvoiceService.findById(invoiceId).get();
        if (invoice != null) {
            view.getIncomingInvoiceIdTextfield().setText(String.valueOf(invoice.getInvId()));
            view.getInvoiceNameTextField().setText(invoice.getInvName());
            view.getGrossWeightTextField().setText(String.valueOf(invoice.getInvGrossWeight()));
            view.getNetWeightTextField().setText(String.valueOf(invoice.getInvNetWeight()));
            view.getAbroadReducedTextField().setText(String.valueOf(invoice.getInvAbroadReduced()));
            view.getSaltTextField().setText(String.valueOf(invoice.getInvSalt()));
            view.getDescriptionTextField().setText(invoice.getInvDescription());
            view.getDateTimeTextField().setText(String.valueOf(invoice.getInvTimeStamp()));
            view.getTotalLoadTextField().setText(String.valueOf(invoice.getInvTotalLoad()));
            view.getDifferenceTextField().setText(String.valueOf(invoice.getInvDifference()));

            IncomingInvoiceCertificateModel certificateModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(invoice.getInvId());
            view.getCertificateTextField().setText(certificateModel.getCertificateNumber());

            List<CertificateModel> certificateModels = certificateService.findAll();
            view.getCertificateTypeTextField().setText(certificateModels.get(certificateModel.getCertificateId() - 1).getName());

            List<IncomingInvoiceHideTypeModel> hideTypes = incomingInvoiceHideTypeService.findAllByIncomingInvoiceId(invoice.getInvId());
            hideTypes.forEach((hideType) -> {
                switch (hideType.getHideTypeId()) {
                    case 1:
                        view.getCowTextField().setText(String.valueOf(hideType.getPrice()));
                        break;
                    case 2:
                        view.getBullTextField().setText(String.valueOf(hideType.getPrice()));
                        break;
                    case 3:
                        view.getCalfTextField().setText(String.valueOf(hideType.getPrice()));
                        break;
                    default:
                        break;
                }
            });

        }
    }
}

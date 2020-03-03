/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.outgoinginvoice;

import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.view.outgoinginvoice.OutgoingInvoiceDetailsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class OutgoingInvoiceDetailsController {

    OutgoingLegalEntityInvoiceModel invoiceModel;

    @Autowired
    private OutgoingInvoiceDetailsView view;

    public void passInvoice(OutgoingLegalEntityInvoiceModel selectedItem) {
        this.invoiceModel = selectedItem;
    }

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

    private void populateInvoiceId() {
        OutgoingLegalEntityInvoiceModel invoice = this.invoiceModel;
        if (invoice != null) {
            view.getInvoiceIdTextField().setText(String.valueOf(invoice.getOutgoingInvoiceId()));
            view.getInvoiceNameTextfield().setText(invoice.getName());

        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.helper.outgoinginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.common.Validation;
import com.hidetrace.admin.controller.outgoinginvoice.NewOutgoingInvoiceDialogController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class NewOutgoingInvoiceDialogHelper {

    @Autowired
    private NewOutgoingInvoiceDialogController controller;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    private Validation validation;

    public void addInvoice() {

        boolean emptyField = validation.isFieldEmpty(controller.arrayOfTextFields());

        if (emptyField) {
            messageDialog.EmptyFieldForbidden();
        } else {
            if (controller.confirmData()) {
                if (controller.saveInvoice()) {
                    messageDialog.InvoiceSuccessfullyCreated();
                    controller.completeSave();
                } else {
                    messageDialog.InvoiceNotSuccessfullyCreated();
                }
            }

        }
    }
}

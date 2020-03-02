/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.helper.outgoinginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.common.Validation;
import com.hidetrace.admin.controller.outgoinginvoice.OutgoingInvoiceUpdateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class OutgoingInvoiceUpdateHelper {

    @Autowired
    OutgoingInvoiceUpdateController controller;

    @Autowired
    MessageDialog messageDialog;

    @Autowired
    Validation validation;

    public void updateInvoice() {

        boolean emptyField = validation.isFieldEmpty(controller.arrayOfTextFields());

        if (emptyField) {
            messageDialog.EmptyFieldForbidden();
        } else {
            if (controller.updateInvoiceInfo()) {
                messageDialog.UpdateSuccessful();
            } else {
                messageDialog.UpdateUnsuccessful();
            }
        }

    }
}

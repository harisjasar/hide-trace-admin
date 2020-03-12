/*
 * Contributors:
 *    Jashar
 */
package com.hidetrace.admin.helper.incominginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.common.Validation;
import com.hidetrace.admin.controller.incominginvoice.NewIncomingInvoiceDialogController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class NewIncomingInvoiceDialogHelper {

    @Autowired
    NewIncomingInvoiceDialogController controller;

    @Autowired
    MessageDialog messageDialog;

    @Autowired
    Validation validation;

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

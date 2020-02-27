/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.common.MessageDialog;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class IncomingInvoiceUpdateHelper {

    @Autowired
    IncomingInvoiceUpdateController controller;

    @Autowired
    MessageDialog messageDialog;

    public void updateInvoice() {

        boolean emptyField = isFieldEmpty(controller.arrayOfTextFields());

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

    private boolean isFieldEmpty(JTextField[] txtFields) {
        boolean field = false;
        for (JTextField txtField : txtFields) {
            if (txtField.getText().isEmpty()) {
                field = true;
            }
        }

        return field;
    }

}

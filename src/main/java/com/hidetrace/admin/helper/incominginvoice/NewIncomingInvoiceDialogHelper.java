/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.helper.incominginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.controller.incominginvoice.NewIncomingInvoiceDialogController;
import java.util.HashMap;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
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

    public void addInvoice() {

        boolean emptyField = isFieldEmpty(controller.arrayOfTextFields());

        if (emptyField) {
            messageDialog.EmptyFieldForbidden();
        } else {
            boolean[] articleState = articleState(controller.getArticleTextFields(), controller.getArticleToggleBtns());
            boolean notTypeSelected = articleState[0];
            boolean notTypePriceEntered = articleState[1];

            if (notTypeSelected) {
                if (notTypePriceEntered) {
                    if (controller.saveInvoice()) {
                        controller.completeSave();
                    }
                } else {
                    messageDialog.EnterPriceForSelectedType();
                }
            } else {
                messageDialog.SelectType();
            }
        }

    }

    /**
     *
     * @param txtFields
     * @return
     */
    private boolean isFieldEmpty(JTextField[] txtFields) {
        boolean field = false;
        for (JTextField txtField : txtFields) {
            if (txtField.getText().isEmpty()) {
                field = true;
            }
        }

        return field;
    }

    private boolean[] articleState(JTextField[] txtFields, JToggleButton[] tBtns) {
        JTextField[] articleTxtFields = txtFields;
        JToggleButton[] tglBtns = tBtns;

        HashMap hydeTypeHashMap = new HashMap();
        for (int i = 0; i < tglBtns.length; i++) {
            hydeTypeHashMap.put(tglBtns[i], articleTxtFields[i]);
        }

        boolean notTypeSelected = false;
        boolean notTypePriceEntered = true;
        for (JToggleButton tglBtn : tglBtns) {
            if (tglBtn.isSelected()) {
                notTypeSelected = true;
                if (((JTextField) hydeTypeHashMap.get(tglBtn)).getText().isEmpty()) {
                    notTypePriceEntered = false;
                }
            }
        }

        boolean[] articleState = {notTypeSelected, notTypePriceEntered};

        return articleState;

    }
}

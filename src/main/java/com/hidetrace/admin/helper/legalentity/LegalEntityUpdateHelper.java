/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.helper.legalentity;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.common.Validation;
import com.hidetrace.admin.controller.legalentity.LegalEntityUpdateController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class LegalEntityUpdateHelper {

    @Autowired
    LegalEntityUpdateController controller;

    @Autowired
    MessageDialog messageDialog;

    @Autowired
    Validation validation;

    public void updateLegalEntity() {

        boolean emptyField = validation.isFieldEmpty(controller.getAllTextFields());

        if (emptyField) {
            messageDialog.EmptyFieldForbidden();
        } else {
            if (controller.confirmData()) {
                if (controller.updateLegalEntity()) {
                    controller.completeLegalEntityUpdate();

                }
            }
        }
    }
}

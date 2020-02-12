/*
 * Copyright (c) 2020 Jashar.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jashar - initial API and implementation and/or initial documentation
 */
package com.hidetrace.admin.helper.legalentity;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.controller.legalentity.NewLegalEntityDialogController;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class NewLegalEntityDialogHelper {

    @Autowired
    NewLegalEntityDialogController controller;

    @Autowired
    MessageDialog messageDialog;

    public void addLegalEntity() {

        boolean emptyField = isFieldEmpty(controller.getAllTextFields());

        if (emptyField) {
            messageDialog.EmptyFieldForbidden();
        } else {

            if (controller.saveLegalEntity()) {
                controller.completeSave();

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

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
package com.hidetrace.admin.controller.legalentity;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.common.SaveException;
import com.hidetrace.admin.helper.legalentity.NewLegalEntityDialogHelper;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.view.legalentity.NewLegalEntityDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
public class NewLegalEntityDialogController {

    @Autowired
    private NewLegalEntityDialogView view;

    @Autowired
    private LegalEntityService service;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    private SaveException exception;

    @Autowired
    private NewLegalEntityConfirmationMessagePanelController confirmMessagePanelController;

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private void initListeners() {
        if (view.getAddLegalEntityButton().getActionListeners().length == 0) {
            view.getAddLegalEntityButton().addActionListener(appContext.getBean(AddLegalEntityButtonListener.class));

        }

    }

    private void initData() {

    }

    private void initView() {
        view.setVisible(true);
    }

    public JTextField[] getAllTextFields() {
        JTextField[] fields = {
            view.getLegalEntityAddressTxtField(),
            view.getLegalEntityNameTxtField(),
            view.getLegalEntityPhoneNumberTxtField()
        };
        return fields;
    }

    public boolean saveLegalEntity() {
        String newLegalEntityCode = service.getUniqueLegalEntityCode();
        LegalEntityModel model = getLegalEntityModelInfo();
        model.setLegalEntityCode(newLegalEntityCode);
        try {
            service.saveNewLegalEntityModel(model);
            service.createLegalEntityTable(newLegalEntityCode);
            messageDialog.LegalEntitySuccessfullyCreated(model.getName());
            return true;
        } catch (DataIntegrityViolationException ex) {
            messageDialog.AlreadyExist(model.getName());
            exception.setRaised(true);
        }
        return false;
    }

    public void completeSave() {
        view.getLegalEntityAddressTxtField().setText("");
        view.getLegalEntityNameTxtField().setText("");
        view.getLegalEntityPhoneNumberTxtField().setText("");

    }

    private LegalEntityModel getLegalEntityModelInfo() {
        LegalEntityModel model = new LegalEntityModel();
        model.setName(view.getLegalEntityNameTxtField().getText());
        model.setAddress(view.getLegalEntityAddressTxtField().getText());
        model.setPhoneNumber(view.getLegalEntityPhoneNumberTxtField().getText());

        return model;
    }

    @Component
    private static class AddLegalEntityButtonListener implements ActionListener {

        @Autowired
        private NewLegalEntityDialogHelper helper;

        @Override
        public void actionPerformed(ActionEvent e) {
            addLegalEntity();

        }

        public void addLegalEntity() {
            helper.addLegalEntity();
        }

    }

    public HashMap getConfirmInvoiceData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("legalEntityNameLabel", view.getLegalEntityNameLabel().getText());
        map.put("legalEntityName", view.getLegalEntityNameTxtField().getText());
        map.put("legalEntityAddressLabel", view.getLegalEntityAddressLabel().getText());
        map.put("legalEntityAddress", view.getLegalEntityAddressTxtField().getText());
        map.put("legalEntityPhoneNumberLabel", view.getLegalEntityPhoneNumberLabel().getText());
        map.put("legalEntityPhoneNumber", view.getLegalEntityPhoneNumberTxtField().getText());

        return map;
    }

    public boolean confirmData() {

        HashMap<String, String> data = getConfirmInvoiceData();
        HashMap<String, Object> objects = confirmMessagePanelController.getData();
        ((JLabel) objects.get("legalEntityNameLabel")).setText(data.get("legalEntityNameLabel"));
        ((JLabel) objects.get("legalEntityName")).setText(data.get("legalEntityName"));
        ((JLabel) objects.get("legalEntityAddressLabel")).setText(data.get("legalEntityAddressLabel"));
        ((JLabel) objects.get("legalEntityAddress")).setText(data.get("legalEntityAddress"));
        ((JLabel) objects.get("legalEntityPhoneNumberLabel")).setText(data.get("legalEntityPhoneNumberLabel"));
        ((JLabel) objects.get("legalEntityPhoneNumber")).setText(data.get("legalEntityPhoneNumber"));

        UIManager.put("OptionPane.yesButtonText", "Da");
        UIManager.put("OptionPane.noButtonText", "Ne");
        UIManager.put("OptionPane.cancelButtonText", "Otkaži");
        int dialogButton = 0;
        int dialogResult = JOptionPane.showConfirmDialog(null, confirmMessagePanelController.getConfirmationPanel(),
                "Potvrda", dialogButton);

        return dialogResult == JOptionPane.YES_OPTION;
    }

}

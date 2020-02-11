/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.outgoinginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.common.SaveException;
import com.hidetrace.admin.helper.outgoinginvoice.NewOutgoingInvoiceDialogHelper;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.outgoinginvoice.OutgoingLegalEntityInvoiceService;
import com.hidetrace.admin.view.outgoinginvoice.NewOutgoingInvoiceDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.UnexpectedRollbackException;

/**
 *
 * @author Jashar
 */
@Component
@Data
public class NewOutgoingInvoiceDialogController {

    @Autowired
    private NewOutgoingInvoiceDialogView view;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private OutgoingLegalEntityInvoiceService outgoingLegalEntityInvoiceService;

    @Autowired
    private SaveException exception;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    private ApplicationContext appContext;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.setVisible(true);
    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private void initListeners() {
        if (view.getAddInvoice().getActionListeners().length == 0) {
            view.getAddInvoice().addActionListener(appContext.getBean(AddInvoiceButtonListener.class));

        }
    }

    private void initData() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) view.getLegalEntityDropDown().getModel();
        model.removeAllElements();

        ArrayList<LegalEntityModel> entities = (ArrayList<LegalEntityModel>) legalEntityService.getAllLegalEntities();
        entities.forEach((entity) -> {
            model.addElement(entity);
        });
    }

    public JTextField[] arrayOfTextFields() {
        JTextField[] txtFields = {
            view.getInvoiceIdTextField()
        };
        return txtFields;
    }

    public boolean saveInvoice() {
        OutgoingLegalEntityInvoiceModel invoiceModel = null;
        if (!exception.isRaised()) {
            invoiceModel = newInvoiceInfo();
        }
        if (invoiceModel != null) {

            messageDialog.showMessageDialog(null, "Faktura:   " + invoiceModel.getName() + "   uspješno kreirana", "Poruka", 1);
            return true;
        } else {
            messageDialog.showMessageDialog(null, "Greška\n\nFaktura nije kreirana", "Pažnja", 0);
            exception.setRaised(false);
        }
        return false;
    }

    public void completeSave() {
        view.getInvoiceIdTextField().setText("");
    }

    private OutgoingLegalEntityInvoiceModel newInvoiceInfo() {
        OutgoingLegalEntityInvoiceModel model = new OutgoingLegalEntityInvoiceModel();
        try {

            model.setLegalEntityId(((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getLegalEntityID());
            model.setName(view.getInvoiceIdTextField().getText());

            OutgoingLegalEntityInvoiceModel model_ = outgoingLegalEntityInvoiceService.saveOutgoingInvoice(model);
            return model_;

        } catch (NumberFormatException | UnexpectedRollbackException ex) {
            if (!exception.isRaised()) {
                messageDialog.showMessageDialog(null, "Neispravan format", "Pažnja", 0);
                exception.setRaised(true);
            }
        }
        return null;
    }

    @Component
    private static class AddInvoiceButtonListener implements ActionListener {

        @Autowired
        private NewOutgoingInvoiceDialogHelper helper;

        @Override
        public void actionPerformed(ActionEvent e) {
            addInvoice();

        }

        public void addInvoice() {
            helper.addInvoice();
        }

    }

}

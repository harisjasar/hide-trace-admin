/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.outgoinginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.helper.outgoinginvoice.OutgoingInvoiceUpdateHelper;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.outgoinginvoice.OutgoingLegalEntityInvoiceService;
import com.hidetrace.admin.view.outgoinginvoice.OutgoingInvoiceUpdateView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class OutgoingInvoiceUpdateController {

    @Autowired
    private OutgoingInvoiceUpdateView view;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private OutgoingLegalEntityInvoiceService outgoingLegalEntityInvoiceService;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MessageDialog messageDialog;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.getDeleteInvoiceButton().setVisible(false);
        view.getEnableDeletionCheckbox().setSelected(false);
        view.setVisible(true);
    }

    private void initListeners() {
        if (view.getLegalEntityDropDown().getActionListeners().length == 0) {
            view.getLegalEntityDropDown().addActionListener(appContext.getBean(LegalEntityDropDownActionListener.class));
        }
        if (view.getInvoiceDropDown().getActionListeners().length == 0) {
            view.getInvoiceDropDown().addActionListener(appContext.getBean(IncomingInvoiceLegalEntityDropDownActionListener.class));
        }
        if (view.getUpdateInvoiceButton().getActionListeners().length == 0) {
            view.getUpdateInvoiceButton().addActionListener(appContext.getBean(UpdateInvoiceInfoButtonActionListener.class));
        }
        if (view.getEnableDeletionCheckbox().getItemListeners().length == 0) {
            view.getEnableDeletionCheckbox().addItemListener(appContext.getBean(DeleteInvoiceItemListener.class));
        }
        if (view.getDeleteInvoiceButton().getActionListeners().length == 0) {
            view.getDeleteInvoiceButton().addActionListener(appContext.getBean(DeleteInvoiceButtonActionListener.class));
        }
    }

    private void initData() {
        DefaultComboBoxModel legalEntityDropdown = (DefaultComboBoxModel) view.getLegalEntityDropDown().getModel();
        legalEntityDropdown.removeAllElements();

        ArrayList<LegalEntityModel> entities = (ArrayList<LegalEntityModel>) legalEntityService.getAllLegalEntities();
        entities.forEach((entity) -> {
            legalEntityDropdown.addElement(entity);
        });

    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private void clearInvoiceData() {
        view.getInvoiceIdTextField().setText("");
        view.getInvoiceNameTextField().setText("");
    }

    private void populateInvoiceData() {
        if (view.getLegalEntityDropDown().getSelectedItem() != null) {
            OutgoingLegalEntityInvoiceModel model = (OutgoingLegalEntityInvoiceModel) view.getInvoiceDropDown().getSelectedItem();
            if (model != null) {
                view.getInvoiceIdTextField().setText(String.valueOf(model.getOutgoingInvoiceId()));
                view.getInvoiceNameTextField().setText(model.getName());
            }
        }
    }

    private void deleteInvoice() {
        OutgoingLegalEntityInvoiceModel invModel = (OutgoingLegalEntityInvoiceModel) view.getInvoiceDropDown().getSelectedItem();
        if (invModel != null) {
            try {
                outgoingLegalEntityInvoiceService.removeInvoice(invModel);
                messageDialog.DeletionSuccessful();
                view.getInvoiceDropDown().removeItem(view.getInvoiceDropDown().getSelectedItem());

            } catch (DataIntegrityViolationException ex) {
                messageDialog.DeletionNotSuccessful();
            }
        } else {
            messageDialog.InvoiceNotSelected();
        }
    }

    @Component
    private static class DeleteInvoiceButtonActionListener implements ActionListener {

        @Autowired
        private OutgoingInvoiceUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite brisanje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                controller.deleteInvoice();
            }

        }

    }

    @Component
    private static class DeleteInvoiceItemListener implements ItemListener {

        @Autowired
        private OutgoingInvoiceUpdateController controller;

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (((JCheckBoxMenuItem) e.getSource()).isSelected()) {
                controller.getDeleteInvoiceButton().setVisible(true);
            } else {
                controller.getDeleteInvoiceButton().setVisible(false);
            }
        }

    }

    @Component
    private static class IncomingInvoiceLegalEntityDropDownActionListener implements ActionListener {

        @Autowired
        private OutgoingInvoiceUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.clearInvoiceData();
            controller.populateInvoiceData();

        }

    }

    @Component
    private static class UpdateInvoiceInfoButtonActionListener implements ActionListener {

        @Autowired
        private OutgoingInvoiceUpdateHelper outgoingInvoiceUpdateHelper;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite ažuriranje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                outgoingInvoiceUpdateHelper.updateInvoice();
            }

        }

    }

    @Component
    private static class LegalEntityDropDownActionListener implements ActionListener {

        @Autowired
        private OutgoingInvoiceUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.populateLegalEntityInvoiceDropDown();

        }

    }

    private void populateLegalEntityInvoiceDropDown() {
        if (view.getLegalEntityDropDown().getSelectedItem() != null) {
            int legalEntityId = ((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getLegalEntityId();
            DefaultComboBoxModel model = (DefaultComboBoxModel) view.getInvoiceDropDown().getModel();
            model.removeAllElements();

            ArrayList<OutgoingLegalEntityInvoiceModel> entities = (ArrayList<OutgoingLegalEntityInvoiceModel>) outgoingLegalEntityInvoiceService.findByLegalEntityId(legalEntityId);
            entities.forEach((entity) -> {
                model.addElement(entity);
            });

        }
    }

    public boolean updateInvoiceInfo() {
        OutgoingLegalEntityInvoiceModel invoiceModel = (OutgoingLegalEntityInvoiceModel) view.getInvoiceDropDown().getSelectedItem();
        if (invoiceModel != null) {
            String invName = view.getInvoiceNameTextField().getText();
            invoiceModel.setName(invName);
            OutgoingLegalEntityInvoiceModel updatedInvoiceModel = outgoingLegalEntityInvoiceService.saveOutgoingInvoice(invoiceModel);
            return updatedInvoiceModel != null;
        } else {
            messageDialog.InvoiceNotSelected();
        }
        return false;
    }

    public JTextField[] arrayOfTextFields() {
        JTextField[] fields = {
            view.getInvoiceNameTextField()
        };

        return fields;
    }

    private JButton getDeleteInvoiceButton() {
        return view.getDeleteInvoiceButton();
    }

}

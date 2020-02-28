/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.common.CalculateInvoiceData;
import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.model.CertificateModel;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.CertificateService;
import com.hidetrace.admin.service.CompositeService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceCertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceHideTypeService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceUpdateView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import lombok.Data;
import org.decimal4j.util.DoubleRounder;
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
public class IncomingInvoiceUpdateController {

    @Autowired
    private IncomingInvoiceUpdateView view;

    @Autowired
    private IncomingLegalEntityInvoiceService incomingLegalEntityInvoiceService;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private IncomingInvoiceCertificateService incomingInvoiceCertificateService;

    @Autowired
    private IncomingInvoiceHideTypeService incomingInvoiceHideTypeService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CalculateInvoiceData calculateInvoiceData;

    @Autowired
    private CompositeService compositeService;

    @Autowired
    private MessageDialog messageDialog;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.getDeleteInvoiceButton().setVisible(false);
        view.getEnableDeletionMenuItem().setSelected(false);

        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getLegalEntityInvoiceDropDown().getActionListeners().length == 0) {
            view.getLegalEntityInvoiceDropDown().addActionListener(appContext.getBean(IncomingInvoiceLegalEntityDropDownActionListener.class));
        }
        if (view.getLegalEntityDropDown().getActionListeners().length == 0) {
            view.getLegalEntityDropDown().addActionListener(appContext.getBean(LegalEntityDropDownActionListener.class));
        }
        if (view.getUpdateInvoiceInfoButton().getActionListeners().length == 0) {
            view.getUpdateInvoiceInfoButton().addActionListener(appContext.getBean(UpdateInvoiceInfoButtonActionListener.class));
        }
        if (view.getEnableDeletionMenuItem().getItemListeners().length == 0) {
            view.getEnableDeletionMenuItem().addItemListener(appContext.getBean(DeleteInvoiceItemListener.class));
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
        view.getIncomingInvoiceIdTextfield().setText("");
        view.getInvoiceNameTextField().setText("");
        view.getGrossWeightTextField().setText("");
        view.getNetWeightTextField().setText("");
        view.getSaltTextField().setText("");
        view.getAbroadReducedTextField().setText("");
        view.getDescriptionTextField().setText("");
        view.getCertificateTextField().setText("");
        view.getCowTextField().setText("");
        view.getBullTextField().setText("");
        view.getCalfTextField().setText("");
    }

    @Component
    private static class DeleteInvoiceItemListener implements ItemListener {

        @Autowired
        private IncomingInvoiceUpdateController controller;

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
    private static class DeleteInvoiceButtonActionListener implements ActionListener {

        @Autowired
        private IncomingInvoiceUpdateController controller;

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
    private static class IncomingInvoiceLegalEntityDropDownActionListener implements ActionListener {

        @Autowired
        private IncomingInvoiceUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.clearInvoiceData();
            controller.populateInvoiceData();

        }

    }

    @Component
    private static class LegalEntityDropDownActionListener implements ActionListener {

        @Autowired
        private IncomingInvoiceUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.populateLegalEntityInvoiceDropDown();

        }

    }

    @Component
    private static class UpdateInvoiceInfoButtonActionListener implements ActionListener {

        @Autowired
        private IncomingInvoiceUpdateHelper incomingInvoiceUpdateHelper;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite ažuriranje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                incomingInvoiceUpdateHelper.updateInvoice();
            }

        }

    }

    private void populateInvoiceData() {
        if (view.getLegalEntityInvoiceDropDown().getSelectedItem() != null) {
            IncomingLegalEntityInvoiceModel model = (IncomingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
            view.getIncomingInvoiceIdTextfield().setText(String.valueOf(model.getInvId()));
            view.getInvoiceNameTextField().setText(model.getInvName());
            view.getGrossWeightTextField().setText(String.valueOf(model.getInvGrossWeight()));
            view.getNetWeightTextField().setText(String.valueOf(model.getInvNetWeight()));
            view.getSaltTextField().setText(String.valueOf(model.getInvSalt()));
            view.getAbroadReducedTextField().setText(String.valueOf(model.getInvAbroadReduced()));
            view.getDescriptionTextField().setText(model.getInvDescription());

            IncomingInvoiceCertificateModel certificateModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(model.getInvId());
            view.getCertificateTextField().setText(certificateModel.getCertificateNumber());

            List<CertificateModel> certificateModels = certificateService.findAll();
            DefaultComboBoxModel certificatesDropdown = (DefaultComboBoxModel) view.getCertificateTypeDropdown().getModel();
            certificatesDropdown.removeAllElements();
            certificateModels.forEach((entity) -> {
                certificatesDropdown.addElement(entity);
            });

            view.getCertificateTypeDropdown().setSelectedIndex(certificateModel.getCertificateId() - 1);
            List<IncomingInvoiceHideTypeModel> hideTypes = incomingInvoiceHideTypeService.findAllByIncomingInvoiceId(model.getInvId());
            hideTypes.forEach((hideType) -> {
                switch (hideType.getHideTypeId()) {
                    case 1:
                        view.getCowTextField().setText(String.valueOf(hideType.getPrice()));
                        break;
                    case 2:
                        view.getBullTextField().setText(String.valueOf(hideType.getPrice()));
                        break;
                    case 3:
                        view.getCalfTextField().setText(String.valueOf(hideType.getPrice()));
                        break;
                    default:
                        break;
                }
            });

        }

    }

    private void populateLegalEntityInvoiceDropDown() {
        if (view.getLegalEntityDropDown().getSelectedItem() != null) {
            int legalEntityId = ((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getLegalEntityID();
            DefaultComboBoxModel model = (DefaultComboBoxModel) view.getLegalEntityInvoiceDropDown().getModel();
            model.removeAllElements();

            ArrayList<IncomingLegalEntityInvoiceModel> entities = (ArrayList<IncomingLegalEntityInvoiceModel>) incomingLegalEntityInvoiceService.findByLegalEntityId(legalEntityId);
            entities.forEach((entity) -> {
                model.addElement(entity);
            });

        }
    }

    public boolean updateInvoiceInfo() {
        IncomingLegalEntityInvoiceModel invoiceModel = (IncomingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
        String invName = view.getInvoiceNameTextField().getText();
        double invGrossWeight = Double.parseDouble(view.getGrossWeightTextField().getText());
        double invNetWeight = Double.parseDouble(view.getNetWeightTextField().getText());
        double invAbroadReduced = Double.parseDouble(view.getAbroadReducedTextField().getText());
        double invSalt = Double.parseDouble(view.getSaltTextField().getText());
        String invDescription = view.getDescriptionTextField().getText();

        invoiceModel.setInvName(invName);
        invoiceModel.setInvGrossWeight(invGrossWeight);
        invoiceModel.setInvNetWeight(invNetWeight);
        invoiceModel.setInvAbroadReduced(invAbroadReduced);
        invoiceModel.setInvSalt(invSalt);
        invoiceModel.setInvDescription(invDescription);
        invoiceModel.setInvDifference(DoubleRounder.round(calculateInvoiceData.differenceGrossNet(invSalt, (invGrossWeight - invNetWeight), invAbroadReduced, invGrossWeight), 3));
        invoiceModel.setInvTotalLoad(DoubleRounder.round(calculateInvoiceData.calculateTotalLoad(invSalt, (invGrossWeight - invNetWeight), invGrossWeight), 3));

        IncomingInvoiceCertificateModel certModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(invoiceModel.getInvId());
        certModel.setCertificateNumber(view.getCertificateTextField().getText());
        certModel.setCertificateId(((CertificateModel) view.getCertificateTypeDropdown().getSelectedItem()).getCertificateId());

        IncomingLegalEntityInvoiceModel updatedInvoiceModel = incomingLegalEntityInvoiceService.saveIncomingInvoice(invoiceModel);
        IncomingInvoiceCertificateModel updatedCertModel = incomingInvoiceCertificateService.saveIncomingInvoiceCertificate(certModel);

        return updatedCertModel != null && updatedInvoiceModel != null;

    }

    public JTextField[] arrayOfTextFields() {
        JTextField[] txtFields = {
            view.getAbroadReducedTextField(),
            view.getCertificateTextField(),
            view.getInvoiceNameTextField(),
            view.getGrossWeightTextField(),
            view.getNetWeightTextField(),
            view.getSaltTextField()

        };

        return txtFields;
    }

    public JButton getDeleteInvoiceButton() {
        return view.getDeleteInvoiceButton();
    }

    private void deleteInvoice() {
        int index = view.getLegalEntityDropDown().getSelectedIndex();
        IncomingLegalEntityInvoiceModel invModel = (IncomingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
        if (invModel != null) {
            IncomingInvoiceCertificateModel certModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(invModel.getInvId());
            List<IncomingInvoiceHideTypeModel> hideTypeModels = incomingInvoiceHideTypeService.findAllByIncomingInvoiceId(invModel.getInvId());
            try {
                compositeService.removeInvoiceAndCertAndHideTypes(invModel, certModel, hideTypeModels);
                messageDialog.DeletionSuccessful();
                view.getLegalEntityDropDown().setSelectedIndex(index);
            } catch (DataIntegrityViolationException ex) {
                messageDialog.DeletionNotSuccessful();
            }
        } else {
            messageDialog.InvoiceNotSelected();
        }
    }
}

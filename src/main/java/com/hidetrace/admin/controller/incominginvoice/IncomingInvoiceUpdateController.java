/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.model.CertificateModel;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.CertificateService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceCertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceHideTypeService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceUpdateView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getLegalEntityInvoiceDropDown().getActionListeners().length == 0) {
            view.getLegalEntityInvoiceDropDown().addActionListener(appContext.getBean(IncomingInvoiceLegalEntityDropDownActionListener.class));
        }
        if (view.getLegalEntityDropDown().getActionListeners().length == 0) {
            view.getLegalEntityDropDown().addActionListener(appContext.getBean(LegalEntityDropDownActionListener.class));
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
            System.out.println(hideTypes);
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
}

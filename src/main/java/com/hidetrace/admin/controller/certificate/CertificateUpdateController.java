/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.certificate;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.model.CertificateModel;
import com.hidetrace.admin.service.CertificateService;
import com.hidetrace.admin.view.certificate.CertificateUpdateView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
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
public class CertificateUpdateController {

    @Autowired
    private CertificateUpdateView view;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    private CertificateService certificateService;

    private void initView() {

        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.getEnableDeletionMenuItem().setSelected(false);
        view.getDeleteHideTypeButton().setVisible(false);

        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getUpdateHideTypeButton().getActionListeners().length == 0) {
            view.getUpdateHideTypeButton().addActionListener(appContext.getBean(UpdateCertificateButtonListener.class));
        }
        if (view.getCertificateCombobox().getActionListeners().length == 0) {
            view.getCertificateCombobox().addActionListener(appContext.getBean(UpdateCertificateComboBoxListener.class));
        }
        if (view.getEnableDeletionMenuItem().getItemListeners().length == 0) {
            view.getEnableDeletionMenuItem().addItemListener(appContext.getBean(DeleteCertificateItemListener.class));
        }
        if (view.getDeleteHideTypeButton().getActionListeners().length == 0) {
            view.getDeleteHideTypeButton().addActionListener(appContext.getBean(DeleteCertificateButtonActionListener.class));
        }

    }

    private void initData() {
        List<CertificateModel> certificateModel = certificateService.findAll();
        DefaultComboBoxModel comboBox = (DefaultComboBoxModel) view.getCertificateCombobox().getModel();
        comboBox.removeAllElements();
        certificateModel.forEach((a) -> {
            comboBox.addElement(a);
        });
    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private void deleteCertificate() {

        CertificateModel catModel = (CertificateModel) view.getCertificateCombobox().getSelectedItem();
        if (catModel != null) {
            try {
                certificateService.remove(catModel);
                messageDialog.DeletionSuccessful();
                view.getCertificateCombobox().removeItem(view.getCertificateCombobox().getSelectedItem());

            } catch (DataIntegrityViolationException ex) {
                messageDialog.DeletionNotSuccessful();
            }
        } else {
            messageDialog.HideTypeNotSelected();
        }

    }

    @Component
    private static class DeleteCertificateButtonActionListener implements ActionListener {

        @Autowired
        private CertificateUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite brisanje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                controller.deleteCertificate();
            }

        }

    }

    @Component
    private static class DeleteCertificateItemListener implements ItemListener {

        @Autowired
        private CertificateUpdateController controller;

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (((JCheckBoxMenuItem) e.getSource()).isSelected()) {
                controller.getDeleteButton().setVisible(true);
            } else {
                controller.getDeleteButton().setVisible(false);
            }
        }

    }

    @Component
    private static class UpdateCertificateButtonListener implements ActionListener {

        @Autowired
        private CertificateUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite ažuriranje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                controller.updateCategory();
            }

        }

    }

    @Component
    private static class UpdateCertificateComboBoxListener implements ActionListener {

        @Autowired
        private CertificateUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            CertificateModel model = (CertificateModel) controller.getCertificateCombobox().getSelectedItem();
            if (model != null) {
                controller.getNameTextField().setText(model.getName());
                controller.getDescTextField().setText(model.getDescription());
            }
        }

    }

    private void updateCategory() {
        int id = ((CertificateModel) view.getCertificateCombobox().getSelectedItem()).getCertificateId();
        CertificateModel model = new CertificateModel();
        if (view.getNameTextField().getText().length() == 0 || view.getDescTextField().getText().length() == 0) {
            messageDialog.EmptyFieldForbidden();
        } else {
            String name = view.getNameTextField().getText();
            String desc = view.getDescTextField().getText();
            model.setName(name);
            model.setDescription(desc);
            model.setCertificateId(id);

            CertificateModel newModel = certificateService.save(model);
            if (newModel != null) {
                messageDialog.CreationSuccessful();
            } else {
                messageDialog.CreationNotSuccessful();
            }

        }
    }

    public JTextField getNameTextField() {
        return view.getNameTextField();
    }

    public JTextField getDescTextField() {
        return view.getDescTextField();
    }

    public JComboBox getCertificateCombobox() {
        return view.getCertificateCombobox();
    }

    public JButton getDeleteButton() {
        return view.getDeleteHideTypeButton();
    }

}

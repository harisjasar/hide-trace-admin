/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.legalentity;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.helper.legalentity.LegalEntityUpdateHelper;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.service.CompositeService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.view.legalentity.LegalEntityUpdateView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.persistence.PersistenceException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
public class LegalEntityUpdateController {

    @Autowired
    private LegalEntityUpdateView view;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    private CompositeService compositeService;

    private void initView() {
        view.getRemoveLegalEntityButton().setVisible(false);
        view.getAllowDeletionCheckBox().setSelected(false);
        view.setVisible(true);
    }

    private void initListeners() {
        if (view.getLegalEntityDropdown().getActionListeners().length == 0) {
            view.getLegalEntityDropdown().addActionListener(appContext.getBean(LegalEntityDropDownActionListener.class));
        }

        if (view.getUpdateLegalEntityButton().getActionListeners().length == 0) {
            view.getUpdateLegalEntityButton().addActionListener(appContext.getBean(UpdateLegalEntityInfoButtonActionListener.class));
        }

        if (view.getAllowDeletionCheckBox().getItemListeners().length == 0) {
            view.getAllowDeletionCheckBox().addItemListener(appContext.getBean(DeleteLegalEntityItemListener.class));
        }

        if (view.getRemoveLegalEntityButton().getActionListeners().length == 0) {
            view.getRemoveLegalEntityButton().addActionListener(appContext.getBean(DeleteLegalEntityButtonActionListener.class));
        }
    }

    private void initData() {
        DefaultComboBoxModel legalEntityDropdown = (DefaultComboBoxModel) view.getLegalEntityDropdown().getModel();
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

    @Component
    private static class LegalEntityDropDownActionListener implements ActionListener {

        @Autowired
        private LegalEntityUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.clearEntityData();
            controller.populateEntityData();

        }

    }

    @Component
    private static class UpdateLegalEntityInfoButtonActionListener implements ActionListener {

        @Autowired
        private LegalEntityUpdateHelper legalEntityUpdateHelperHelper;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite ažuriranje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                legalEntityUpdateHelperHelper.updateLegalEntity();
            }

        }

    }

    @Component
    private static class DeleteLegalEntityItemListener implements ItemListener {

        @Autowired
        private LegalEntityUpdateController controller;

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (((JCheckBox) e.getSource()).isSelected()) {
                controller.getLegalEntityDeleteButton().setVisible(true);
            } else {
                controller.getLegalEntityDeleteButton().setVisible(false);
            }
        }

    }

    @Component
    private static class DeleteLegalEntityButtonActionListener implements ActionListener {

        @Autowired
        private LegalEntityUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite brisanje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                controller.deleteLegalEntity();
            }

        }

    }

    public void clearEntityData() {
        view.getNameTextField().setText("");
        view.getAddressTextField().setText("");
        view.getPhoneNumberTextField().setText("");
    }

    public void populateEntityData() {
        LegalEntityModel model = (LegalEntityModel) view.getLegalEntityDropdown().getSelectedItem();
        if (model != null) {
            view.getNameTextField().setText(model.getName());
            view.getAddressTextField().setText(model.getAddress());
            view.getPhoneNumberTextField().setText(model.getPhoneNumber());
        }
    }

    public JTextField[] getAllTextFields() {
        JTextField[] fields = {
            view.getNameTextField(),
            view.getAddressTextField(),
            view.getPhoneNumberTextField()
        };
        return fields;
    }

    public boolean confirmData() {
        //@TODO add the logic to confirm the data later
        return true;
    }

    public boolean updateLegalEntity() {
        LegalEntityModel oldModel = (LegalEntityModel) view.getLegalEntityDropdown().getSelectedItem();
        if (oldModel != null) {
            oldModel.setName(view.getNameTextField().getText());
            oldModel.setAddress(view.getAddressTextField().getText());
            oldModel.setPhoneNumber(view.getPhoneNumberTextField().getText());

            LegalEntityModel newModel = legalEntityService.saveNewLegalEntityModel(oldModel);
            if (newModel != null) {
                messageDialog.UpdateSuccessful();
                return true;
            } else {
                messageDialog.UpdateUnsuccessful();
                return false;
            }

        } else {
            messageDialog.LegalEntityNotSelected();
            return false;
        }
    }

    public void completeLegalEntityUpdate() {
        //@TODO add complete update logic later
    }

    private JButton getLegalEntityDeleteButton() {
        return view.getRemoveLegalEntityButton();
    }

    private void deleteLegalEntity() {
        LegalEntityModel model = (LegalEntityModel) view.getLegalEntityDropdown().getSelectedItem();
        if (model != null) {
            try {
                compositeService.removeLegalEntityAndLegalEntityTable(model, model.getLegalEntityCode());
                completeLegalEntityDeletion();
                messageDialog.DeletionSuccessful();
            } catch (DataIntegrityViolationException | PersistenceException ex) {
                messageDialog.DeletionNotSuccessful();
            }
        } else {
            messageDialog.LegalEntityNotSelected();
        }

    }

    public void completeLegalEntityDeletion() {
        view.getLegalEntityDropdown().removeItem(view.getLegalEntityDropdown().getSelectedItem());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.hidetype;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.service.HideTypeService;
import com.hidetrace.admin.view.hidetype.HideTypeNewView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class HideTypeNewController {

    @Autowired
    private HideTypeNewView view;

    @Autowired
    private MessageDialog messagDialog;

    @Autowired
    private HideTypeService hideTypeService;

    @Autowired
    private ApplicationContext appContext;

    private void initView() {

        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getCreateButton().getActionListeners().length == 0) {
            view.getCreateButton().addActionListener(appContext.getBean(CreateNewHideTypeButtonListener.class));
        }

    }

    private void initData() {
    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    @Component
    private static class CreateNewHideTypeButtonListener implements ActionListener {

        @Autowired
        private HideTypeNewController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite kreiranje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                controller.saveNewHideType();
            }

        }

    }

    private void saveNewHideType() {
        if (view.getNameTextField().getText().length() == 0 || view.getDescriptionTextField().getText().length() == 0) {
            messagDialog.EmptyFieldForbidden();
        } else {
            HideTypeModel model = new HideTypeModel();
            String name = view.getNameTextField().getText();
            String desc = view.getDescriptionTextField().getText();
            model.setName(name);
            model.setDescription(desc);

            HideTypeModel newModel = hideTypeService.save(model);
            if (newModel != null) {
                messagDialog.CreationSuccessful();
                clearDataAfterCreation();
            } else {
                messagDialog.CreationNotSuccessful();
            }
        }
    }

    private void clearDataAfterCreation() {
        view.getNameTextField().setText("");
        view.getDescriptionTextField().setText("");
    }

}

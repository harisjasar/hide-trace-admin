/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.category;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.model.CategoryModel;
import com.hidetrace.admin.service.CategoryService;
import com.hidetrace.admin.view.category.CategoryUpdateView;
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
public class CategoryUpdateController {

    @Autowired
    private CategoryUpdateView view;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    private CategoryService categoryService;

    private void initView() {

        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.getEnableDeletionMenuItem().setSelected(false);
        view.getDeleteHideTypeButton().setVisible(false);

        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getUpdateHideTypeButton().getActionListeners().length == 0) {
            view.getUpdateHideTypeButton().addActionListener(appContext.getBean(UpdateHideTypeButtonListener.class));
        }
        if (view.getCaetgoryCombobox().getActionListeners().length == 0) {
            view.getCaetgoryCombobox().addActionListener(appContext.getBean(UpdateHideTypeComboBoxListener.class));
        }
        if (view.getEnableDeletionMenuItem().getItemListeners().length == 0) {
            view.getEnableDeletionMenuItem().addItemListener(appContext.getBean(DeleteHideTypeItemListener.class));
        }
        if (view.getDeleteHideTypeButton().getActionListeners().length == 0) {
            view.getDeleteHideTypeButton().addActionListener(appContext.getBean(DeleteInvoiceButtonActionListener.class));
        }

    }

    private void initData() {
        List<CategoryModel> categoryModel = categoryService.getAllCategories();
        DefaultComboBoxModel comboBox = (DefaultComboBoxModel) view.getCaetgoryCombobox().getModel();
        comboBox.removeAllElements();
        categoryModel.forEach((a) -> {
            comboBox.addElement(a);
        });
    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private void deleteHideType() {

        CategoryModel catModel = (CategoryModel) view.getCaetgoryCombobox().getSelectedItem();
        if (catModel != null) {
            try {
                categoryService.remove(catModel);
                messageDialog.DeletionSuccessful();
                view.getCaetgoryCombobox().removeItem(view.getCaetgoryCombobox().getSelectedItem());

            } catch (DataIntegrityViolationException ex) {
                messageDialog.DeletionNotSuccessful();
            }
        } else {
            messageDialog.HideTypeNotSelected();
        }

    }

    @Component
    private static class DeleteInvoiceButtonActionListener implements ActionListener {

        @Autowired
        private CategoryUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Da");
            UIManager.put("OptionPane.noButtonText", "Ne");
            UIManager.put("OptionPane.cancelButtonText", "Otkaži");
            int dialogButton = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Potvrdite brisanje",
                    "Potvrda", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                controller.deleteHideType();
            }

        }

    }

    @Component
    private static class DeleteHideTypeItemListener implements ItemListener {

        @Autowired
        private CategoryUpdateController controller;

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
    private static class UpdateHideTypeButtonListener implements ActionListener {

        @Autowired
        private CategoryUpdateController controller;

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
    private static class UpdateHideTypeComboBoxListener implements ActionListener {

        @Autowired
        private CategoryUpdateController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            CategoryModel model = (CategoryModel) controller.getCaetgoryCombobox().getSelectedItem();
            if (model != null) {
                controller.getNameTextField().setText(model.getName());
                controller.getDescTextField().setText(model.getDescription());
            }
        }

    }

    private void updateCategory() {
        int id = ((CategoryModel) view.getCaetgoryCombobox().getSelectedItem()).getCategoryId();
        CategoryModel model = new CategoryModel();
        if (view.getNameTextField().getText().length() == 0 || view.getDescTextField().getText().length() == 0) {
            messageDialog.EmptyFieldForbidden();
        } else {
            String name = view.getNameTextField().getText();
            String desc = view.getDescTextField().getText();
            model.setName(name);
            model.setDescription(desc);
            model.setCategoryId(id);

            CategoryModel newModel = categoryService.save(model);
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

    public JComboBox getCaetgoryCombobox() {
        return view.getCaetgoryCombobox();
    }

    public JButton getDeleteButton() {
        return view.getDeleteHideTypeButton();
    }

}

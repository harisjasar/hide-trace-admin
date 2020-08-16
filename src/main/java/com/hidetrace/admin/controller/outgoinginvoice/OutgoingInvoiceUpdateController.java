/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.outgoinginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.helper.outgoinginvoice.OutgoingInvoiceUpdateHelper;
import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.category.CategoryModel;
import com.hidetrace.admin.model.certificate.CertificateModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingInvoiceCertificateModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingInvoiceHideTypeCategoryModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.CompositeService;
import com.hidetrace.admin.service.HideTypeService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.category.CategoryService;
import com.hidetrace.admin.service.certificate.CertificateService;
import com.hidetrace.admin.service.outgoinginvoice.OutgoingInvoiceCertificateService;
import com.hidetrace.admin.service.outgoinginvoice.OutgoingInvoiceHideTypeCategoryService;
import com.hidetrace.admin.service.outgoinginvoice.OutgoingLegalEntityInvoiceService;
import com.hidetrace.admin.view.outgoinginvoice.OutgoingInvoiceUpdateView;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

    @Autowired
    private HideTypeService hideTypeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private OutgoingInvoiceHideTypeCategoryService outgoingInvoiceHideTypeCategoryService;

    @Autowired
    private OutgoingInvoiceCertificateService outgoingInvoiceCertificateService;

    @Autowired
    private CompositeService compositeService;

    private int nextY = 0;
    private List<JComboBox> hideTypeComboxList = new ArrayList<>();
    private List<JComboBox> categoryComboxList = new ArrayList<>();
    private List<JTextField> priceList = new ArrayList<>();
    private List<JLabel> labelList = new ArrayList<>();
    List<CategoryModel> categories = new ArrayList<>();
    List<HideTypeModel> hideTypes = new ArrayList<>();
    List<OutgoingInvoiceHideTypeCategoryModel> allArticles = new ArrayList<>();

    private void initView() {
        view.getDeleteInvoiceButton().setVisible(false);
        view.setVisible(true);
    }

    private void initListeners() {
        if (view.getLegalEntityDropDown().getActionListeners().length == 0) {
            view.getLegalEntityDropDown().addActionListener(appContext.getBean(LegalEntityDropDownActionListener.class));
        }
        if (view.getLegalEntityInvoiceDropDown().getActionListeners().length == 0) {
            view.getLegalEntityInvoiceDropDown().addActionListener(appContext.getBean(IncomingInvoiceLegalEntityDropDownActionListener.class));
        }
        if (view.getUpdateInvoiceInfoButton().getActionListeners().length == 0) {
            view.getUpdateInvoiceInfoButton().addActionListener(appContext.getBean(UpdateInvoiceInfoButtonActionListener.class));
        }
        if (view.getAllowDeletionCheckBox().getItemListeners().length == 0) {
            view.getAllowDeletionCheckBox().addItemListener(appContext.getBean(DeleteInvoiceItemListener.class));
        }
        if (view.getDeleteInvoiceButton().getActionListeners().length == 0) {
            view.getDeleteInvoiceButton().addActionListener(appContext.getBean(DeleteInvoiceButtonActionListener.class));
        }
        if (view.getAddNewArticleButton().getActionListeners().length == 0) {
            view.getAddNewArticleButton().addActionListener(appContext.getBean(OutgoingUpdateAddNewArticleComponentsButtonActionListener.class));
        }
    }

    private void initData() {
        DefaultComboBoxModel legalEntityDropdown = (DefaultComboBoxModel) view.getLegalEntityDropDown().getModel();
        legalEntityDropdown.removeAllElements();

        ArrayList<LegalEntityModel> entities = (ArrayList<LegalEntityModel>) legalEntityService.getAllLegalEntities();
        entities.forEach((entity) -> {
            legalEntityDropdown.addElement(entity);
        });

        hideTypes = hideTypeService.getAllHideTypes();
        categories = categoryService.getAllCategories();

    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private void clearInvoiceData() {
        view.getIncomingInvoiceIdTextfield().setText("");
        view.getInvoiceNameTextField().setText("");
        view.getCertificateTextField().setText("");
        view.getDescriptionTextField().setText("");
    }

    private void populateInvoiceData() {
        view.getArticleGridBagLayoutPanel().removeAll();
        view.revalidate();
        view.repaint();

        nextY = 0;
        hideTypeComboxList.clear();
        categoryComboxList.clear();
        priceList.clear();
        labelList.clear();

        if (view.getLegalEntityDropDown().getSelectedItem() != null) {
            OutgoingLegalEntityInvoiceModel model = (OutgoingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
            if (model != null) {
                view.getIncomingInvoiceIdTextfield().setText(String.valueOf(model.getOutgoingInvoiceId()));
                view.getInvoiceNameTextField().setText(model.getName());
                view.getDescriptionTextField().setText(model.getDescription());

                List<CertificateModel> certificateModels = certificateService.findAll();
                DefaultComboBoxModel certificatesDropdown = (DefaultComboBoxModel) view.getCertificateTypeDropdown().getModel();
                certificatesDropdown.removeAllElements();
                certificateModels.forEach((entity) -> {
                    certificatesDropdown.addElement(entity);
                });

                OutgoingInvoiceCertificateModel certificateModel = outgoingInvoiceCertificateService.findByOutgoingInvoiceId(model.getOutgoingInvoiceId());
                if (certificateModel != null) {
                    view.getCertificateTextField().setText(certificateModel.getCertificateNumber());
                    CertificateModel cert = certificateService.findById(certificateModel.getCertificateId()).get();
                    view.getCertificateTypeDropdown().setSelectedItem(cert);
                }

                //get all articles (hide types and categories)
                List<OutgoingInvoiceHideTypeCategoryModel> allArticlesList = outgoingInvoiceHideTypeCategoryService.findOutgoingInvoiceHideTypeCategoryByOutgoingInvoiceId(model.getOutgoingInvoiceId());
                populateArticlesData(allArticlesList);

            }
        }
    }

    private void deleteInvoice() {
        OutgoingLegalEntityInvoiceModel invModel = (OutgoingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
        if (invModel != null) {
            OutgoingInvoiceCertificateModel certModel = outgoingInvoiceCertificateService.findByOutgoingInvoiceId(invModel.getOutgoingInvoiceId());
            List<OutgoingInvoiceHideTypeCategoryModel> hideTypeCategoryModels = outgoingInvoiceHideTypeCategoryService.findAllByOutgoingInvoiceId(invModel.getOutgoingInvoiceId());

            try {
                //outgoingLegalEntityInvoiceService.removeInvoice(invModel);
                compositeService.removeOutgoingInvoiceAndCertAndHideTypes(invModel, certModel, hideTypeCategoryModels);

                messageDialog.DeletionSuccessful();
                view.getLegalEntityInvoiceDropDown().removeItem(view.getLegalEntityInvoiceDropDown().getSelectedItem());

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
            if (((JCheckBox) e.getSource()).isSelected()) {
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
            DefaultComboBoxModel model = (DefaultComboBoxModel) view.getLegalEntityInvoiceDropDown().getModel();
            model.removeAllElements();

            ArrayList<OutgoingLegalEntityInvoiceModel> entities = (ArrayList<OutgoingLegalEntityInvoiceModel>) outgoingLegalEntityInvoiceService.findByLegalEntityId(legalEntityId);
            entities.forEach((entity) -> {
                model.addElement(entity);
            });

        }
    }

    public boolean updateInvoiceInfo() {
        OutgoingLegalEntityInvoiceModel invoiceModel = (OutgoingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
        if (invoiceModel != null) {
            String invName = view.getInvoiceNameTextField().getText();
            String description = view.getDescriptionTextField().getText();

            invoiceModel.setName(invName);
            invoiceModel.setDescription(description);

            OutgoingInvoiceCertificateModel certModel = outgoingInvoiceCertificateService.findByOutgoingInvoiceId(invoiceModel.getOutgoingInvoiceId());
            if (certModel == null) {
                certModel = new OutgoingInvoiceCertificateModel();
                certModel.setOutgoingInvoiceId(invoiceModel.getOutgoingInvoiceId());
            }
            certModel.setCertificateNumber(view.getCertificateTextField().getText());
            certModel.setCertificateId(((CertificateModel) view.getCertificateTypeDropdown().getSelectedItem()).getCertificateId());

            List<OutgoingInvoiceHideTypeCategoryModel> hideTypeCategoriesToSave = getInvoiceHideTypeAndCategoryInfo();

            return compositeService.saveOutgoingInvoiceHideTypeCategory(invoiceModel, hideTypeCategoriesToSave, certModel);

            //OutgoingLegalEntityInvoiceModel updatedInvoiceModel = outgoingLegalEntityInvoiceService.saveOutgoingInvoice(invoiceModel);
            //return updatedInvoiceModel != null;
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

    private void populateArticlesData(List<OutgoingInvoiceHideTypeCategoryModel> allArticlesList) {
        if (allArticlesList != null) {
            allArticles.clear();
            allArticles.addAll(allArticlesList);

            JPanel panel = view.getArticleGridBagLayoutPanel();
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();

            for (int i = 0; i < allArticlesList.size(); i++) {

                JComboBox comboBox1 = new JComboBox();
                comboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(hideTypes.toArray()));
                comboBox1.setPreferredSize(new java.awt.Dimension(120, 30));
                HideTypeModel hideTypeModel = hideTypeService.findById(allArticlesList.get(i).getHideTypeId()).get();
                comboBox1.setSelectedItem(hideTypeModel);
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(comboBox1, gridBagConstraints);
                hideTypeComboxList.add(comboBox1);

                JComboBox comboBox2 = new JComboBox();
                comboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(categories.toArray()));
                comboBox2.setPreferredSize(new java.awt.Dimension(120, 30));
                CategoryModel categoryModel = categoryService.getCategyById(allArticlesList.get(i).getCategoryId()).get();
                comboBox2.setSelectedItem(categoryModel);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(comboBox2, gridBagConstraints);
                categoryComboxList.add(comboBox2);

                JTextField field = new JTextField();
                field.setText(String.valueOf(allArticlesList.get(i).getPrice()));
                field.setPreferredSize(new java.awt.Dimension(60, 30));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(field, gridBagConstraints);
                priceList.add(field);

                JLabel label = new JLabel();
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        removeArticleComponent(evt);
                    }

                });
                label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/rsz_stop.png"))); // NOI18N
                label.setPreferredSize(new java.awt.Dimension(30, 30));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(label, gridBagConstraints);
                labelList.add(label);

                panel.revalidate();
                panel.repaint();
                nextY++;
            }
        }

    }

    private void removeArticleComponent(MouseEvent evt) {
        int index = labelList.indexOf(evt.getSource());
        if (index > -1) {
            outgoingInvoiceHideTypeCategoryService.removeSingle(allArticles.get(index));

            JPanel panel = view.getArticleGridBagLayoutPanel();
            panel.remove(hideTypeComboxList.get(index));
            panel.remove(categoryComboxList.get(index));
            panel.remove(priceList.get(index));
            panel.remove(labelList.get(index));

            hideTypeComboxList.remove(index);
            categoryComboxList.remove(index);
            priceList.remove(index);
            labelList.remove(index);
            allArticles.remove(index);

            nextY--;
            panel.revalidate();
            panel.repaint();

        }
    }

    private List<OutgoingInvoiceHideTypeCategoryModel> getInvoiceHideTypeAndCategoryInfo() {
        List<OutgoingInvoiceHideTypeCategoryModel> hideTypeAndCategoryList = new ArrayList<>();
        if (view.getArticleGridBagLayoutPanel().getComponentCount() != 0) {
            for (int i = 0; i < labelList.size(); i++) {
                try {
                    HideTypeModel hideType = (HideTypeModel) hideTypeComboxList.get(i).getSelectedItem();
                    CategoryModel category = (CategoryModel) categoryComboxList.get(i).getSelectedItem();
                    if (priceList.get(i).getText().isEmpty()) {
                        messageDialog.ArticlePriceCannotBeEmpty();
                        return null;
                    }
                    Double price = Double.parseDouble(priceList.get(i).getText());

                    OutgoingInvoiceHideTypeCategoryModel model = new OutgoingInvoiceHideTypeCategoryModel();
                    model.setId(allArticles.get(i).getId());
                    model.setCategoryId(category.getCategoryId());
                    model.setHideTypeId(hideType.getHideTypeId());
                    model.setPrice(price);
                    hideTypeAndCategoryList.add(model);
                } catch (NumberFormatException ex) {
                    messageDialog.WrongFormat();
                    return null;
                }

            }
        } else {
            messageDialog.ArticleNeedsToBeAdded();
            return null;
        }
        return hideTypeAndCategoryList;
    }

    @Component
    private static class OutgoingUpdateAddNewArticleComponentsButtonActionListener implements ActionListener {

        @Autowired
        private OutgoingInvoiceUpdateView view;

        @Autowired
        private OutgoingInvoiceUpdateController controller;

        @Autowired
        private MessageDialog messagDialog;

        @Autowired
        private HideTypeService hideTypeService;

        @Autowired
        private CategoryService categoryService;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller.nextY < 10) {

                controller.allArticles.add(new OutgoingInvoiceHideTypeCategoryModel());

                JPanel panel = view.getArticleGridBagLayoutPanel();
                GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();

                JComboBox comboBox1 = new JComboBox();
                List<HideTypeModel> hideTypes = hideTypeService.getAllHideTypes();
                comboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(hideTypes.toArray()));
                comboBox1.setPreferredSize(new java.awt.Dimension(120, 30));
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = controller.nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(comboBox1, gridBagConstraints);
                controller.hideTypeComboxList.add(comboBox1);

                JComboBox comboBox2 = new JComboBox();
                List<CategoryModel> categories = categoryService.getAllCategories();
                comboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(categories.toArray()));
                comboBox2.setPreferredSize(new java.awt.Dimension(120, 30));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = controller.nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(comboBox2, gridBagConstraints);
                controller.categoryComboxList.add(comboBox2);

                JTextField field = new JTextField();
                field.setPreferredSize(new java.awt.Dimension(60, 30));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = controller.nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(field, gridBagConstraints);
                controller.priceList.add(field);

                JLabel label = new JLabel();
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        controller.removeArticleComponent(evt);
                    }
                });
                label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/rsz_stop.png"))); // NOI18N
                label.setPreferredSize(new java.awt.Dimension(30, 30));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 3;
                gridBagConstraints.gridy = controller.nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(label, gridBagConstraints);
                controller.labelList.add(label);

                panel.revalidate();
                panel.repaint();
                controller.nextY++;
            } else {
                messagDialog.MaxNumberOfArticlesReached();
            }

        }

    }
}

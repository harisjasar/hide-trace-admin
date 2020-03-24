/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.common.CalculateInvoiceData;
import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.helper.incominginvoice.IncomingInvoiceUpdateHelper;
import com.hidetrace.admin.model.category.CategoryModel;
import com.hidetrace.admin.model.certificate.CertificateModel;
import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeCategoryModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.category.CategoryService;
import com.hidetrace.admin.service.certificate.CertificateService;
import com.hidetrace.admin.service.CompositeService;
import com.hidetrace.admin.service.HideTypeService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceCertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceHideTypeCategoryService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceUpdateView;
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
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    private IncomingInvoiceHideTypeCategoryService incomingInvoiceHideTypeCategoryService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private HideTypeService hideTypeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CalculateInvoiceData calculateInvoiceData;

    @Autowired
    private CompositeService compositeService;

    @Autowired
    private MessageDialog messageDialog;

    private int nextY = 0;
    private List<JComboBox> hideTypeComboxList = new ArrayList<>();
    private List<JComboBox> categoryComboxList = new ArrayList<>();
    private List<JTextField> priceList = new ArrayList<>();
    private List<JLabel> labelList = new ArrayList<>();
    List<CategoryModel> categories;
    List<HideTypeModel> hideTypes;
    List<IncomingInvoiceHideTypeCategoryModel> allArticles = new ArrayList<>();

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

        if (view.getAddNewArticleButton().getActionListeners().length == 0) {
            view.getAddNewArticleButton().addActionListener(appContext.getBean(UpdateAddNewArticleComponentsButtonActionListener.class));
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
        view.getGrossWeightTextField().setText("");
        view.getNetWeightTextField().setText("");
        view.getSaltTextField().setText("");
        view.getAbroadReducedTextField().setText("");
        view.getDescriptionTextField().setText("");
        view.getCertificateTextField().setText("");

    }

    private void populateArticlesData(List<IncomingInvoiceHideTypeCategoryModel> allArticlesList) {
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
            incomingInvoiceHideTypeCategoryService.removeSingle(allArticles.get(index));

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

    @Component
    private static class UpdateAddNewArticleComponentsButtonActionListener implements ActionListener {

        @Autowired
        private IncomingInvoiceUpdateView view;

        @Autowired
        private IncomingInvoiceUpdateController controller;

        @Autowired
        private MessageDialog messagDialog;

        @Autowired
        private HideTypeService hideTypeService;

        @Autowired
        private CategoryService categoryService;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller.nextY < 10) {

                controller.allArticles.add(new IncomingInvoiceHideTypeCategoryModel());

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
        view.getArticleGridBagLayoutPanel().removeAll();
        view.revalidate();
        view.repaint();

        nextY = 0;
        hideTypeComboxList.clear();
        categoryComboxList.clear();
        priceList.clear();
        labelList.clear();

        if (view.getLegalEntityInvoiceDropDown().getSelectedItem() != null) {
            IncomingLegalEntityInvoiceModel model = (IncomingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
            view.getIncomingInvoiceIdTextfield().setText(String.valueOf(model.getInvId()));
            view.getInvoiceNameTextField().setText(model.getInvName());
            view.getGrossWeightTextField().setText(String.valueOf(model.getInvGrossWeight()));
            view.getNetWeightTextField().setText(String.valueOf(model.getInvNetWeight()));
            view.getSaltTextField().setText(String.valueOf(model.getInvSalt()));
            view.getAbroadReducedTextField().setText(String.valueOf(model.getInvAbroadReduced()));
            view.getDescriptionTextField().setText(model.getInvDescription());

            List<CertificateModel> certificateModels = certificateService.findAll();
            DefaultComboBoxModel certificatesDropdown = (DefaultComboBoxModel) view.getCertificateTypeDropdown().getModel();
            certificatesDropdown.removeAllElements();
            certificateModels.forEach((entity) -> {
                certificatesDropdown.addElement(entity);
            });

            IncomingInvoiceCertificateModel certificateModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(model.getInvId());
            if (certificateModel != null) {
                view.getCertificateTextField().setText(certificateModel.getCertificateNumber());
                CertificateModel cert = certificateService.findById(certificateModel.getCertificateId()).get();
                view.getCertificateTypeDropdown().setSelectedItem(cert);
            }

            //get all articles (hide types and categories)
            List<IncomingInvoiceHideTypeCategoryModel> allArticlesList = incomingInvoiceHideTypeCategoryService.findIncomingInvoiceHideTypeCategoryByIncomingInvoiceId(model.getInvId());
            populateArticlesData(allArticlesList);

        }

    }

    private void populateLegalEntityInvoiceDropDown() {
        if (view.getLegalEntityDropDown().getSelectedItem() != null) {
            int legalEntityId = ((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getLegalEntityId();
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
        if (invoiceModel != null) {
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
            if (certModel == null) {
                certModel = new IncomingInvoiceCertificateModel();
                certModel.setIncomingInvoiceId(invoiceModel.getInvId());
            }
            certModel.setCertificateNumber(view.getCertificateTextField().getText());
            certModel.setCertificateId(((CertificateModel) view.getCertificateTypeDropdown().getSelectedItem()).getCertificateId());

            List<IncomingInvoiceHideTypeCategoryModel> hideTypeCategoriesToSave = getInvoiceHideTypeAndCategoryInfo();

            return compositeService.saveIncomingInvoiceHideTypeCategory(invoiceModel, hideTypeCategoriesToSave, certModel);

        } else {
            messageDialog.InvoiceNotSelected();
        }
        return false;
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
        IncomingLegalEntityInvoiceModel invModel = (IncomingLegalEntityInvoiceModel) view.getLegalEntityInvoiceDropDown().getSelectedItem();
        if (invModel != null) {
            IncomingInvoiceCertificateModel certModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(invModel.getInvId());
            List<IncomingInvoiceHideTypeCategoryModel> hideTypeCategoryModels = incomingInvoiceHideTypeCategoryService.findIncomingInvoiceHideTypeCategoryByIncomingInvoiceId(invModel.getInvId());
            try {
                compositeService.removeInvoiceAndCertAndHideTypes(invModel, certModel, hideTypeCategoryModels);
                messageDialog.DeletionSuccessful();
                view.getLegalEntityInvoiceDropDown().removeItem(view.getLegalEntityInvoiceDropDown().getSelectedItem());

            } catch (DataIntegrityViolationException ex) {
                messageDialog.DeletionNotSuccessful();
            }
        } else {
            messageDialog.InvoiceNotSelected();
        }
    }

    private List<IncomingInvoiceHideTypeCategoryModel> getInvoiceHideTypeAndCategoryInfo() {
        List<IncomingInvoiceHideTypeCategoryModel> hideTypeAndCategoryList = new ArrayList<>();
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

                    IncomingInvoiceHideTypeCategoryModel model = allArticles.get(i);
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
}

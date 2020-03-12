/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.outgoinginvoice;

import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.common.SaveException;
import com.hidetrace.admin.helper.outgoinginvoice.NewOutgoingInvoiceDialogHelper;
import com.hidetrace.admin.model.CategoryModel;
import com.hidetrace.admin.model.CertificateModel;
import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingInvoiceCertificateModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingInvoiceHideTypeCategoryModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.CategoryService;
import com.hidetrace.admin.service.CertificateService;
import com.hidetrace.admin.service.CompositeService;
import com.hidetrace.admin.service.HideTypeService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.outgoinginvoice.OutgoingLegalEntityInvoiceService;
import com.hidetrace.admin.view.outgoinginvoice.NewOutgoingInvoiceDialogView;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
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

    @Autowired
    private NewOutgoingInvoiceConfirmationMessagePanelController confirmMessagePanelController;

    @Autowired
    private CompositeService compositeService;

    @Autowired
    private CertificateService certificateService;

    private int nextY = 0;
    private List<JComboBox> hideTypeComboxList = new ArrayList<>();
    private List<JComboBox> categoryComboxList = new ArrayList<>();
    private List<JTextField> priceList = new ArrayList<>();
    private List<JLabel> labelList = new ArrayList<>();

    private void initView() {

        view.getCertificateTextField().setText("");
        view.getCommentTextField().setText("");
        view.getInvoiceIdTextField().setText("");

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
        if (view.getAddInvoiceButton().getActionListeners().length == 0) {
            view.getAddInvoiceButton().addActionListener(appContext.getBean(AddInvoiceButtonListener.class));

        }
        if (view.getAddNewArticleButton().getActionListeners().length == 0) {
            view.getAddNewArticleButton().addActionListener(appContext.getBean(OutgoingInvoiceAddNewArticleComponentsButtonActionListener.class));
        }
    }

    private void initData() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) view.getLegalEntityDropDown().getModel();
        model.removeAllElements();

        ArrayList<LegalEntityModel> entities = (ArrayList<LegalEntityModel>) legalEntityService.getAllLegalEntities();
        entities.forEach((entity) -> {
            model.addElement(entity);
        });

        DefaultComboBoxModel certificateDropDown = (DefaultComboBoxModel) view.getCertficateDropDown().getModel();
        certificateDropDown.removeAllElements();

        List<CertificateModel> certificates = certificateService.findAll();
        certificates.forEach((certificate) -> {
            certificateDropDown.addElement(certificate);
        });
    }

    private void removeArticleComponent(MouseEvent evt) {
        int index = labelList.indexOf(evt.getSource());
        if (index > -1) {
            JPanel panel = view.getArticleGridBagLayoutPanel();
            panel.remove(hideTypeComboxList.get(index));
            panel.remove(categoryComboxList.get(index));
            panel.remove(priceList.get(index));
            panel.remove(labelList.get(index));
            hideTypeComboxList.remove(index);
            categoryComboxList.remove(index);
            priceList.remove(index);
            labelList.remove(index);
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
    private static class OutgoingInvoiceAddNewArticleComponentsButtonActionListener implements ActionListener {

        @Autowired
        private NewOutgoingInvoiceDialogView view;

        @Autowired
        private NewOutgoingInvoiceDialogController controller;

        @Autowired
        private MessageDialog messagDialog;

        @Autowired
        private HideTypeService hideTypeService;

        @Autowired
        private CategoryService categoryService;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller.nextY < 10) {
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

    public JTextField[] arrayOfTextFields() {
        JTextField[] txtFields = {
            view.getInvoiceIdTextField()
        };
        return txtFields;
    }

    public boolean saveInvoice() {
        //Important to check that invoiceModel is not null, hideTypesAndCategories is not null certModel is not null
        //update certModel is never null

        //Need to get all of the hide type and category info here
        List<OutgoingInvoiceHideTypeCategoryModel> hideTypesAndCategories = getInvoiceHideTypeAndCategoryInfo();

        //Need to get the certificate info here
        CertificateModel certModel = (CertificateModel) view.getCertficateDropDown().getSelectedItem();
        OutgoingInvoiceCertificateModel outgoingInvoiceCertModel = new OutgoingInvoiceCertificateModel();
        outgoingInvoiceCertModel.setCertificateId(certModel.getCertificateId());
        outgoingInvoiceCertModel.setCertificateNumber(view.getCertificateTextField().getText());

        //Need to get the invoice info here
        OutgoingLegalEntityInvoiceModel invoiceModel = newInvoiceInfo();

        if (hideTypesAndCategories != null && invoiceModel != null) {
            try {
                return compositeService.saveOutgoingInvoiceHideTypeCategory(invoiceModel, hideTypesAndCategories, outgoingInvoiceCertModel);
            } catch (UnexpectedRollbackException ex) {
                messageDialog.ErrorCreatingInvoice();
            }
        }

        return false;
    }

    public void completeSave() {
        view.getInvoiceIdTextField().setText("");
        view.getCommentTextField().setText("");
        view.getCertificateTextField().setText("");
        view.getArticleGridBagLayoutPanel().removeAll();
        view.revalidate();
        view.repaint();
    }

    private OutgoingLegalEntityInvoiceModel newInvoiceInfo() {
        OutgoingLegalEntityInvoiceModel model = new OutgoingLegalEntityInvoiceModel();
        try {

            model.setLegalEntityId(((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getLegalEntityId());
            model.setName(view.getInvoiceIdTextField().getText());
            model.setDescription(view.getCommentTextField().getText());
            model.setDateTime(new java.sql.Timestamp(new java.util.Date().getTime()));

            return model;

        } catch (NumberFormatException ex) {
            if (!exception.isRaised()) {
                messageDialog.WrongFormat();
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

    public HashMap getConfirmInvoiceData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("legalEntity", ((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getName());
        map.put("legalEntityLabel", view.getLegalEntityLabel().getText());
        map.put("invoiceId", view.getInvoiceIdTextField().getText());
        map.put("invoiceIdLabel", view.getInvoiceIdLabel().getText());

        return map;
    }

    public boolean confirmData() {

        HashMap<String, String> data = getConfirmInvoiceData();
        HashMap<String, Object> objects = confirmMessagePanelController.getData();
        ((JLabel) objects.get("legalEntityLabel")).setText(data.get("legalEntityLabel"));
        ((JLabel) objects.get("legalEntity")).setText(data.get("legalEntity"));
        ((JLabel) objects.get("invoiceIdLabel")).setText(data.get("invoiceIdLabel"));
        ((JLabel) objects.get("invoiceId")).setText(data.get("invoiceId"));

        UIManager.put("OptionPane.yesButtonText", "Da");
        UIManager.put("OptionPane.noButtonText", "Ne");
        UIManager.put("OptionPane.cancelButtonText", "Otkaži");
        int dialogButton = 0;
        int dialogResult = JOptionPane.showConfirmDialog(null, confirmMessagePanelController.getConfirmationPanel(),
                "Potvrda", dialogButton);

        return dialogResult == JOptionPane.YES_OPTION;
    }

}

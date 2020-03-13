/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.model.CategoryModel;
import com.hidetrace.admin.model.CertificateModel;
import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeCategoryModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.CategoryService;
import com.hidetrace.admin.service.CertificateService;
import com.hidetrace.admin.service.HideTypeService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceCertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceHideTypeCategoryService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceDetailsView;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class IncomingInvoiceDetailsController {

    @Autowired
    private IncomingInvoiceDetailsView view;

    @Autowired
    private IncomingLegalEntityInvoiceService incomingLegalEntityInvoiceService;

    @Autowired
    private IncomingInvoiceCertificateService incomingInvoiceCertificateService;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private IncomingInvoiceHideTypeCategoryService incomingInvoiceHideTypeCategoryService;

    @Autowired
    private HideTypeService hideTypeService;

    @Autowired
    private CategoryService categoryService;

    private int nextY;
    private List<JComboBox> hideTypeComboxList = new ArrayList<>();
    private List<JComboBox> categoryComboxList = new ArrayList<>();
    private List<JTextField> priceList = new ArrayList<>();
    private List<JLabel> labelList = new ArrayList<>();
    List<CategoryModel> categories;
    List<HideTypeModel> hideTypes;
    List<IncomingInvoiceHideTypeCategoryModel> allArticles = new ArrayList<>();

    private int invoiceId;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    private void initListeners() {

    }

    private void initData() {
        view.getArticleGridBagLayoutPanel().removeAll();

        view.getJLabel7().setText("Vrsta");
        view.getJLabel7().setPreferredSize(new java.awt.Dimension(120, 30));
        GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        view.getArticleGridBagLayoutPanel().add(view.getJLabel7(), gridBagConstraints);

        view.getJLabel9().setText("Klasa/Kategorija");
        view.getJLabel9().setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        view.getArticleGridBagLayoutPanel().add(view.getJLabel9(), gridBagConstraints);

        view.getJLabel10().setText("Cijena");
        view.getJLabel10().setPreferredSize(new java.awt.Dimension(120, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        view.getArticleGridBagLayoutPanel().add(view.getJLabel10(), gridBagConstraints);

        view.revalidate();
        view.repaint();
        nextY = 1;

        hideTypes = hideTypeService.getAllHideTypes();
        categories = categoryService.getAllCategories();

        populateInvoiceId();
    }

    public void start() {
        initListeners();
        initData();
        initView();

    }

    public void passInvoiceId(int id) {
        this.invoiceId = id;
    }

    private void populateInvoiceId() {
        IncomingLegalEntityInvoiceModel invoice = incomingLegalEntityInvoiceService.findById(invoiceId).get();
        if (invoice != null) {
            view.getIncomingInvoiceIdTextfield().setText(String.valueOf(invoice.getInvId()));
            view.getInvoiceNameTextField().setText(invoice.getInvName());
            view.getGrossWeightTextField().setText(String.valueOf(invoice.getInvGrossWeight()));
            view.getNetWeightTextField().setText(String.valueOf(invoice.getInvNetWeight()));
            view.getAbroadReducedTextField().setText(String.valueOf(invoice.getInvAbroadReduced()));
            view.getSaltTextField().setText(String.valueOf(invoice.getInvSalt()));
            view.getDescriptionTextField().setText(invoice.getInvDescription());
            view.getDateTimeTextField().setText(String.valueOf(invoice.getInvTimeStamp()));
            view.getTotalLoadTextField().setText(String.valueOf(invoice.getInvTotalLoad()));
            view.getDifferenceTextField().setText(String.valueOf(invoice.getInvDifference()));

            IncomingInvoiceCertificateModel certificateModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(invoice.getInvId());
            if (certificateModel != null) {
                view.getCertificateTextField().setText(certificateModel.getCertificateNumber());

                IncomingInvoiceCertificateModel certModel = incomingInvoiceCertificateService.findByIncomingInvoiceId(invoice.getInvId());
                CertificateModel cert = certificateService.findById(certModel.getCertificateId()).get();
                view.getCertificateTypeTextField().setText(cert.getName());

                //List<CertificateModel> certificateModels = certificateService.findAll();
                //view.getCertificateTypeTextField().setText(certificateModels.get(certificateModel.getCertificateId() - 1).getName());
            }

            //get all articles (hide types and categories)
            List<IncomingInvoiceHideTypeCategoryModel> allArticlesList = incomingInvoiceHideTypeCategoryService.findIncomingInvoiceHideTypeCategoryByIncomingInvoiceId(invoice.getInvId());
            populateArticlesData(allArticlesList);
        }
    }

    private void populateArticlesData(List<IncomingInvoiceHideTypeCategoryModel> allArticlesList) {
        if (allArticlesList != null) {

            JPanel panel = view.getArticleGridBagLayoutPanel();
            GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();

            for (int i = 0; i < allArticlesList.size(); i++) {
                HideTypeModel hideTypeModel = hideTypeService.findById(allArticlesList.get(i).getHideTypeId()).get();
                JTextField hideTypeField = new JTextField(hideTypeModel.getName());
                hideTypeField.setBackground(new java.awt.Color(255, 255, 255));
                hideTypeField.setPreferredSize(new java.awt.Dimension(120, 30));
                hideTypeField.setEditable(false);
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(hideTypeField, gridBagConstraints);

                CategoryModel categoryModel = categoryService.getCategyById(allArticlesList.get(i).getCategoryId()).get();
                JTextField categoryField = new JTextField(categoryModel.getName());
                categoryField.setPreferredSize(new java.awt.Dimension(120, 30));
                categoryField.setBackground(new java.awt.Color(255, 255, 255));
                categoryField.setEditable(false);
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(categoryField, gridBagConstraints);

                JTextField field = new JTextField();
                field.setText(String.valueOf(allArticlesList.get(i).getPrice()));
                field.setPreferredSize(new java.awt.Dimension(60, 30));
                field.setEditable(false);
                field.setBackground(new java.awt.Color(255, 255, 255));
                gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 2;
                gridBagConstraints.gridy = nextY;
                gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
                gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
                panel.add(field, gridBagConstraints);

                panel.revalidate();
                panel.repaint();
                nextY++;
            }
        }

    }
}

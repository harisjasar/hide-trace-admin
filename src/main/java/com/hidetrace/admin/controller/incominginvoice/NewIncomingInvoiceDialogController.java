/*
 *
 * Contributors:
 *    Jashar
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.common.*;
import com.hidetrace.admin.helper.incominginvoice.NewIncomingInvoiceDialogHelper;
import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.category.CategoryModel;
import com.hidetrace.admin.model.certificate.CertificateModel;
import com.hidetrace.admin.model.incominginvoice.*;
import com.hidetrace.admin.service.CompositeService;
import com.hidetrace.admin.service.HideTypeService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.category.CategoryService;
import com.hidetrace.admin.service.certificate.CertificateService;
import com.hidetrace.admin.service.incominginvoice.*;
import com.hidetrace.admin.view.incominginvoice.NewIncomingInvoicePanelView;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
import org.decimal4j.util.DoubleRounder;
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
public class NewIncomingInvoiceDialogController {

    @Autowired
    private NewIncomingInvoicePanelView view;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private NewIncomingInvoiceDialogHelper helper;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private IncomingLegalEntityInvoiceService incomingLegalEntityInvoiceService;

    @Autowired
    private IncomingInvoiceCertificateService incomingInvoiceCertificateService;

    @Autowired
    private SaveException exception;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    private NewIncomingInvoiceConfirmationMessagePanelController confirmMessagePanelController;

    @Autowired
    private CalculateInvoiceData calculateInvoiceData;

    @Autowired
    private IncomingInvoiceHideTypeCategoryService incomingInvoiceHideTypeCategoryService;

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

        for (JTextField field : getAllTextFields()) {
            field.setText("");
        }
        view.getCommentTextField().setText("");
        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getAddInvoiceButton().getActionListeners().length == 0) {
            view.getAddInvoiceButton().addActionListener(appContext.getBean(AddInvoiceButtonListener.class));

        }
        if (view.getAddNewArticleButton().getActionListeners().length == 0) {
            view.getAddNewArticleButton().addActionListener(appContext.getBean(AddNewArticleComponentsButtonActionListener.class));
        }
        for (JTextField field : getAllTextFields()) {
            if (field != view.getInvoiceIdTextField()) {
                if (field != view.getCertificateTextField()) {
                    field.addKeyListener(appContext.getBean(TextFieldNumberFormat.class));
                }
            }

        }
        appContext.getBean(CheckFormatCorrectIncomingInvoice.class).start();
        Thread t1 = new Thread(appContext.getBean(CheckFormatCorrectIncomingInvoice.class), "T1");
        t1.start();
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

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private IncomingLegalEntityInvoiceModel newInvoiceInfo() {
        IncomingLegalEntityInvoiceModel model = new IncomingLegalEntityInvoiceModel();
        try {
            int LegalEntityID = ((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getLegalEntityId();
            model.setInvLegalEntityId(LegalEntityID);

            String InvoiceName = view.getInvoiceIdTextField().getText();
            model.setInvName(InvoiceName);

            double InvGrossWeight = Double.parseDouble(view.getControlWeightInvoicegrossTextField().getText());
            model.setInvGrossWeight(InvGrossWeight);

            double InvNetWeight = Double.parseDouble(view.getControlWeightCompanyGrossTextField().getText());
            model.setInvNetWeight(InvNetWeight);

            double InvAbroadReduced = Double.parseDouble(view.getAbroadReducedTextField().getText());
            model.setInvAbroadReduced(InvAbroadReduced);

            double InvSalt = Double.parseDouble(view.getSaltControlTextField().getText());
            model.setInvSalt(InvSalt);

            String InvDescription = view.getCommentTextField().getText();
            model.setInvDescription(InvDescription);

            model.setInvDifference(DoubleRounder.round(calculateInvoiceData.differenceGrossNet(InvSalt, (InvGrossWeight - InvNetWeight), InvAbroadReduced, InvGrossWeight), 3));

            model.setInvTimeStamp(new java.sql.Timestamp(new java.util.Date().getTime()));
            model.setInvTotalLoad(DoubleRounder.round(calculateInvoiceData.calculateTotalLoad(InvSalt, (InvGrossWeight - InvNetWeight), InvGrossWeight), 3));

            return model;
        } catch (NumberFormatException ex) {
            messageDialog.WrongFormat();
            return null;
        }
    }

    public boolean saveInvoice() {
        //Important to check that invoiceModel is not null, hideTypesAndCategories is not null certModel is not null
        //update certModel is never null

        //Need to get all of the hide type and category info here
        List<IncomingInvoiceHideTypeCategoryModel> hideTypesAndCategories = getInvoiceHideTypeAndCategoryInfo();

        //Need to get the certificate info here
        CertificateModel certModel = (CertificateModel) view.getCertficateDropDown().getSelectedItem();
        IncomingInvoiceCertificateModel incomingInvoiceCertModel = new IncomingInvoiceCertificateModel();
        incomingInvoiceCertModel.setCertificateId(certModel.getCertificateId());
        incomingInvoiceCertModel.setCertificateNumber(view.getCertificateTextField().getText());

        //Need to get the invoice info here
        IncomingLegalEntityInvoiceModel invoiceModel = newInvoiceInfo();

        if (hideTypesAndCategories != null && invoiceModel != null) {
            try {
                return compositeService.saveIncomingInvoiceHideTypeCategory(invoiceModel, hideTypesAndCategories, incomingInvoiceCertModel);
            } catch (UnexpectedRollbackException ex) {
                messageDialog.ErrorCreatingInvoice();
            }
        }

        return false;
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

                    IncomingInvoiceHideTypeCategoryModel model = new IncomingInvoiceHideTypeCategoryModel();
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
    private static class TextFieldNumberFormat extends KeyAdapter {

        @Autowired
        private NewIncomingInvoicePanelView view;

        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            try {
                double j = Double.parseDouble(((JTextField) evt.getComponent()).getText());
                ((JTextField) evt.getComponent()).setBackground(new java.awt.Color(255, 255, 255));
            } catch (NumberFormatException ex) {
                ((JTextField) evt.getComponent()).setBackground(new java.awt.Color(255, 51, 51));

            }
        }
    }

    @Component
    private static class AddInvoiceButtonListener implements ActionListener {

        @Autowired
        private NewIncomingInvoiceDialogHelper helper;

        @Override
        public void actionPerformed(ActionEvent e) {
            addInvoice();

        }

        public void addInvoice() {
            helper.addInvoice();
        }

    }

    @Component
    private static class AddNewArticleComponentsButtonActionListener implements ActionListener {

        @Autowired
        private NewIncomingInvoicePanelView view;

        @Autowired
        private NewIncomingInvoiceDialogController controller;

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

    private JTextField[] getAllTextFields() {
        JTextField[] txtFields = {
            view.getAbroadReducedTextField(),
            view.getCertificateTextField(),
            view.getControlWeightCompanyGrossTextField(),
            view.getControlWeightInvoicegrossTextField(),
            view.getInvoiceIdTextField(),
            view.getSaltControlTextField()
        };
        return txtFields;
    }

    public void removeArticleComponent(java.awt.event.MouseEvent evt) {
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

    public JTextField[] arrayOfTextFields() {
        JTextField[] txtFields = {
            view.getAbroadReducedTextField(),
            view.getCertificateTextField(),
            view.getControlWeightCompanyGrossTextField(),
            view.getControlWeightInvoicegrossTextField(),
            view.getInvoiceIdTextField(),
            view.getSaltControlTextField()
        };

        return txtFields;
    }

    public void completeSave() {
        for (JTextField field : getAllTextFields()) {
            field.setText("");
        }

        view.getArticleGridBagLayoutPanel().removeAll();
        view.getArticleGridBagLayoutPanel().revalidate();
        view.getArticleGridBagLayoutPanel().repaint();
        nextY = 0;
        view.getCommentTextField().setText("");
        Thread t1 = new Thread(appContext.getBean(CheckFormatCorrectIncomingInvoice.class
        ), "T1");
        t1.start();
    }

    public HashMap getConfirmInvoiceData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("legalEntity", ((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getName());
        map.put("legalEntityLabel", view.getLegalEntityLabel().getText());
        map.put("invoiceId", view.getInvoiceIdTextField().getText());
        map.put("invoiceIdLabel", view.getInvoiceIdLabel().getText());
        map.put("grossWeight", view.getControlWeightInvoicegrossTextField().getText());
        map.put("grossWeightLabel", view.getGrossWeightLabel().getText());
        map.put("netWeight", view.getControlWeightCompanyGrossTextField().getText());
        map.put("netWeightLabel", view.getNetWeightLabel().getText());
        map.put("abroadReduced", view.getAbroadReducedTextField().getText());
        map.put("abroadReducedLabel", view.getAbroadReducedLabel().getText());
        map.put("salt", view.getSaltControlTextField().getText());
        map.put("saltLabel", view.getSaltLabel().getText());
        map.put("comment", view.getCommentTextField().getText());
        map.put("commentLabel", view.getCommentLabel().getText());
        map.put("certificateLabel", view.getCertficateDropDown().getSelectedItem().toString());
        map.put("certificate", view.getCertificateTextField().getText());

        return map;
    }

    public boolean confirmData() {

        HashMap<String, String> data = getConfirmInvoiceData();
        HashMap<String, Object> objects = confirmMessagePanelController.getData();
        ((JLabel) objects.get("legalEntityLabel")).setText(data.get("legalEntityLabel"));
        ((JLabel) objects.get("legalEntity")).setText(data.get("legalEntity"));
        ((JLabel) objects.get("invoiceIdLabel")).setText(data.get("invoiceIdLabel"));
        ((JLabel) objects.get("invoiceId")).setText(data.get("invoiceId"));
        ((JLabel) objects.get("grossWeightLabel")).setText(data.get("grossWeightLabel"));
        ((JLabel) objects.get("grossWeight")).setText(data.get("grossWeight"));
        ((JLabel) objects.get("netWeightLabel")).setText(data.get("netWeightLabel"));
        ((JLabel) objects.get("netWeight")).setText(data.get("netWeight"));
        ((JLabel) objects.get("abroadReducedLabel")).setText(data.get("abroadReducedLabel"));
        ((JLabel) objects.get("abroadReduced")).setText(data.get("abroadReduced"));
        ((JLabel) objects.get("saltLabel")).setText(data.get("saltLabel"));
        ((JLabel) objects.get("salt")).setText(data.get("salt"));
        ((JLabel) objects.get("certificateLabel")).setText(data.get("certificateLabel"));
        ((JLabel) objects.get("certificate")).setText(data.get("certificate"));
        ((JLabel) objects.get("cowLabel")).setText(data.get("cowLabel") != null ? data.get("cowLabel") : "");
        ((JLabel) objects.get("cow")).setText(data.get("cow") != null ? data.get("cow") : "");
        ((JLabel) objects.get("bullLabel")).setText(data.get("bullLabel") != null ? data.get("bullLabel") : "");
        ((JLabel) objects.get("bull")).setText(data.get("bull") != null ? data.get("bull") : "");
        ((JLabel) objects.get("calfLabel")).setText(data.get("calfLabel") != null ? data.get("calfLabel") : "");
        ((JLabel) objects.get("calf")).setText(data.get("calf") != null ? data.get("calf") : "");
        ((JLabel) objects.get("commentLabel")).setText(data.get("commentLabel"));

        StringBuilder comment = new StringBuilder();

        comment.append("<html>");
        comment.append(data.get("comment"));
        comment.append("</html>");
        ((JLabel) objects.get("comment")).setText(comment.toString());

        UIManager.put("OptionPane.yesButtonText", "Da");
        UIManager.put("OptionPane.noButtonText", "Ne");
        UIManager.put("OptionPane.cancelButtonText", "Otkaži");
        int dialogButton = 0;
        int dialogResult = JOptionPane.showConfirmDialog(null, confirmMessagePanelController.getConfirmationPanel(),
                "Potvrda", dialogButton);

        return dialogResult == JOptionPane.YES_OPTION;

    }

    @Component
    public class CheckFormatCorrectIncomingInvoice implements Runnable {

        @Autowired
        private NewIncomingInvoicePanelView view;

        private volatile boolean correct = false;

        @Override
        public void run() {
            List<JTextField> list = new ArrayList<>();
            list.add(view.getControlWeightCompanyGrossTextField());
            list.add(view.getControlWeightInvoicegrossTextField());
            list.add(view.getAbroadReducedTextField());
            list.add(view.getSaltControlTextField());

            while (!correct) {
                boolean good = true;
                for (JTextField field : list) {
                    if (field.isEnabled()) {
                        if (!checkCorrect(field.getText())) {
                            good = false;
                        }
                    }
                }
                if (!good) {
                    view.getAddInvoiceButton().setEnabled(false);
                } else {
                    view.getAddInvoiceButton().setEnabled(true);
                }
            }

        }

        public boolean checkCorrect(String a) {
            try {
                double n = Double.parseDouble(a);
            } catch (NumberFormatException ex) {
                return false;
            }
            return true;
        }

        public void stop() {
            correct = true;
        }

        public void start() {
            correct = false;
        }
    }

    public void StopCheckFormatCorrect() {
        appContext.getBean(CheckFormatCorrectIncomingInvoice.class
        ).stop();
    }
}

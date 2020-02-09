/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.common.HideTypeEnum;
import com.hidetrace.admin.common.HideTypeValues;
import com.hidetrace.admin.common.incominginvoice.IncomingCertificateEnum;
import com.hidetrace.admin.common.incominginvoice.IncomingCertificateValues;
import com.hidetrace.admin.helper.incominginvoice.NewIncomingInvoiceDialogHelper;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceCertificateModel;
import com.hidetrace.admin.model.incominginvoice.IncomingInvoiceHideTypeModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceCertificateService;
import com.hidetrace.admin.service.incominginvoice.IncomingInvoiceHideTypeService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.view.incominginvoice.NewIncomingInvoiceDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import lombok.Data;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
public class NewIncomingInvoiceDialogController {

    @Autowired
    private NewIncomingInvoiceDialogView view;

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
    private IncomingInvoiceHideTypeService incomingInvoiceHideTypeService;

    public NewIncomingInvoiceDialogController(NewIncomingInvoiceDialogView view) {
        this.view = view;
    }

    private void initView() {
        for (JTextField field : getArticleTextFields()) {
            field.setEnabled(false);
        }
        for (JTextField field : getAllTextFields()) {
            field.setText("");
        }
        for (JToggleButton btn : getArticleToggleBtns()) {
            btn.setSelected(false);
        }

        view.getCommentTextField().setText("");
        view.getInfoLabel().setText("");

        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getAddInvoiceButton().getActionListeners().length == 0) {
            view.getAddInvoiceButton().addActionListener(appContext.getBean(AddInvoiceButtonListener.class));

        }

        for (JToggleButton tglBtn : getArticleToggleBtns()) {
            if (tglBtn.getActionListeners().length == 0) {
                tglBtn.addActionListener(appContext.getBean(ArticleTglButtonActionListener.class));
            }
        }

        for (JTextField field : getAllTextFields()) {
            if (field != view.getInvoiceIdTextField()) {
                if (field != view.getCertificateTextField()) {
                    field.addKeyListener(appContext.getBean(TextFieldNumberFormat.class));
                }
            }

        }
    }

    private void initData() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) view.getLegalEntityDropDown().getModel();
        model.removeAllElements();

        ArrayList<LegalEntityModel> entities = (ArrayList<LegalEntityModel>) legalEntityService.getAllLegalEntities();
        entities.forEach((entity) -> {
            model.addElement(entity);
        });

    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    public List<IncomingInvoiceHideTypeModel> newIncomingInvoiceHideTypeInfo() {
        JTextField[] articleTxtFields = getArticleTextFields();
        JToggleButton[] tglBtns = getArticleToggleBtns();

        HashMap hydeTypeHashMap = new HashMap();
        for (int i = 0; i < tglBtns.length; i++) {
            hydeTypeHashMap.put(tglBtns[i], articleTxtFields[i]);
        }

        List<IncomingInvoiceHideTypeModel> list = new ArrayList<>();

        for (JToggleButton tglBtn : tglBtns) {
            if (tglBtn.isSelected()) {
                JTextField field = ((JTextField) hydeTypeHashMap.get(tglBtn));
                if (!field.getText().isEmpty()) {
                    IncomingInvoiceHideTypeModel model = new IncomingInvoiceHideTypeModel();
                    model.setPrice(Double.parseDouble(field.getText()));
                    String hideType = tglBtn.getText();
                    if (hideType.equals(HideTypeEnum.COW.getValue())) {
                        model.setHideTypeId(HideTypeValues.COW);
                    } else if (hideType.equals(HideTypeEnum.BULL.getValue())) {
                        model.setHideTypeId(HideTypeValues.BULL);
                    } else {
                        model.setHideTypeId(HideTypeValues.CALF);
                    }

                    list.add(model);
                }
            }
        }

        return list;
    }

    public IncomingInvoiceCertificateModel newIncomingInvoiceCertificateInfo() {
        IncomingInvoiceCertificateModel model = new IncomingInvoiceCertificateModel();
        String cert = view.getCertficateDropDown().getSelectedItem().toString();
        if (cert.equals(IncomingCertificateEnum.ZVUD.getValue())) {
            model.setCertificateId(IncomingCertificateValues.ZVUD);
        } else {
            model.setCertificateId(IncomingCertificateValues.VS_B2);
        }

        model.setCertificateNumber(view.getCertificateTextField().getText());

        return model;
    }

    public IncomingLegalEntityInvoiceModel newInvoiceInfo() {
        IncomingLegalEntityInvoiceModel model = new IncomingLegalEntityInvoiceModel();
        int LegalEntityID = ((LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem()).getLegalEntityID();
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

        model.setInvDifference(DoubleRounder.round(differenceGrossNet(InvSalt, (InvGrossWeight - InvNetWeight), InvAbroadReduced, InvGrossWeight), 3));

        model.setInvTimeStamp(new java.sql.Timestamp(new java.util.Date().getTime()));
        model.setInvTotalLoad(DoubleRounder.round(calculateTotalLoad(InvSalt, (InvGrossWeight - InvNetWeight), InvGrossWeight), 3));

        return incomingLegalEntityInvoiceService.saveIncomingInvoice(model);

    }

    public void saveInvoice() {
        int newInvoiceId = newInvoiceInfo().getInvId();
        IncomingInvoiceCertificateModel incomingInvoiceCertModel = newIncomingInvoiceCertificateInfo();
        incomingInvoiceCertModel.setIncomingInvoiceId(newInvoiceId);
        incomingInvoiceCertificateService.saveIncomingInvoiceCertificate(incomingInvoiceCertModel);
        newIncomingInvoiceHideTypeInfo().stream().map((model) -> {
            model.setIncomingInvoiceId(newInvoiceId);
            return model;
        }).forEachOrdered((model) -> {
            incomingInvoiceHideTypeService.saveIncomingInvoiceHideType(model);
        });

    }

    @Component
    private static class TextFieldNumberFormat extends KeyAdapter {

        @Autowired
        private NewIncomingInvoiceDialogView view;

        @Override
        public void keyReleased(java.awt.event.KeyEvent evt) {
            try {
                double j = Double.parseDouble(((JTextField) evt.getComponent()).getText());
                view.infoLabel.setText("");
            } catch (NumberFormatException ex) {
                view.infoLabel.setText("neispravan format");
            }
        }
    }

    @Component
    private static class AddInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

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
    private static class ArticleTglButtonActionListener implements ActionListener {

        @Autowired
        private NewIncomingInvoiceDialogView view;

        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton tglbtn = (JToggleButton) e.getSource();
            checkSelected(tglbtn, getTextFieldByTglBtn(tglbtn));
        }

        private JTextField getTextFieldByTglBtn(JToggleButton tglBtn) {
            JTextField[] articleTxtFields = {view.getBulltxtField(), view.getCowtxtField(), view.getCalftxtField()};
            JToggleButton[] tglBtns = {view.getBullArticleTglBtn(), view.getCowArticleTglBtn(), view.getCalfArticleTglBtn()};

            HashMap hydeTypeHashMap = new HashMap();
            for (int i = 0; i < tglBtns.length; i++) {
                hydeTypeHashMap.put(tglBtns[i], articleTxtFields[i]);
            }

            return (JTextField) hydeTypeHashMap.get(tglBtn);

        }
    }

    private static void checkSelected(JToggleButton tglbtn, JTextField txtfield) {
        if (tglbtn.isSelected()) {
            txtfield.setEnabled(true);
        } else {
            txtfield.setEnabled(false);
            txtfield.setText("");
        }
    }

    private JTextField[] getAllTextFields() {
        JTextField[] txtFields = {
            view.getAbroadReducedTextField(),
            view.getCertificateTextField(),
            view.getControlWeightCompanyGrossTextField(),
            view.getControlWeightInvoicegrossTextField(),
            view.getInvoiceIdTextField(),
            view.getSaltControlTextField(),
            view.getBulltxtField(),
            view.getCalftxtField(),
            view.getCowtxtField()
        };
        return txtFields;
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

    public JToggleButton[] getArticleToggleBtns() {
        JToggleButton[] tglBtns = {
            view.getBullArticleTglBtn(), view.getCalfArticleTglBtn(), view.getCowArticleTglBtn()
        };

        return tglBtns;
    }

    public JTextField[] getArticleTextFields() {
        JTextField[] articleTxtFields = {
            view.getBulltxtField(), view.getCalftxtField(), view.getCowtxtField()
        };

        return articleTxtFields;
    }

    public void completeSave() {
        view.dispose();
    }

    private double differenceGrossNet(double salt, double weightDifference, double abroadReduced, double abroadWeight) {
        return (salt + ((weightDifference * 100) / abroadWeight)) - abroadReduced;
    }

    private Double calculateTotalLoad(Double salt, Double weightDifference, Double wrossWeight) {
        return salt + ((weightDifference * 100) / wrossWeight);
    }
}

/*
 *
 * Contributors:
 *    Jashar
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.common.*;
import com.hidetrace.admin.common.incominginvoice.*;
import com.hidetrace.admin.helper.incominginvoice.NewIncomingInvoiceDialogHelper;
import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.incominginvoice.*;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.incominginvoice.*;
import com.hidetrace.admin.view.incominginvoice.NewIncomingInvoiceDialogView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
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

    @Autowired
    private SaveException exception;

    @Autowired
    private MessageDialog messageDialog;

    @Autowired
    NewOutgoingInvoiceConfirmationMessagePanelController confirmMessagePanelController;

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

        if (view.getWindowListeners().length == 0) {
            view.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    appContext.getBean(CheckFormatCorrect.class).stop();
                }
            });
        }

        appContext.getBean(CheckFormatCorrect.class).start();
        Thread t1 = new Thread(appContext.getBean(CheckFormatCorrect.class), "T1");
        t1.start();
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

        try {
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
        } catch (NumberFormatException ex) {
            if (!exception.isRaised()) {
                messageDialog.WrongFormat();
                exception.setRaised(true);
            }
        }
        return null;
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

    private IncomingLegalEntityInvoiceModel newInvoiceInfo() {
        IncomingLegalEntityInvoiceModel model = new IncomingLegalEntityInvoiceModel();
        try {
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

            IncomingLegalEntityInvoiceModel model_ = incomingLegalEntityInvoiceService.saveIncomingInvoice(model);
            return model_;
        } catch (NumberFormatException | UnexpectedRollbackException ex) {
            if (!exception.isRaised()) {
                messageDialog.WrongFormat();
                exception.setRaised(true);
            }
        }
        return null;
    }

    public boolean saveInvoice() {
        List<IncomingInvoiceHideTypeModel> newIncomingInvoiceHideTypeInfo = newIncomingInvoiceHideTypeInfo();
        IncomingLegalEntityInvoiceModel invoiceModel = null;
        if (!exception.isRaised()) {
            invoiceModel = newInvoiceInfo();
        }
        int newInvoiceId = invoiceModel != null ? invoiceModel.getInvId() : -1;
        if (invoiceModel != null) {
            IncomingInvoiceCertificateModel incomingInvoiceCertModel = newIncomingInvoiceCertificateInfo();
            incomingInvoiceCertModel.setIncomingInvoiceId(newInvoiceId);
            incomingInvoiceCertificateService.saveIncomingInvoiceCertificate(incomingInvoiceCertModel);
            newIncomingInvoiceHideTypeInfo.stream().map((model) -> {
                model.setIncomingInvoiceId(newInvoiceId);
                return model;
            }).forEachOrdered(incomingInvoiceHideTypeService::saveIncomingInvoiceHideType);

            messageDialog.InvoiceSuccessfullyCreated(invoiceModel.getInvName());
            return true;
        } else {
            messageDialog.ErrorCreatingInvoice();
            exception.setRaised(false);
        }
        return false;
    }

    @Component
    private static class TextFieldNumberFormat extends KeyAdapter {

        @Autowired
        private NewIncomingInvoiceDialogView view;

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
        for (JTextField field : getAllTextFields()) {
            field.setText("");
        }
        for (JToggleButton btn : getArticleToggleBtns()) {
            btn.setSelected(false);
        }
        view.getCommentTextField().setText("");
        view.getBulltxtField().setEnabled(false);
        view.getCowtxtField().setEnabled(false);
        view.getCalftxtField().setEnabled(false);

        Thread t1 = new Thread(appContext.getBean(CheckFormatCorrect.class), "T1");
        t1.start();
    }

    private double differenceGrossNet(double salt, double weightDifference, double abroadReduced, double abroadWeight) {
        return (salt + ((weightDifference * 100) / abroadWeight)) - abroadReduced;
    }

    private Double calculateTotalLoad(Double salt, Double weightDifference, Double wrossWeight) {
        return salt + ((weightDifference * 100) / wrossWeight);
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
        if (view.getCowArticleTglBtn().isSelected()) {
            map.put("cowLabel", view.getCowArticleTglBtn().getText());
            map.put("cow", view.getCowtxtField().getText());
        }
        if (view.getBullArticleTglBtn().isSelected()) {
            map.put("bullLabel", view.getBullArticleTglBtn().getText());
            map.put("bull", view.getBulltxtField().getText());
        }
        if (view.getCalfArticleTglBtn().isSelected()) {
            map.put("calfLabel", view.getCalfArticleTglBtn().getText());
            map.put("calf", view.getCalftxtField().getText());
        }

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
    public class CheckFormatCorrect implements Runnable {

        @Autowired
        private NewIncomingInvoiceDialogView view;

        private volatile boolean correct = false;

        @Override
        public void run() {
            List<JTextField> list = new ArrayList<>();
            JTextField[] fields = getArticleTextFields();
            list.addAll(Arrays.asList(fields));
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
        appContext.getBean(CheckFormatCorrect.class).stop();
    }
}

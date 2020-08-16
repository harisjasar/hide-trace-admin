/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller;

import com.hidetrace.admin.common.Utils;
import com.hidetrace.admin.controller.category.CategoryNewController;
import com.hidetrace.admin.controller.category.CategoryReviewController;
import com.hidetrace.admin.controller.category.CategoryUpdateController;
import com.hidetrace.admin.controller.certificate.CertificateNewController;
import com.hidetrace.admin.controller.certificate.CertificateReviewController;
import com.hidetrace.admin.controller.certificate.CertificateUpdateController;
import com.hidetrace.admin.controller.certificate.certificatepreview.CertificatePreviewController;
import com.hidetrace.admin.controller.hidetype.HideTypeNewController;
import com.hidetrace.admin.controller.hidetype.HideTypeReviewController;
import com.hidetrace.admin.controller.hidetype.HideTypeUpdateController;
import com.hidetrace.admin.controller.incominginvoice.IncomingInvoiceInvoicesListController;
import com.hidetrace.admin.controller.incominginvoice.IncomingInvoiceUpdateController;
import com.hidetrace.admin.controller.incominginvoice.NewIncomingInvoiceDialogController;
import com.hidetrace.admin.controller.legalentity.LegalEntityListController;
import com.hidetrace.admin.controller.legalentity.LegalEntityUpdateController;
import com.hidetrace.admin.controller.legalentity.NewLegalEntityDialogController;
import com.hidetrace.admin.controller.outgoinginvoice.NewOutgoingInvoiceDialogController;
import com.hidetrace.admin.controller.outgoinginvoice.OutgoingInvoiceInvoicesListController;
import com.hidetrace.admin.controller.outgoinginvoice.OutgoingInvoiceUpdateController;
import com.hidetrace.admin.model.OperatorModel;
import com.hidetrace.admin.view.AdminView;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceInvoicesListView;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceUpdateView;
import com.hidetrace.admin.view.incominginvoice.NewIncomingInvoicePanelView;
import com.hidetrace.admin.view.outgoinginvoice.NewOutgoingInvoicePanelView;
import com.hidetrace.admin.view.outgoinginvoice.OutgoingInvoiceInvoicesListView;
import com.hidetrace.admin.view.outgoinginvoice.OutgoingInvoiceUpdateView;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class AdminController {

    @Autowired
    private AdminView view;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private OperatorModel operModel;

    public AdminController(AdminView view) {
        this.view = view;
    }

    private void initView() {
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private void initListeners() {
        if (view.getUpdateIncomingInvoiceButton().getActionListeners().length == 0) {
            view.getUpdateIncomingInvoiceButton().addActionListener(appContext.getBean(UpdateIncomingInvoiceButtonListener.class));
        }
        if (view.getNewIncomingInvoiceButton().getActionListeners().length == 0) {
            view.getNewIncomingInvoiceButton().addActionListener(appContext.getBean(NewIncomingInvoiceButtonListener.class));
        }
        if (view.getNewOutgoingInvoiceButton().getActionListeners().length == 0) {
            view.getNewOutgoingInvoiceButton().addActionListener(appContext.getBean(NewOutgoingInvoiceButtonListener.class));
        }

        if (view.getNewAddLegalEntityButton().getActionListeners().length == 0) {
            view.getNewAddLegalEntityButton().addActionListener(appContext.getBean(NewLegalEntityButtonListener.class));
        }
        if (view.getReviewIncomingInvoicesButton().getActionListeners().length == 0) {
            view.getReviewIncomingInvoicesButton().addActionListener(appContext.getBean(ReviewIncomingInvoicesButtonListener.class));
        }
        if (view.getUpdateLegalEntityButton().getActionListeners().length == 0) {
            view.getUpdateLegalEntityButton().addActionListener(appContext.getBean(UpdateLegalEntityButtonButtonListener.class));
        }

        if (view.getReviewLegalEntitiesButton().getActionListeners().length == 0) {
            view.getReviewLegalEntitiesButton().addActionListener(appContext.getBean(ReviewLegalEntitiesButtonListener.class));
        }

        if (view.getUpdateOutgoingInvoiceButton().getActionListeners().length == 0) {
            view.getUpdateOutgoingInvoiceButton().addActionListener(appContext.getBean(UpdateOutgoingInvoiceButtonListener.class));
        }
        if (view.getReviewOutgoingInvoiceButton().getActionListeners().length == 0) {
            view.getReviewOutgoingInvoiceButton().addActionListener(appContext.getBean(ReviewOutgoingInvoiceButtonListener.class));
        }
        if (view.getNewHideTypeButton().getActionListeners().length == 0) {
            view.getNewHideTypeButton().addActionListener(appContext.getBean(NewHideTypeButtonListener.class));
        }
        if (view.getUpdateHideTypeButton().getActionListeners().length == 0) {
            view.getUpdateHideTypeButton().addActionListener(appContext.getBean(UpdateHideTypeButtonListener.class));
        }
        if (view.getNewCategoryButton().getActionListeners().length == 0) {
            view.getNewCategoryButton().addActionListener(appContext.getBean(NewCategoryButtonListener.class));
        }
        if (view.getUpdateCategory().getActionListeners().length == 0) {
            view.getUpdateCategory().addActionListener(appContext.getBean(UpdateCategoryButtonListener.class));
        }
        if (view.getReviewHideTypeButton().getActionListeners().length == 0) {
            view.getReviewHideTypeButton().addActionListener(appContext.getBean(ReviewHideTypeButtonListener.class));
        }
        if (view.getReviewCategoryButton().getActionListeners().length == 0) {
            view.getReviewCategoryButton().addActionListener(appContext.getBean(ReviewCategoryButtonListener.class));
        }
        if (view.getNewCertificateButton().getActionListeners().length == 0) {
            view.getNewCertificateButton().addActionListener(appContext.getBean(NewCertificateButtonListener.class));
        }
        if (view.getUpdateCertificateButton().getActionListeners().length == 0) {
            view.getUpdateCertificateButton().addActionListener(appContext.getBean(UpdateCertificateButtonListener.class));
        }
        if (view.getReviewCertificateButton().getActionListeners().length == 0) {
            view.getReviewCertificateButton().addActionListener(appContext.getBean(ReviewCertificateButtonListener.class));
        }
        if (view.getIncomingCertificatePreviewButton().getActionListeners().length == 0) {
            view.getIncomingCertificatePreviewButton().addActionListener(appContext.getBean(IncomingCertificatePreviewButtonListener.class));
        }

        if (view.getOutgoingCertificatePreviewButton().getActionListeners().length == 0) {
            view.getOutgoingCertificatePreviewButton().addActionListener(appContext.getBean(OutgoingCertificatePreviewButtonListener.class));
        }
        if (view.getLegalEntitySideButton().getActionListeners().length == 0) {
            view.getLegalEntitySideButton().addActionListener(appContext.getBean(LegalEntitySideButtonListener.class));
        }
        if (view.getIncomingLegalEntitySideButton().getActionListeners().length == 0) {
            view.getIncomingLegalEntitySideButton().addActionListener(appContext.getBean(IncomingLegalEntitySideButtonListener.class));
        }
        if (view.getOutgoingLegalEntitySideButton().getActionListeners().length == 0) {
            view.getOutgoingLegalEntitySideButton().addActionListener(appContext.getBean(OutgoingLegalEntitySideButtonListener.class));
        }

    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    private void initData() {
        view.getCurrentLoggedOnUserLabel().setText("Dobrodošli, " + operModel.getFirstName());
    }

    @Component
    private static class OutgoingLegalEntitySideButtonListener implements ActionListener {

        @Autowired
        private AdminView view;

        @Override
        public void actionPerformed(ActionEvent e) {
            ((CardLayout) view.getPnlCards().getLayout()).show(view.getPnlCards(), "card3");
        }

    }

    @Component
    private static class IncomingLegalEntitySideButtonListener implements ActionListener {

        @Autowired
        private AdminView view;

        @Override
        public void actionPerformed(ActionEvent e) {
            ((CardLayout) view.getPnlCards().getLayout()).show(view.getPnlCards(), "card2");
        }

    }

    @Component
    private static class LegalEntitySideButtonListener implements ActionListener {

        @Autowired
        private AdminView view;

        @Override
        public void actionPerformed(ActionEvent e) {
            ((CardLayout) view.getPnlCards().getLayout()).show(view.getPnlCards(), "card1");
        }

    }

    @Component
    private static class OutgoingCertificatePreviewButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            CertificatePreviewController contro = appContext.getBean(CertificatePreviewController.class);
            contro.setType(1);
            contro.start();

        }

    }

    @Component
    private static class IncomingCertificatePreviewButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            CertificatePreviewController contro = appContext.getBean(CertificatePreviewController.class);
            contro.setType(0);
            contro.start();

        }

    }

    @Component
    private static class ReviewCertificateButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(CertificateReviewController.class).start();

        }

    }

    @Component
    private static class UpdateCertificateButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(CertificateUpdateController.class).start();

        }

    }

    @Component
    private static class NewCertificateButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(CertificateNewController.class).start();

        }

    }

    @Component
    private static class ReviewCategoryButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(CategoryReviewController.class).start();

        }

    }

    @Component
    private static class ReviewHideTypeButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(HideTypeReviewController.class).start();

        }

    }

    @Component
    private static class UpdateCategoryButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(CategoryUpdateController.class).start();

        }

    }

    @Component
    private static class UpdateHideTypeButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(HideTypeUpdateController.class).start();

        }

    }

    @Component
    private static class NewCategoryButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(CategoryNewController.class).start();

        }

    }

    @Component
    private static class NewHideTypeButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(HideTypeNewController.class).start();

        }

    }

    @Component
    private static class ReviewIncomingInvoicesButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            AdminView adminView = appContext.getBean(AdminView.class);
            IncomingInvoiceInvoicesListView incomingInvoiceListView = appContext.getBean(IncomingInvoiceInvoicesListView.class);

            //@TODO remove commented and the rest
            //trying to speed up UI with removing all elements first
            adminView.getAdminViewCardsPanel().removeAll();
            String randomString = Utils.generateRandomString(8);
            adminView.getAdminViewCardsPanel().add(incomingInvoiceListView, randomString);
            ((CardLayout) adminView.getAdminViewCardsPanel().getLayout()).show(adminView.getAdminViewCardsPanel(), randomString);

            adminView.getAdminViewCardsScrollPane().repaint();
            adminView.getAdminViewCardsScrollPane().revalidate();

            appContext.getBean(IncomingInvoiceInvoicesListController.class).start();

        }
    }

    @Component
    private static class UpdateIncomingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            AdminView adminView = appContext.getBean(AdminView.class);
            IncomingInvoiceUpdateView incomingInvoiceUpdateView = appContext.getBean(IncomingInvoiceUpdateView.class);

            //trying to speed up UI with removing all elements first
            adminView.getAdminViewCardsPanel().removeAll();
            String randomString = Utils.generateRandomString(8);
            adminView.getAdminViewCardsPanel().add(incomingInvoiceUpdateView, randomString);
            ((CardLayout) adminView.getAdminViewCardsPanel().getLayout()).show(adminView.getAdminViewCardsPanel(), randomString);

            adminView.getAdminViewCardsScrollPane().repaint();
            adminView.getAdminViewCardsScrollPane().revalidate();

            appContext.getBean(IncomingInvoiceUpdateController.class).start();

        }
    }

    @Component
    private static class NewIncomingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            AdminView adminView = appContext.getBean(AdminView.class);
            NewIncomingInvoicePanelView incomingInvoicePanelView = appContext.getBean(NewIncomingInvoicePanelView.class);

            adminView.getAdminViewCardsPanel().removeAll();
            String randomString = Utils.generateRandomString(8);
            adminView.getAdminViewCardsPanel().add(incomingInvoicePanelView, randomString);
            ((CardLayout) adminView.getAdminViewCardsPanel().getLayout()).show(adminView.getAdminViewCardsPanel(), randomString);

            adminView.getAdminViewCardsScrollPane().repaint();
            adminView.getAdminViewCardsScrollPane().revalidate();

            appContext.getBean(NewIncomingInvoiceDialogController.class).start();

        }
    }

    @Component
    private static class NewOutgoingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            //appContext.getBean(NewOutgoingInvoiceDialogController.class).start();
            AdminView adminView = appContext.getBean(AdminView.class);
            NewOutgoingInvoicePanelView outgoingInvoicePanelView = appContext.getBean(NewOutgoingInvoicePanelView.class);

            adminView.getAdminViewCardsPanel1().removeAll();

            String randomString = Utils.generateRandomString(8);
            adminView.getAdminViewCardsPanel1().add(outgoingInvoicePanelView, randomString);
            ((CardLayout) adminView.getAdminViewCardsPanel1().getLayout()).show(adminView.getAdminViewCardsPanel1(), randomString);

            adminView.getAdminViewCardsScrollPane1().repaint();
            adminView.getAdminViewCardsScrollPane1().revalidate();

            appContext.getBean(NewOutgoingInvoiceDialogController.class).start();

        }

    }

    @Component
    private static class UpdateOutgoingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            AdminView adminView = appContext.getBean(AdminView.class);
            OutgoingInvoiceUpdateView outgoingInvoiceUpdateView = appContext.getBean(OutgoingInvoiceUpdateView.class);

            //trying to speed up UI with removing all elements first
            adminView.getAdminViewCardsPanel1().removeAll();
            String randomString = Utils.generateRandomString(8);
            adminView.getAdminViewCardsPanel1().add(outgoingInvoiceUpdateView, randomString);
            ((CardLayout) adminView.getAdminViewCardsPanel1().getLayout()).show(adminView.getAdminViewCardsPanel1(), randomString);

            adminView.getAdminViewCardsScrollPane1().repaint();
            adminView.getAdminViewCardsScrollPane1().revalidate();

            appContext.getBean(OutgoingInvoiceUpdateController.class).start();

        }
    }

    @Component
    private static class NewLegalEntityButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(NewLegalEntityDialogController.class).start();

        }

    }

    @Component
    private static class UpdateLegalEntityButtonButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(LegalEntityUpdateController.class).start();

        }

    }

    @Component
    private static class ReviewLegalEntitiesButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(LegalEntityListController.class).start();

        }

    }

    @Component
    private static class ReviewOutgoingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            AdminView adminView = appContext.getBean(AdminView.class);
            OutgoingInvoiceInvoicesListView incomingInvoiceListView = appContext.getBean(OutgoingInvoiceInvoicesListView.class);

            //@TODO remove commented and the rest
            //trying to speed up UI with removing all elements first
            adminView.getAdminViewCardsPanel1().removeAll();
            String randomString = Utils.generateRandomString(8);
            adminView.getAdminViewCardsPanel1().add(incomingInvoiceListView, randomString);
            ((CardLayout) adminView.getAdminViewCardsPanel1().getLayout()).show(adminView.getAdminViewCardsPanel1(), randomString);

            adminView.getAdminViewCardsScrollPane1().repaint();
            adminView.getAdminViewCardsScrollPane1().revalidate();

            appContext.getBean(OutgoingInvoiceInvoicesListController.class).start();

        }

    }
}

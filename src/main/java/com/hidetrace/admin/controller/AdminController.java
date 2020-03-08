/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller;

import com.hidetrace.admin.controller.category.CategoryNewController;
import com.hidetrace.admin.controller.category.CategoryReviewController;
import com.hidetrace.admin.controller.category.CategoryUpdateController;
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
    AdminView view;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    OperatorModel operModel;

    public AdminController(AdminView view) {
        this.view = view;
    }

    private void initView() {
        view.setResizable(false);
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
            appContext.getBean(IncomingInvoiceInvoicesListController.class).start();
        }
    }

    @Component
    private static class UpdateIncomingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(IncomingInvoiceUpdateController.class).start();
        }
    }

    @Component
    private static class NewIncomingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(NewIncomingInvoiceDialogController.class).start();
        }
    }

    @Component
    private static class NewOutgoingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(NewOutgoingInvoiceDialogController.class).start();

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
    private static class UpdateOutgoingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(OutgoingInvoiceUpdateController.class).start();

        }

    }

    @Component
    private static class ReviewOutgoingInvoiceButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            appContext.getBean(OutgoingInvoiceInvoicesListController.class).start();

        }

    }
}

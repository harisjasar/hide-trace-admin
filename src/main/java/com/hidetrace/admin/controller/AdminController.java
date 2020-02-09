/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller;

import com.hidetrace.admin.controller.incominginvoice.NewIncomingInvoiceDialogController;
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

    public AdminController(AdminView view) {
        this.view = view;
    }

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private void initListeners() {
        if (view.getNewIncomingInvoiceButton().getActionListeners().length == 0) {
            view.getNewIncomingInvoiceButton().addActionListener(appContext.getBean(NewIncomingInvoiceButtonListener.class));
        }
    }

    public void start() {
        initListeners();
        initView();
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
}

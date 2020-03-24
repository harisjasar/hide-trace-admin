/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.login;

import com.google.common.hash.Hashing;
import com.hidetrace.admin.common.MessageDialog;
import com.hidetrace.admin.controller.AdminController;
import com.hidetrace.admin.model.OperatorModel;
import com.hidetrace.admin.service.login.LoginService;
import com.hidetrace.admin.view.login.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class LoginController {

    @Autowired
    private LoginView view;

    @Autowired
    private LoginService service;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    MessageDialog messageDialog;

    @Autowired
    OperatorModel model;

    private void initView() {

        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.toFront();
        view.setAlwaysOnTop(true);
        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getLoginButton().getActionListeners().length == 0) {
            view.getLoginButton().addActionListener(appContext.getBean(LoginButtonButtonListener.class));

        }

        if (view.getWindowListeners().length == 0) {
            view.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    //@TODO figure out the to close the application
                    // System.exit(0);
                }
            });
        }
    }

    private void initData() {

    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    public boolean grantAccess() {
        String username = view.getUserNameTextField().getText();
        String password = view.getPasswordTextField().getText();
        String hashedPassword = calculateHashedPassword(password);
        OperatorModel model_ = service.findRoleByUsernameAndPassword(username, hashedPassword);
        if (model_ != null && model_.getUserRole().equals("admin")) {
            model.setFirstName(model_.getFirstName());
            model.setLastName(model_.getLastName());
            model.setOperatorId(model_.getOperatorId());
            model.setUserRole(model_.getUserRole());
            model.setUsername(model_.getUsername());
            view.dispose();
            return true;
        } else {
            messageDialog.WrongLoginCredentials();
            return false;
        }

    }

    private String calculateHashedPassword(String string) {
        return Hashing.sha512()
                .hashString(string, StandardCharsets.UTF_8)
                .toString();
    }

    @Component
    private static class LoginButtonButtonListener implements ActionListener {

        @Autowired
        private ApplicationContext appContext;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (appContext.getBean(LoginController.class).grantAccess()) {
                appContext.getBean(AdminController.class).start();
            }
        }

    }
}

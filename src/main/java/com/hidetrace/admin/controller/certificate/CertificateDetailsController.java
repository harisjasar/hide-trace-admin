/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.certificate;

import com.hidetrace.admin.model.certificate.CertificateModel;
import com.hidetrace.admin.view.certificate.CertificateDetailsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class CertificateDetailsController {

    @Autowired
    private CertificateDetailsView view;

    @Autowired
    private CertificateModel certModel;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    private void initListeners() {

    }

    private void initData() {
        if (certModel != null) {
            view.getNameTextField().setText(certModel.getName());
            view.getDescriptionTextField().setText(certModel.getDescription());
        }
    }

    public void start() {
        initListeners();
        initData();
        initView();

    }

    void passCategory(CertificateModel certModel) {
        this.certModel = certModel;
    }
}

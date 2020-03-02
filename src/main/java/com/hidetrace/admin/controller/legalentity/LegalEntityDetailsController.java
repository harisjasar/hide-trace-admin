/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.legalentity;

import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.view.legalentity.LegalEntityDetailsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class LegalEntityDetailsController {

    @Autowired
    private LegalEntityDetailsView view;

    @Autowired
    LegalEntityService legalEntityService;

    private int legalEntityId;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.setVisible(true);

    }

    private void initListeners() {

    }

    private void initData() {
        populateLegalEntityData();

    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    public void passLegalEntityId(int selectedItem) {
        this.legalEntityId = selectedItem;
    }

    private void populateLegalEntityData() {
        LegalEntityModel model = legalEntityService.findByLegalEntityId(legalEntityId);
        if (model != null) {
            view.getNameTextField().setText(model.getName());
            view.getAddressTextField().setText(model.getAddress());
            view.getPhoneNumberTextfield().setText(model.getPhoneNumber());
        }
    }

}

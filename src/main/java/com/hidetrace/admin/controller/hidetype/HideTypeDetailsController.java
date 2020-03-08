/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.hidetype;

import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.view.hidetype.HideTypeDetailsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class HideTypeDetailsController {

    @Autowired
    private HideTypeModel hideTypeModel;

    @Autowired
    private HideTypeDetailsView view;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    private void initListeners() {

    }

    private void initData() {
        if (hideTypeModel != null) {
            view.getNameTextField().setText(hideTypeModel.getName());
            view.getDescriptionTextField().setText(hideTypeModel.getDescription());
        }
    }

    public void start() {
        initListeners();
        initData();
        initView();

    }

    void passInvoice(HideTypeModel selectedItem) {
        this.hideTypeModel = selectedItem;
    }

}

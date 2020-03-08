/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.category;

import com.hidetrace.admin.model.CategoryModel;
import com.hidetrace.admin.view.category.CategoryDetailsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class CategoryDetailsController {

    @Autowired
    private CategoryDetailsView view;

    @Autowired
    private CategoryModel catModel;

    private void initView() {
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    private void initListeners() {

    }

    private void initData() {
        if (catModel != null) {
            view.getNameTextField().setText(catModel.getName());
            view.getDescriptionTextField().setText(catModel.getDescription());
        }
    }

    public void start() {
        initListeners();
        initData();
        initView();

    }

    void passCategory(CategoryModel catModel) {
        this.catModel = catModel;
    }
}

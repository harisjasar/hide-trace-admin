/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.hidetype;

import com.hidetrace.admin.model.HideTypeModel;
import com.hidetrace.admin.service.HideTypeService;
import com.hidetrace.admin.view.hidetype.HideTypeReviewView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class HideTypeReviewController {

    @Autowired
    private HideTypeReviewView view;

    @Autowired
    private HideTypeService hidteTypeService;

    @Autowired
    private ApplicationContext appContext;

    private void initView() {

        view.setResizable(false);
        view.setLocationRelativeTo(null);

        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getTableOfContent().getMouseListeners().length == 2) {
            view.getTableOfContent().addMouseListener(appContext.getBean(HideTypeTableMouseSelectionAdapter.class));
        }

    }

    private void initData() {
        List<HideTypeModel> categoryModel = hidteTypeService.getAllHideTypes();
        DefaultTableModel invoicesTable = (DefaultTableModel) view.getTableOfContent().getModel();
        invoicesTable.setRowCount(0);
        Object rowData[] = new Object[1];
        for (int i = 0; i < categoryModel.size(); i++) {
            rowData[0] = categoryModel.get(i);
            invoicesTable.addRow(rowData);
        }
    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    @Component
    private static class HideTypeTableMouseSelectionAdapter extends MouseAdapter {

        @Autowired
        private HideTypeReviewController controller;

        @Autowired
        private HideTypeDetailsController detailsController;

        @Override
        public void mouseClicked(MouseEvent e) {
            JTable table = controller.getHideTypeTable();
            int row = table.getSelectedRow();
            HideTypeModel selectedItem = (HideTypeModel) table.getValueAt(row, 0);
            detailsController.passInvoice(selectedItem);
            detailsController.start();

        }
    }

    private JTable getHideTypeTable() {
        return view.getTableOfContent();
    }
}

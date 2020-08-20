/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.legalentity;

import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.view.legalentity.LegalEntityListView;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
public class LegalEntityListController {

    @Autowired
    LegalEntityListView view;

    @Autowired
    LegalEntityService legalEntityService;

    @Autowired
    ApplicationContext appContext;

    private void initView() {
        view.setVisible(true);
    }

    private void initListeners() {
        if (view.getLegalEntityTableList().getMouseListeners().length == 2) {
            view.getLegalEntityTableList().addMouseListener(appContext.getBean(EntityTableMouseSelectionAdapter.class));
        }

    }

    private void initData() {
        populateLegalEntityData();
    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    @Component
    private static class EntityTableMouseSelectionAdapter extends MouseAdapter {

        @Autowired
        private LegalEntityListController controller;

        @Autowired
        private LegalEntityDetailsController detailsController;

        @Override
        public void mouseClicked(MouseEvent e) {
            JTable table = controller.getInvoicesTable();
            int row = table.getSelectedRow();
            int selectedItem = (int) table.getValueAt(row, 0);
            detailsController.passLegalEntityId(selectedItem);
            detailsController.start();

        }
    }

    private void populateLegalEntityData() {
        List<LegalEntityModel> entities = legalEntityService.getAllLegalEntities();
        DefaultTableModel entitiesTable = (DefaultTableModel) view.getLegalEntityTableList().getModel();
        entitiesTable.setRowCount(0);
        Object rowData[] = new Object[2];
        for (int i = 0; i < entities.size(); i++) {
            rowData[0] = entities.get(i).getLegalEntityId();
            rowData[1] = entities.get(i).getName();
            entitiesTable.addRow(rowData);
        }

    }

    private JTable getInvoicesTable() {
        return view.getLegalEntityTableList();
    }
}

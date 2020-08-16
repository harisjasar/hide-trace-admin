/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.incominginvoice.IncomingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.incominginvoice.IncomingLegalEntityInvoiceService;
import com.hidetrace.admin.view.incominginvoice.IncomingInvoiceInvoicesListView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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
public class IncomingInvoiceInvoicesListController {

    @Autowired
    private IncomingInvoiceInvoicesListView view;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private IncomingLegalEntityInvoiceService incomingLegalEntityInvoiceService;

    private void initView() {
        // view.setResizable(false); //@TODO
        // view.setLocationRelativeTo(null); //@TODO
        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getLegalEntityDropDown().getActionListeners().length == 0) {
            view.getLegalEntityDropDown().addActionListener(appContext.getBean(LegalEntityDropDownActionListenerInvoicesListView.class));
        }
        if (view.getInvoicesTable().getMouseListeners().length == 2) {
            view.getInvoicesTable().addMouseListener(appContext.getBean(InvoicesTableMouseSelectionAdapter.class));
        }

    }

    private void initData() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) view.getLegalEntityDropDown().getModel();
        model.removeAllElements();

        ArrayList<LegalEntityModel> entities = (ArrayList<LegalEntityModel>) legalEntityService.getAllLegalEntities();
        entities.forEach((entity) -> {
            model.addElement(entity);
        });

    }

    public void start() {
        initListeners();
        initData();
        initView();
    }

    @Component
    private static class LegalEntityDropDownActionListenerInvoicesListView implements ActionListener {

        @Autowired
        private IncomingInvoiceInvoicesListController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.populateInvoicesTable();

        }

    }

    @Component
    private static class InvoicesTableMouseSelectionAdapter extends MouseAdapter {

        @Autowired
        private IncomingInvoiceInvoicesListController controller;

        @Autowired
        private IncomingInvoiceDetailsController detailsController;

        @Override
        public void mouseClicked(MouseEvent e) {
            JTable table = controller.getInvoicesTable();
            int row = table.getSelectedRow();
            int selectedItem = (int) table.getValueAt(row, 0);
            detailsController.passInvoiceId(selectedItem);
            detailsController.start();

        }
    }

    private JTable getInvoicesTable() {
        return view.getInvoicesTable();
    }

    private void populateInvoicesTable() {
        LegalEntityModel legalEntityModel = (LegalEntityModel) view.getLegalEntityDropDown().getSelectedItem();
        if (legalEntityModel != null) {
            List<IncomingLegalEntityInvoiceModel> invoices = incomingLegalEntityInvoiceService.findByLegalEntityId(legalEntityModel.getLegalEntityId());

            DefaultTableModel invoicesTable = (DefaultTableModel) view.getInvoicesTable().getModel();
            invoicesTable.setRowCount(0);
            Object rowData[] = new Object[2];
            for (int i = 0; i < invoices.size(); i++) {
                rowData[0] = invoices.get(i).getInvId();
                rowData[1] = invoices.get(i).getInvName();
                invoicesTable.addRow(rowData);
            }
        }
    }
}

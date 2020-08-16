/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.outgoinginvoice;

import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.model.outgoinginvoice.OutgoingLegalEntityInvoiceModel;
import com.hidetrace.admin.service.LegalEntityService;
import com.hidetrace.admin.service.outgoinginvoice.OutgoingLegalEntityInvoiceService;
import com.hidetrace.admin.view.outgoinginvoice.OutgoingInvoiceInvoicesListView;
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
public class OutgoingInvoiceInvoicesListController {

    @Autowired
    private OutgoingInvoiceInvoicesListView view;

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private OutgoingLegalEntityInvoiceService outgoingLegalEntityInvoiceService;

    @Autowired
    private ApplicationContext appContext;

    private void initView() {
        view.setVisible(true);

    }

    private void initListeners() {
        if (view.getLegalEntityDropdown().getActionListeners().length == 0) {
            view.getLegalEntityDropdown().addActionListener(appContext.getBean(LegalEntityDropDownActionListenerInvoicesListView.class));
        }

        if (view.getInvoicesTable().getMouseListeners().length == 2) {
            view.getInvoicesTable().addMouseListener(appContext.getBean(InvoicesTableMouseSelectionAdapter.class));
        }
    }

    private void initData() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) view.getLegalEntityDropdown().getModel();
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

    private JTable getInvoicesTable() {
        return view.getInvoicesTable();
    }

    @Component
    private static class LegalEntityDropDownActionListenerInvoicesListView implements ActionListener {

        @Autowired
        private OutgoingInvoiceInvoicesListController controller;

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.populateInvoicesTable();

        }

    }

    @Component
    private static class InvoicesTableMouseSelectionAdapter extends MouseAdapter {

        @Autowired
        private OutgoingInvoiceInvoicesListController controller;

        @Autowired
        private OutgoingInvoiceDetailsController detailsController;

        @Override
        public void mouseClicked(MouseEvent e) {
            JTable table = controller.getInvoicesTable();
            int row = table.getSelectedRow();
            OutgoingLegalEntityInvoiceModel selectedItem = (OutgoingLegalEntityInvoiceModel) table.getValueAt(row, 0);
            detailsController.passInvoice(selectedItem);
            detailsController.start();

        }
    }

    private void populateInvoicesTable() {
        LegalEntityModel legalEntityModel = (LegalEntityModel) view.getLegalEntityDropdown().getSelectedItem();
        if (legalEntityModel != null) {
            List<OutgoingLegalEntityInvoiceModel> invoices = outgoingLegalEntityInvoiceService.findByLegalEntityId(legalEntityModel.getLegalEntityId());

            DefaultTableModel invoicesTable = (DefaultTableModel) view.getInvoicesTable().getModel();
            invoicesTable.setRowCount(0);
            Object rowData[] = new Object[1];
            for (int i = 0; i < invoices.size(); i++) {
                rowData[0] = invoices.get(i);
                invoicesTable.addRow(rowData);
            }
        }
    }
}

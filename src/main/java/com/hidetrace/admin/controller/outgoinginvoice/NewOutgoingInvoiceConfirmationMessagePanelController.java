/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.outgoinginvoice;

import com.hidetrace.admin.view.outgoinginvoice.NewOutgoingInvoiceConfirmationMessagePanelView;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class NewOutgoingInvoiceConfirmationMessagePanelController {

    @Autowired
    NewOutgoingInvoiceConfirmationMessagePanelView panel;

    public HashMap getData() {

        HashMap<String, JLabel> data = new HashMap<>();
        data.put("legalEntityLabel", panel.getLegalEntityLabel());
        data.put("legalEntity", panel.getLegalEntity());
        data.put("invoiceIdLabel", panel.getInvoiceIdLabel());
        data.put("invoiceId", panel.getInvoiceId());

        return data;
    }

    public JPanel getConfirmationPanel() {
        return panel;
    }
}

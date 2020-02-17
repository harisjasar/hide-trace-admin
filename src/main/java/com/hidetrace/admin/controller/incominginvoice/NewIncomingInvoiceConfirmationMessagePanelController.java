/*
 *
 * Contributors:
 *    Jashar
 */
package com.hidetrace.admin.controller.incominginvoice;

import com.hidetrace.admin.view.incominginvoice.NewIncomingInvoiceConfirmationMessagePanelView;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
public class NewIncomingInvoiceConfirmationMessagePanelController {

    @Autowired
    NewIncomingInvoiceConfirmationMessagePanelView panel;

    public HashMap getData() {

        HashMap<String, JLabel> data = new HashMap<>();
        data.put("legalEntityLabel", panel.getLegalEntityLabel());
        data.put("legalEntity", panel.getLegalEntity());
        data.put("invoiceIdLabel", panel.getInvoiceIdLabel());
        data.put("invoiceId", panel.getInvoiceId());
        data.put("grossWeightLabel", panel.getGrossWeightLabel());
        data.put("grossWeight", panel.getGrossWeight());
        data.put("netWeightLabel", panel.getNetWeightLabel());
        data.put("netWeight", panel.getNetWeight());
        data.put("abroadReducedLabel", panel.getAbroadReducedLabel());
        data.put("abroadReduced", panel.getAbroadReduced());
        data.put("saltLabel", panel.getSaltLabel());
        data.put("salt", panel.getSalt());
        data.put("certificateLabel", panel.getCertificateLabel());
        data.put("certificate", panel.getCertificate());
        data.put("cowLabel", panel.getCowLabel());
        data.put("cow", panel.getCow());
        data.put("bullLabel", panel.getBullLabel());
        data.put("bull", panel.getBull());
        data.put("calfLabel", panel.getCalfLabel());
        data.put("calf", panel.getCalf());
        data.put("commentLabel", panel.getCommentLabel());
        data.put("comment", panel.getComment());

        return data;
    }

    public JPanel getConfirmationPanel() {
        return panel;
    }
}

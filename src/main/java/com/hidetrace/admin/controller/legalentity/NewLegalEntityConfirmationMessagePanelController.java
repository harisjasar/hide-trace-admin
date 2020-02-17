/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.controller.legalentity;

import com.hidetrace.admin.view.legalentity.NewLegalEntityConfirmationMessagePanelView;
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
public class NewLegalEntityConfirmationMessagePanelController {

    @Autowired
    NewLegalEntityConfirmationMessagePanelView panel;

    public HashMap getData() {

        HashMap<String, JLabel> data = new HashMap<>();
        data.put("legalEntityNameLabel", panel.getLegalEntityNameLabel());
        data.put("legalEntityName", panel.getLegalEntityName());
        data.put("legalEntityAddressLabel", panel.getLegalEntityAddressLabel());
        data.put("legalEntityAddress", panel.getLegalEntityAddress());
        data.put("legalEntityPhoneNumberLabel", panel.getLegalEntityPhoneNumberLabel());
        data.put("legalEntityPhoneNumber", panel.getLegalEntityPhoneNumber());

        return data;
    }

    public JPanel getConfirmationPanel() {
        return panel;
    }

}

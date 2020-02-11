/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.common;

import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class MessageDialog {

    /**
     * ERROR_MESSAGE = 0; INFORMATION_MESSAGE = 1; WARNING_MESSAGE = 2;
     * QUESTION_MESSAGE = 3; PLAIN_MESSAGE = -1;
     *
     * @param parentComponent
     * @param message
     * @param title
     * @param messageType
     */
    public void showMessageDialog(Component parentComponent,
            Object message, String title, int messageType) {

        JOptionPane.showMessageDialog((java.awt.Component) parentComponent, message, title, messageType);

    }

}

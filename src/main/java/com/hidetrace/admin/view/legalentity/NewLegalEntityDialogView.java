/*
 * Copyright (c) 2020 Jashar.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jashar - initial API and implementation and/or initial documentation
 */
package com.hidetrace.admin.view.legalentity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
public class NewLegalEntityDialogView extends javax.swing.JDialog {

    /**
     * Creates new form NewLegalEntityDialogView
     *
     * @param parent
     * @param modal
     */
    public NewLegalEntityDialogView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel6 = new javax.swing.JPanel();
        LegalEntityNameLabel = new javax.swing.JLabel();
        LegalEntityNameTxtField = new javax.swing.JTextField();
        LegalEntityAddressLabel = new javax.swing.JLabel();
        LegalEntityAddressTxtField = new javax.swing.JTextField();
        LegalEntityPhoneNumberLabel = new javax.swing.JLabel();
        LegalEntityPhoneNumberTxtField = new javax.swing.JTextField();
        AddLegalEntityButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        LegalEntityNameLabel.setText("Naziv Firme");
        LegalEntityNameLabel.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel6.add(LegalEntityNameLabel, gridBagConstraints);

        LegalEntityNameTxtField.setNextFocusableComponent(LegalEntityAddressTxtField);
        LegalEntityNameTxtField.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel6.add(LegalEntityNameTxtField, gridBagConstraints);

        LegalEntityAddressLabel.setText("Adresa Firme");
        LegalEntityAddressLabel.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel6.add(LegalEntityAddressLabel, gridBagConstraints);

        LegalEntityAddressTxtField.setNextFocusableComponent(LegalEntityPhoneNumberTxtField);
        LegalEntityAddressTxtField.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel6.add(LegalEntityAddressTxtField, gridBagConstraints);

        LegalEntityPhoneNumberLabel.setText("Broj Telefona");
        LegalEntityPhoneNumberLabel.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel6.add(LegalEntityPhoneNumberLabel, gridBagConstraints);

        LegalEntityPhoneNumberTxtField.setPreferredSize(new java.awt.Dimension(150, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        jPanel6.add(LegalEntityPhoneNumberTxtField, gridBagConstraints);

        AddLegalEntityButton.setText("Dodaj");
        AddLegalEntityButton.setPreferredSize(new java.awt.Dimension(60, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(30, 0, 0, 0);
        jPanel6.add(AddLegalEntityButton, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddLegalEntityButton;
    private javax.swing.JLabel LegalEntityAddressLabel;
    private javax.swing.JTextField LegalEntityAddressTxtField;
    private javax.swing.JLabel LegalEntityNameLabel;
    private javax.swing.JTextField LegalEntityNameTxtField;
    private javax.swing.JLabel LegalEntityPhoneNumberLabel;
    private javax.swing.JTextField LegalEntityPhoneNumberTxtField;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}

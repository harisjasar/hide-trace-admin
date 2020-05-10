/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.view;

import java.awt.CardLayout;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
@Primary
public class AdminView extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame5
     */
    CardLayout cardLayout;

    public AdminView() {
        initComponents();
        cardLayout = (CardLayout) pnlCards.getLayout();
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

        jSplitPane1 = new javax.swing.JSplitPane();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        currentLoggedOnUserLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        legalEntitySideButton = new keeptoo.KButton();
        incomingLegalEntitySideButton = new keeptoo.KButton();
        adminProfileSideButton = new keeptoo.KButton();
        outgoingLegalEntitySideButton = new keeptoo.KButton();
        pnlCards = new javax.swing.JPanel();
        pnlCard1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        newAddLegalEntityButton = new keeptoo.KButton();
        updateLegalEntityButton = new keeptoo.KButton();
        reviewLegalEntitiesButton = new keeptoo.KButton();
        pnlCard2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        newIncomingInvoiceButton = new keeptoo.KButton();
        updateIncomingInvoiceButton = new keeptoo.KButton();
        reviewIncomingInvoicesButton = new keeptoo.KButton();
        pnlCard3 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        newOutgoingInvoiceButton = new keeptoo.KButton();
        updateOutgoingInvoiceButton = new keeptoo.KButton();
        reviewOutgoingInvoiceButton = new keeptoo.KButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu = new javax.swing.JMenu();
        newHideTypeButton = new javax.swing.JMenuItem();
        updateHideTypeButton = new javax.swing.JMenuItem();
        reviewHideTypeButton = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        newCategoryButton = new javax.swing.JMenuItem();
        updateCategory = new javax.swing.JMenuItem();
        reviewCategoryButton = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        newCertificateButton = new javax.swing.JMenuItem();
        updateCertificateButton = new javax.swing.JMenuItem();
        reviewCertificateButton = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        incomingCertificatePreviewButton = new javax.swing.JMenuItem();
        outgoingCertificatePreviewButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jSplitPane1.setPreferredSize(new java.awt.Dimension(1024, 768));

        kGradientPanel2.setkBorderRadius(0);
        kGradientPanel2.setkEndColor(new java.awt.Color(48, 63, 71));
        kGradientPanel2.setkStartColor(new java.awt.Color(48, 63, 71));
        kGradientPanel2.setPreferredSize(new java.awt.Dimension(200, 600));
        kGradientPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel6.setBackground(new java.awt.Color(255, 204, 0));
        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(64, 64));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/graphics/leatherLogotip.png"))); // NOI18N
        jLabel5.setMaximumSize(new java.awt.Dimension(200, 29));
        jPanel6.add(jLabel5);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel6, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("HideTrace");
        jLabel4.setMaximumSize(new java.awt.Dimension(200, 29));
        jLabel4.setPreferredSize(new java.awt.Dimension(200, 29));
        jPanel3.add(jLabel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setOpaque(false);

        currentLoggedOnUserLabel.setBackground(new java.awt.Color(255, 255, 255));
        currentLoggedOnUserLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        currentLoggedOnUserLabel.setForeground(new java.awt.Color(255, 255, 255));
        currentLoggedOnUserLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currentLoggedOnUserLabel.setText("Dobrodošli, Haris");
        currentLoggedOnUserLabel.setMaximumSize(new java.awt.Dimension(200, 29));
        currentLoggedOnUserLabel.setPreferredSize(new java.awt.Dimension(200, 29));
        jPanel5.add(currentLoggedOnUserLabel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jPanel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        kGradientPanel2.add(jPanel1, gridBagConstraints);

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        legalEntitySideButton.setText("Pravno lice");
        legalEntitySideButton.setkBorderRadius(0);
        legalEntitySideButton.setkEndColor(new java.awt.Color(82, 81, 70));
        legalEntitySideButton.setkHoverEndColor(new java.awt.Color(189, 188, 177));
        legalEntitySideButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        legalEntitySideButton.setkHoverStartColor(new java.awt.Color(189, 188, 177));
        legalEntitySideButton.setkSelectedColor(new java.awt.Color(165, 174, 173));
        legalEntitySideButton.setkStartColor(new java.awt.Color(82, 81, 70));
        legalEntitySideButton.setPreferredSize(new java.awt.Dimension(200, 40));
        legalEntitySideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                legalEntitySideButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel4.add(legalEntitySideButton, gridBagConstraints);

        incomingLegalEntitySideButton.setText("Ulaz");
        incomingLegalEntitySideButton.setkBorderRadius(0);
        incomingLegalEntitySideButton.setkEndColor(new java.awt.Color(82, 81, 70));
        incomingLegalEntitySideButton.setkHoverEndColor(new java.awt.Color(189, 188, 177));
        incomingLegalEntitySideButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        incomingLegalEntitySideButton.setkHoverStartColor(new java.awt.Color(189, 188, 177));
        incomingLegalEntitySideButton.setkSelectedColor(new java.awt.Color(165, 174, 173));
        incomingLegalEntitySideButton.setkStartColor(new java.awt.Color(82, 81, 70));
        incomingLegalEntitySideButton.setPreferredSize(new java.awt.Dimension(200, 40));
        incomingLegalEntitySideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incomingLegalEntitySideButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jPanel4.add(incomingLegalEntitySideButton, gridBagConstraints);

        adminProfileSideButton.setText("Profil");
        adminProfileSideButton.setkBorderRadius(0);
        adminProfileSideButton.setkEndColor(new java.awt.Color(82, 81, 70));
        adminProfileSideButton.setkHoverEndColor(new java.awt.Color(189, 188, 177));
        adminProfileSideButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        adminProfileSideButton.setkHoverStartColor(new java.awt.Color(189, 188, 177));
        adminProfileSideButton.setkSelectedColor(new java.awt.Color(165, 174, 173));
        adminProfileSideButton.setkStartColor(new java.awt.Color(82, 81, 70));
        adminProfileSideButton.setPreferredSize(new java.awt.Dimension(20, 40));
        adminProfileSideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminProfileSideButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(adminProfileSideButton, gridBagConstraints);

        outgoingLegalEntitySideButton.setText("Izlaz");
        outgoingLegalEntitySideButton.setkBorderRadius(0);
        outgoingLegalEntitySideButton.setkEndColor(new java.awt.Color(82, 81, 70));
        outgoingLegalEntitySideButton.setkHoverEndColor(new java.awt.Color(189, 188, 177));
        outgoingLegalEntitySideButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        outgoingLegalEntitySideButton.setkHoverStartColor(new java.awt.Color(189, 188, 177));
        outgoingLegalEntitySideButton.setkSelectedColor(new java.awt.Color(165, 174, 173));
        outgoingLegalEntitySideButton.setkStartColor(new java.awt.Color(82, 81, 70));
        outgoingLegalEntitySideButton.setPreferredSize(new java.awt.Dimension(20, 40));
        outgoingLegalEntitySideButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                outgoingLegalEntitySideButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 100, 0);
        jPanel4.add(outgoingLegalEntitySideButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 2.0;
        kGradientPanel2.add(jPanel4, gridBagConstraints);

        jSplitPane1.setLeftComponent(kGradientPanel2);

        pnlCards.setPreferredSize(new java.awt.Dimension(500, 600));
        pnlCards.setLayout(new java.awt.CardLayout());

        pnlCard1.setBackground(new java.awt.Color(165, 174, 173));
        pnlCard1.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Pravno lice");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlCard1.add(jPanel2, gridBagConstraints);

        jPanel7.setBackground(new java.awt.Color(165, 174, 173));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        newAddLegalEntityButton.setText("Novo pravno lice");
        newAddLegalEntityButton.setkEndColor(new java.awt.Color(48, 63, 71));
        newAddLegalEntityButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        newAddLegalEntityButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        newAddLegalEntityButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        newAddLegalEntityButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel7.add(newAddLegalEntityButton, gridBagConstraints);

        updateLegalEntityButton.setText("Ažuriranje");
        updateLegalEntityButton.setkEndColor(new java.awt.Color(48, 63, 71));
        updateLegalEntityButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        updateLegalEntityButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        updateLegalEntityButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        updateLegalEntityButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 5.0;
        jPanel7.add(updateLegalEntityButton, gridBagConstraints);

        reviewLegalEntitiesButton.setText("Pregled");
        reviewLegalEntitiesButton.setkEndColor(new java.awt.Color(48, 63, 71));
        reviewLegalEntitiesButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        reviewLegalEntitiesButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        reviewLegalEntitiesButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        reviewLegalEntitiesButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel7.add(reviewLegalEntitiesButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 100.0;
        pnlCard1.add(jPanel7, gridBagConstraints);

        pnlCards.add(pnlCard1, "card1");

        pnlCard2.setBackground(new java.awt.Color(165, 174, 173));
        pnlCard2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Ulaz");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlCard2.add(jPanel8, gridBagConstraints);

        jPanel9.setBackground(new java.awt.Color(165, 174, 173));
        jPanel9.setLayout(new java.awt.GridBagLayout());

        newIncomingInvoiceButton.setText("Nova faktura");
        newIncomingInvoiceButton.setkEndColor(new java.awt.Color(48, 63, 71));
        newIncomingInvoiceButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        newIncomingInvoiceButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        newIncomingInvoiceButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        newIncomingInvoiceButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel9.add(newIncomingInvoiceButton, gridBagConstraints);

        updateIncomingInvoiceButton.setText("Ažuriranje");
        updateIncomingInvoiceButton.setkEndColor(new java.awt.Color(48, 63, 71));
        updateIncomingInvoiceButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        updateIncomingInvoiceButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        updateIncomingInvoiceButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        updateIncomingInvoiceButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 5.0;
        jPanel9.add(updateIncomingInvoiceButton, gridBagConstraints);

        reviewIncomingInvoicesButton.setText("Pregled");
        reviewIncomingInvoicesButton.setkEndColor(new java.awt.Color(48, 63, 71));
        reviewIncomingInvoicesButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        reviewIncomingInvoicesButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        reviewIncomingInvoicesButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        reviewIncomingInvoicesButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel9.add(reviewIncomingInvoicesButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 100.0;
        pnlCard2.add(jPanel9, gridBagConstraints);

        pnlCards.add(pnlCard2, "card2");

        pnlCard3.setBackground(new java.awt.Color(165, 174, 173));
        pnlCard3.setLayout(new java.awt.GridBagLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Izlaz");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        pnlCard3.add(jPanel10, gridBagConstraints);

        jPanel11.setBackground(new java.awt.Color(165, 174, 173));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        newOutgoingInvoiceButton.setText("Nova faktura");
        newOutgoingInvoiceButton.setkEndColor(new java.awt.Color(48, 63, 71));
        newOutgoingInvoiceButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        newOutgoingInvoiceButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        newOutgoingInvoiceButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        newOutgoingInvoiceButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel11.add(newOutgoingInvoiceButton, gridBagConstraints);

        updateOutgoingInvoiceButton.setText("Ažuriranje");
        updateOutgoingInvoiceButton.setkEndColor(new java.awt.Color(48, 63, 71));
        updateOutgoingInvoiceButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        updateOutgoingInvoiceButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        updateOutgoingInvoiceButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        updateOutgoingInvoiceButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 5.0;
        jPanel11.add(updateOutgoingInvoiceButton, gridBagConstraints);

        reviewOutgoingInvoiceButton.setText("Pregled");
        reviewOutgoingInvoiceButton.setkEndColor(new java.awt.Color(48, 63, 71));
        reviewOutgoingInvoiceButton.setkHoverEndColor(new java.awt.Color(161, 200, 200));
        reviewOutgoingInvoiceButton.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        reviewOutgoingInvoiceButton.setkHoverStartColor(new java.awt.Color(161, 200, 200));
        reviewOutgoingInvoiceButton.setkStartColor(new java.awt.Color(48, 63, 71));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        jPanel11.add(reviewOutgoingInvoiceButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 100.0;
        pnlCard3.add(jPanel11, gridBagConstraints);

        pnlCards.add(pnlCard3, "card3");

        jSplitPane1.setRightComponent(pnlCards);

        jMenu.setText("Vrsta");

        newHideTypeButton.setText("Nova vrsta");
        jMenu.add(newHideTypeButton);

        updateHideTypeButton.setText("Ažuriranje");
        jMenu.add(updateHideTypeButton);

        reviewHideTypeButton.setText("Pregled");
        jMenu.add(reviewHideTypeButton);

        jMenuBar1.add(jMenu);

        jMenu1.setText("Kategorija");

        newCategoryButton.setText("Nova kategorija");
        jMenu1.add(newCategoryButton);

        updateCategory.setText("Ažuriranje");
        jMenu1.add(updateCategory);

        reviewCategoryButton.setText("Pregled");
        jMenu1.add(reviewCategoryButton);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Certifikat");

        newCertificateButton.setText("Novi certifikat");
        jMenu2.add(newCertificateButton);

        updateCertificateButton.setText("Ažuriranje");
        jMenu2.add(updateCertificateButton);

        reviewCertificateButton.setText("Pregled");
        jMenu2.add(reviewCertificateButton);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Pregled");

        incomingCertificatePreviewButton.setText("Ulazni certifikati");
        jMenu4.add(incomingCertificatePreviewButton);

        outgoingCertificatePreviewButton.setText("Izlazni certifikati");
        jMenu4.add(outgoingCertificatePreviewButton);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void legalEntitySideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_legalEntitySideButtonActionPerformed
        cardLayout.show(pnlCards, "card1");

    }//GEN-LAST:event_legalEntitySideButtonActionPerformed

    private void incomingLegalEntitySideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incomingLegalEntitySideButtonActionPerformed
        cardLayout.show(pnlCards, "card2");

    }//GEN-LAST:event_incomingLegalEntitySideButtonActionPerformed

    private void adminProfileSideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminProfileSideButtonActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_adminProfileSideButtonActionPerformed

    private void outgoingLegalEntitySideButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outgoingLegalEntitySideButtonActionPerformed
        cardLayout.show(pnlCards, "card3");
        // TODO add your handling code here:
    }//GEN-LAST:event_outgoingLegalEntitySideButtonActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame51.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame51.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame51.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(NewJFrame51.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new NewJFrame51().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private keeptoo.KButton adminProfileSideButton;
    private javax.swing.JLabel currentLoggedOnUserLabel;
    private javax.swing.JMenuItem incomingCertificatePreviewButton;
    private keeptoo.KButton incomingLegalEntitySideButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSplitPane jSplitPane1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KButton legalEntitySideButton;
    private keeptoo.KButton newAddLegalEntityButton;
    private javax.swing.JMenuItem newCategoryButton;
    private javax.swing.JMenuItem newCertificateButton;
    private javax.swing.JMenuItem newHideTypeButton;
    private keeptoo.KButton newIncomingInvoiceButton;
    private keeptoo.KButton newOutgoingInvoiceButton;
    private javax.swing.JMenuItem outgoingCertificatePreviewButton;
    private keeptoo.KButton outgoingLegalEntitySideButton;
    private javax.swing.JPanel pnlCard1;
    private javax.swing.JPanel pnlCard2;
    private javax.swing.JPanel pnlCard3;
    private javax.swing.JPanel pnlCards;
    private javax.swing.JMenuItem reviewCategoryButton;
    private javax.swing.JMenuItem reviewCertificateButton;
    private javax.swing.JMenuItem reviewHideTypeButton;
    private keeptoo.KButton reviewIncomingInvoicesButton;
    private keeptoo.KButton reviewLegalEntitiesButton;
    private keeptoo.KButton reviewOutgoingInvoiceButton;
    private javax.swing.JMenuItem updateCategory;
    private javax.swing.JMenuItem updateCertificateButton;
    private javax.swing.JMenuItem updateHideTypeButton;
    private keeptoo.KButton updateIncomingInvoiceButton;
    private keeptoo.KButton updateLegalEntityButton;
    private keeptoo.KButton updateOutgoingInvoiceButton;
    // End of variables declaration//GEN-END:variables
}

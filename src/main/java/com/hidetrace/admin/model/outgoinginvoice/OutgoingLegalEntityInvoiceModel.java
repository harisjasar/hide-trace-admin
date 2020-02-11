/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.model.outgoinginvoice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Entity
@Table(name = "outgoinginvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OutgoingLegalEntityInvoiceModel {

    @Id
    @Column(name = "OutgoingInvoiceID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int outgoingInvoiceId;
    @Column(name = "Name")
    private String name;
    @Column(name = "LegalEntityID")
    private int legalEntityId;

}

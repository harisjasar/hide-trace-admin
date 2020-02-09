/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.model.incominginvoice;

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
@Table(name = "incominginvoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class IncomingLegalEntityInvoiceModel {

    @Id
    @Column(name = "IncomingInvoiceID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invId;
    @Column(name = "Name")
    private String invName;
    @Column(name = "GrossWeight")
    private double invGrossWeight;
    @Column(name = "NetWeight")
    private double invNetWeight;
    @Column(name = "Salt")
    private double invSalt;
    @Column(name = "Description")
    private String invDescription;
    @Column(name = "DateTime")
    private java.sql.Timestamp invTimeStamp;
    @Column(name = "AbroadReduced")
    private double invAbroadReduced;
    @Column(name = "TotalLoad")
    private double invTotalLoad;
    @Column(name = "Difference")
    private double invDifference;
    @Column(name = "LegalEntityID")
    private int invLegalEntityId;

}

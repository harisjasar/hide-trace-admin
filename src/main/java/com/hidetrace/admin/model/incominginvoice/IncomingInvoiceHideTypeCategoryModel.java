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
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "incominginvoice_has_hidetype_has_category")
public class IncomingInvoiceHideTypeCategoryModel {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "IncomingInvoiceID")
    private int incomingInvoiceId;
    @Column(name = "Price")
    private double price;
    @Column(name = "HideTypeID")
    private int hideTypeId;
    @Column(name = "CategoryID")
    private int categoryId;
}

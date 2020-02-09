/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Jashar
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "legalentity")
public class LegalEntityModel {

    @Id
    @Column(name = "LegalEntityID")
    private int LegalEntityID;
    @Column(name = "Name")

    private String name;
    @Column(name = "Address")
    private String address;
    @Column(name = "PhoneNumber")
    private String phoneNumber;
    @Column(name = "LegalEntityCode")
    private String legalEntityCode;

    @Override
    public String toString() {
        return name;
    }
}

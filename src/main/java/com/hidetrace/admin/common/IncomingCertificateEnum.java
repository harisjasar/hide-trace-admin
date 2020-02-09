/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.common;

/**
 *
 * @author Jashar
 */
public enum IncomingCertificateEnum {

    /**
     *
     */
    ZVUD("ZVUD"),
    VS_B2("VS-B2");

    // declaring private variable for getting values
    private String value;

    // getter method
    public String getValue() {
        return this.value;
    }

    // enum constructor - cannot be public or protected
    private IncomingCertificateEnum(String action) {
        this.value = action;
    }
}

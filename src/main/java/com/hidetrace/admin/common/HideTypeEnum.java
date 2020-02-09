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
public enum HideTypeEnum {

    COW("KRAVA"),
    BULL("BIK"),
    CALF("TELE");

    // declaring private variable for getting values
    private String value;

    // getter method
    public String getValue() {
        return this.value;
    }

    private HideTypeEnum(String action) {
        this.value = action;
    }
}

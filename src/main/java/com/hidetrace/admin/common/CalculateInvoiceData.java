/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.common;

import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class CalculateInvoiceData {

    public double differenceGrossNet(double salt, double weightDifference, double abroadReduced, double abroadWeight) {
        return (salt + ((weightDifference * 100) / abroadWeight)) - abroadReduced;
    }

    public Double calculateTotalLoad(Double salt, Double weightDifference, Double wrossWeight) {
        return salt + ((weightDifference * 100) / wrossWeight);
    }
}

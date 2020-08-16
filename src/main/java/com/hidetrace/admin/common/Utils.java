/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.common;

import java.nio.charset.Charset;
import java.util.Random;

/**
 *
 * @author Jashar
 */
public class Utils {

    public static String generateRandomString(int length) {
        byte[] array = new byte[length]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }
}

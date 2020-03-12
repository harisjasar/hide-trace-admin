/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.common;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveException {

    private boolean isRaised;
    private ArrayList<String> errorMessage;

    public void addErrorMessage(String msg) {
        errorMessage.add(msg);
    }

}

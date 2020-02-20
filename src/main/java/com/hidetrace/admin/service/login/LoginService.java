/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.login;

import com.hidetrace.admin.model.OperatorModel;
import com.hidetrace.admin.repository.login.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class LoginService {

    @Autowired
    private LoginRepository repo;

    public OperatorModel findRoleByUsernameAndPassword(String uname, String password) {
        return repo.findRoleByUsernameAndPassword(uname, password);
    }
}

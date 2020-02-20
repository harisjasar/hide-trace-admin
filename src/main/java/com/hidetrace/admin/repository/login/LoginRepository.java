/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.repository.login;

import com.hidetrace.admin.model.OperatorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jashar
 */
@Repository
public interface LoginRepository extends JpaRepository<OperatorModel, Integer> {

    OperatorModel findRoleByUsernameAndPassword(String uname, String password);
}

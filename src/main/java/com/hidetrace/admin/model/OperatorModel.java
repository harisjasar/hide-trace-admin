/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "operator")
@ToString
@Component
public class OperatorModel implements Serializable {

    @Id
    @Column(name = "OperatorID")
    private int operatorId;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Password")
    private String password;
    @Column(name = "Role")
    private String userRole;
    @Column(name = "Username")
    private String username;

}

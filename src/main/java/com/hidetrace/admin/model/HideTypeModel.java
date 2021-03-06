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
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hidetype")
public class HideTypeModel {

    @Id
    @Column(name = "HideTypeID")
    private int hideTypeId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Description")
    private String description;

    @Override
    public String toString() {
        return name;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.repository;

import com.hidetrace.admin.model.certificate.CertificateModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jashar
 */
public interface CertificateRepository extends JpaRepository<CertificateModel, Integer> {

}

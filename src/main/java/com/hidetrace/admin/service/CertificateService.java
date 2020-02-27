/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.CertificateModel;
import com.hidetrace.admin.repository.incominginvoice.CertificateRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jashar
 */
@Service
public class CertificateService {

    @Autowired
    private CertificateRepository repo;

    @Transactional
    public List<CertificateModel> findAll() {
        return repo.findAll();
    }
}

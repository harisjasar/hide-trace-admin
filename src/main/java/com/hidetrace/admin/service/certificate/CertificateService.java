/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service.certificate;

import com.hidetrace.admin.model.certificate.CertificateModel;
import com.hidetrace.admin.repository.CertificateRepository;
import java.util.List;
import java.util.Optional;
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

    @Transactional
    public CertificateModel save(CertificateModel model) {
        return repo.save(model);
    }

    @Transactional
    public void remove(CertificateModel model) {
        repo.delete(model);
    }

    public Optional<CertificateModel> findById(int id) {
        return repo.findById(id);
    }
}

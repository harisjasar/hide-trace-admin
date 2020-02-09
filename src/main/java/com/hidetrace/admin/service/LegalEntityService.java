/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.repository.LegalEntityRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class LegalEntityService {

    @Autowired
    private LegalEntityRepository legalEntityRepo;

    @Transactional
    public List<LegalEntityModel> getAllLegalEntities() {
        List<LegalEntityModel> entities = new ArrayList<>();
        legalEntityRepo.findAll().forEach(entities::add);
        return entities;
    }
}

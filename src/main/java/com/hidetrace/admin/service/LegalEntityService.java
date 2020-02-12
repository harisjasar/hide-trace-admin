/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.service;

import com.hidetrace.admin.model.LegalEntityModel;
import com.hidetrace.admin.repository.LegalEntityRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

    public String getUniqueLegalEntityCode() {
        Random rand = new Random();
        List<LegalEntityModel> entities = getAllLegalEntities();
        HashSet<String> legalEntityCodes = new HashSet<>();
        entities.forEach((entity) -> {
            legalEntityCodes.add(entity.getLegalEntityCode());
        });
        boolean found = false;

        int i = 0;
        while (!found) {
            i = rand.nextInt((999 - 100) + 1) + 100;
            if (!legalEntityCodes.contains(String.valueOf(i))) {
                found = true;
            }
        }

        return String.valueOf(i);
    }

    @Transactional
    public LegalEntityModel saveNewLegalEntityModel(LegalEntityModel model) {
        return legalEntityRepo.save(model);
    }
    @Autowired
    EntityManager entityManager;

    @Transactional
    public void createLegalEntityTable(String tableName) {
        String query_ = new StringBuilder()
                .append("CREATE  TABLE IF NOT EXISTS `%s` \n(")
                .append("`HideID` INT NOT NULL AUTO_INCREMENT,\n")
                .append("`HideCode` VARCHAR(6) NOT NULL,\n")
                .append("`IncomingDateTime` DATETIME NOT NULL,\n")
                .append("`OutgoingDateTime` DATETIME NULL,\n")
                .append("`IncomingWeight` DOUBLE NOT NULL,\n")
                .append("`OutgoingWeight` DOUBLE NULL,\n")
                .append("`OperatorID` INT NOT NULL,\n")
                .append("`CategoryID` INT NULL,\n")
                .append("`HideTypeID` INT NOT NULL,\n")
                .append("`IncomingLegalEntityID` INT NOT NULL,\n")
                .append("`IncomingInvoiceID` INT NOT NULL,\n")
                .append("`OutgoingInvoiceID` INT NULL,\n")
                .append("`OutgoingLegalEntityID` INT NULL,\n")
                .append("PRIMARY KEY (`HideID`),\n")
                .append("INDEX `fk_")
                .append(tableName)
                .append("_Operator1_idx` (`OperatorID` ASC) ,\n")
                .append("INDEX `fk_")
                .append(tableName)
                .append("_Category1_idx` (`CategoryID` ASC) ,\n")
                .append("INDEX `fk_")
                .append(tableName)
                .append("_HideType1_idx` (`HideTypeID` ASC) ,\n")
                .append("INDEX `fk_")
                .append(tableName)
                .append("_IncomingLegalEntity1_idx` (`IncomingLegalEntityID` ASC) ,\n")
                .append("INDEX `fk_")
                .append(tableName)
                .append("_IncomingInvoice1_idx` (`IncomingInvoiceID` ASC) ,\n")
                .append("INDEX `fk_")
                .append(tableName)
                .append("_OutgoingInvoice1_idx` (`OutgoingInvoiceID` ASC) ,\n")
                .append("INDEX `fk_")
                .append(tableName)
                .append("_OutgoingLegalEntity1_idx` (`OutgoingLegalEntityID` ASC) ,\n")
                .append("UNIQUE INDEX `HideCode_UNIQUE` (`HideCode` ASC),\n")
                .append("CONSTRAINT `fk_")
                .append(tableName)
                .append("_Operator1` \n")
                .append("FOREIGN KEY (`OperatorID`) \n")
                .append("REFERENCES `operator` (`OperatorID`) \n")
                .append("ON DELETE NO ACTION \n")
                .append("ON UPDATE NO ACTION,\n")
                .append("CONSTRAINT `fk_")
                .append(tableName)
                .append("_Category1`\n")
                .append("FOREIGN KEY (`CategoryID`) \n")
                .append("REFERENCES `category` (`CategoryID`) \n")
                .append("ON DELETE NO ACTION \n")
                .append("ON UPDATE NO ACTION,\n")
                .append("CONSTRAINT `fk_")
                .append(tableName)
                .append("_HideType1` \n")
                .append("FOREIGN KEY (`HideTypeID`) \n")
                .append("REFERENCES `hidetype` (`HideTypeID` ) \n")
                .append("ON DELETE NO ACTION \n")
                .append("ON UPDATE NO ACTION,\n")
                .append("CONSTRAINT `fk_")
                .append(tableName)
                .append("_IncomingLegalEntity1` \n")
                .append("FOREIGN KEY (`IncomingLegalEntityID`) \n")
                .append("REFERENCES `legalentity` (`LegalEntityID`) \n")
                .append("ON DELETE NO ACTION \n")
                .append("ON UPDATE NO ACTION,\n")
                .append("CONSTRAINT `fk_")
                .append(tableName)
                .append("_IncomingInvoice1` \n")
                .append("FOREIGN KEY (`IncomingInvoiceID`) \n")
                .append("REFERENCES `incominginvoice` (`IncomingInvoiceID`) \n")
                .append("ON DELETE NO ACTION \n")
                .append("ON UPDATE NO ACTION,\n")
                .append("CONSTRAINT `fk_")
                .append(tableName)
                .append("_OutgoingInvoice1` \n")
                .append("FOREIGN KEY (`OutgoingInvoiceID` ) \n")
                .append("REFERENCES `outgoinginvoice` (`OutgoingInvoiceID` ) \n")
                .append("ON DELETE NO ACTION \n")
                .append("ON UPDATE NO ACTION, \n")
                .append("CONSTRAINT `fk_")
                .append(tableName)
                .append("_OutgoingLegalEntity1` \n")
                .append("FOREIGN KEY (`OutgoingLegalEntityID` ) \n")
                .append(" REFERENCES `leatherms`.`legalentity` (`LegalEntityID` ) \n")
                .append("ON DELETE NO ACTION \n")
                .append("ON UPDATE NO ACTION)\n")
                .toString();

        String formattedQuery = String.format(query_, tableName);
        Query query = entityManager.createNativeQuery(formattedQuery);
        query.executeUpdate();

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.repository.outgoinginvoice;

import com.hidetrace.admin.model.outgoinginvoice.OutgoingInvoiceHideTypeCategoryModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jashar
 */
@Repository
public interface OutgoingInvoiceHideTypeCategoryRepository extends JpaRepository<OutgoingInvoiceHideTypeCategoryModel, Integer> {

    List<OutgoingInvoiceHideTypeCategoryModel> findAllByOutgoingInvoiceId(int id);
}

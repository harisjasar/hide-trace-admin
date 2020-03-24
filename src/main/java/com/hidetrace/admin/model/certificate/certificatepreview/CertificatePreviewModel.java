package com.hidetrace.admin.model.certificate.certificatepreview;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SqlResultSetMapping(
        name = "IncomingCertificatePreviewMapping",
        classes = @ConstructorResult(
                targetClass = CertificatePreviewModel.class,
                columns = {
                    @ColumnResult(name = "certificateType")
                    ,@ColumnResult(name = "certificateNumber")
                    ,@ColumnResult(name = "invoiceName")
                    ,@ColumnResult(name = "legalEntity")}))
public class CertificatePreviewModel {

    @Id
    String certificateType;
    String certificateNumber;
    String invoiceName;
    String legalEntity;
}

package com.hidetrace.admin.controller.certificate.certificatepreview;

import com.hidetrace.admin.common.RowFilterUtil;
import com.hidetrace.admin.model.certificate.certificatepreview.CertificatePreviewModel;
import com.hidetrace.admin.service.certificate.certificatepreview.CertificatePreviewService;
import com.hidetrace.admin.view.certificate.certificatepreview.CertificatePreviewView;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
@Data
public class CertificatePreviewController {

    @Autowired
    private CertificatePreviewView view;

    @Autowired
    private CertificatePreviewService service;

    private int type;

    private void initView() {
        view.setResizable(true);
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    private void initListeners() {
        RowFilterUtil.createRowFilter(view.getCertificatesTable(), view.getCertificateFilterTextfield());
    }

    private void initData() {
        List<CertificatePreviewModel> certs = new ArrayList<>();
        if (this.type == 0) {
            certs = service.IncomingCertificatesPreview();
        } else if (this.type == 1) {
            certs = service.OutgoingCertificatesPreview();
        }
        DefaultTableModel tbModel = (DefaultTableModel) view.getCertificatesTable().getModel();
        tbModel.setRowCount(0);

        certs.forEach((CertificatePreviewModel _item) -> {
            String[] aRow = {_item.getCertificateType(), _item.getCertificateNumber(), _item.getInvoiceName(), _item.getLegalEntity()};
            tbModel.addRow(aRow);
        });
    }

    public void start() {
        initListeners();
        initData();
        initView();

    }
}

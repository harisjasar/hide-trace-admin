/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hidetrace.admin.common;

import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
@Component
public class MessageDialog {

    /**
     * ERROR_MESSAGE = 0; INFORMATION_MESSAGE = 1; WARNING_MESSAGE = 2;
     * QUESTION_MESSAGE = 3; PLAIN_MESSAGE = -1;
     *
     * @param parentComponent
     * @param message
     * @param title
     * @param messageType
     */
    private void showMessageDialog(Component parentComponent,
            Object message, String title, int messageType) {

        JOptionPane.showMessageDialog((java.awt.Component) parentComponent, message, title, messageType);

    }

    public void EmptyFieldForbidden() {
        showMessageDialog(null, "Polje ne može biti prazno", "Pažnja", 0);
    }

    public void EnterPriceForSelectedType() {
        showMessageDialog(null, "Unesite cijenu za odabranu vrstu", "Pažnja", 0);
    }

    public void SelectType() {
        showMessageDialog(null, "Odaberi vrstu", "Pažnja", 3);
    }

    public void InvoiceSuccessfullyCreated() {
        showMessageDialog(null, "Faktura uspješno kreirana", "Poruka", 1);
    }

    public void InvoiceNotSuccessfullyCreated() {
        showMessageDialog(null, "Faktura: nije kreirana\n\nProvjerite log za više informacija", "Poruka", 0);
    }

    public void ErrorCreatingInvoice() {
        showMessageDialog(null, "Greška\n\nFaktura nije kreirana", "Pažnja", 0);
    }

    public void WrongFormat() {
        showMessageDialog(null, "Neispravan format", "Pažnja", 0);
    }

    public void AlreadyExist(String name) {
        showMessageDialog(null, "Greška " + name + " već postoji", "Pažnja", 0);
    }

    public void LegalEntitySuccessfullyCreated(String name) {
        showMessageDialog(null, "Pravno lice:   " + name + "   uspješno kreirano", "Poruka", 1);

    }

    public void WrongLoginCredentials() {
        showMessageDialog(null, "Nemate pravo pristupa", "Greška", 2);

    }

    public void UpdateSuccessful() {
        showMessageDialog(null, "Ažuriranje uspješno", "Info", 1);
    }

    public void UpdateUnsuccessful() {
        showMessageDialog(null, "Ažuriranje nije uspjelo", "Info", 0);
    }

    public void DeletionNotSuccessful() {
        showMessageDialog(null, "Brisanje nije uspjelo. Moguća referenca na stavku", "Greška", 0);
    }

    public void DeletionSuccessful() {
        showMessageDialog(null, "Brisanje uspjelo", "Info", 1);
    }

    public void InvoiceNotSelected() {
        showMessageDialog(null, "Brisanje nije uspjelo. Faktura nije odabrana", "Greška", 0);
    }

    public void LegalEntityNotSelected() {
        showMessageDialog(null, "Firma nije odabrana", "Greška", 0);
    }

    public void MaxNumberOfArticlesReached() {
        showMessageDialog(null, "Maksimalan broj artikala odabran", "Greška", 0);
    }

    public void ArticleNeedsToBeAdded() {
        showMessageDialog(null, "Dodajte artikal", "Greška", 0);
    }

    public void ArticlePriceCannotBeEmpty() {
        showMessageDialog(null, "Cijena artikla ne može biti prazna", "Greška", 0);
    }

    public void CreationSuccessful() {
        showMessageDialog(null, "Uspješno kreirano", "Info", 1);

    }

    public void CreationNotSuccessful() {
        showMessageDialog(null, "Kreiranje nije uspješno", "Info", 1);

    }

    public void HideTypeNotSelected() {
        showMessageDialog(null, "Vrsta mora biti odabrana", "Greška", 1);

    }

}

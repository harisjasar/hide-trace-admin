package com.hidetrace.admin;

import com.hidetrace.admin.controller.SplashScreenController;
import com.hidetrace.admin.controller.login.LoginController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AdminApplication {

    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    try {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AdminApplication.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(AdminApplication.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(AdminApplication.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(AdminApplication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        //</editor-fold>
        //</editor-fold>
        SplashScreenController.splashScreenThread();

        ConfigurableApplicationContext context = new SpringApplicationBuilder(AdminApplication.class).headless(false).run(args);
        LoginController loginController = context.getBean(LoginController.class);
        loginController.start();
    }

}

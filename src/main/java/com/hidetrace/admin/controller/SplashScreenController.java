package com.hidetrace.admin.controller;

import com.hidetrace.admin.view.SplashScreenView;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jashar
 */
public class SplashScreenController {
    // static used here because
    // a non-static variable cannot be referenced
    // from a static context
    // exit variable to stop thread

    static volatile boolean exit = false;
    static SplashScreenView splashFrame;

    public static void splashScreenThread() {

        new Thread() {
            @Override
            public void run() {
                // will not run infinitely
                while (!exit) {
                    if (splashFrame == null) {
                        splashFrame = new SplashScreenView();
                        splashFrame.setAlwaysOnTop(true);
                        splashFrame.setLocationRelativeTo(null);
                        splashFrame.setVisible(true);

                    }
                }

                // this will be printed
                splashFrame.dispose();
            }
        }.start();
    }

    @Component
    @Order(0)
    static class SplashScreenListener implements ApplicationListener<ApplicationReadyEvent> {

        @Override
        public void onApplicationEvent(ApplicationReadyEvent event) {
            SplashScreenController.exit = true;
        }

    }

}

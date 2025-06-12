package com.vpn.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VPNLogger {
    private static final Logger logger = Logger.getLogger("VPNLogger");

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }

    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }
}

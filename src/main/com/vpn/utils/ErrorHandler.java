package com.vpn.utils;

public class ErrorHandler {

    public static void handle(Exception e, String contextMessage) {
        VPNLogger.error(contextMessage, e);
    }

    public static void handle(String errorMessage) {
        VPNLogger.error(errorMessage);
    }
}

package com.vpn.core;

import java.io.*;
import java.util.*;

public class VPNClient {
    private Process vpnProcess;

    public boolean connectToVPN(String ovpnFilePath, String credentialsFilePath) {
        try {
            List<String> command = Arrays.asList(
                    "openvpn",
                    "--config", ovpnFilePath,
                    "--auth-user-pass", credentialsFilePath
            );

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            vpnProcess = builder.start();


            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(vpnProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[VPN] " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disconnectVPN() {
        if (vpnProcess != null && vpnProcess.isAlive()) {
            vpnProcess.destroy();
            System.out.println("VPN disconnected.");
            return true;
        }
        return false;
    }

    public boolean isConnected() {
        return vpnProcess != null && vpnProcess.isAlive();
    }
}

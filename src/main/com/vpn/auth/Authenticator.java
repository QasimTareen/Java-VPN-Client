package com.vpn.auth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Authenticator {

    public static boolean validate(String username, String password) {
        try {
            InputStream input = Authenticator.class.getClassLoader().getResourceAsStream("ovpn/credentials.txt");
            if (input == null) {
                System.err.println("credentials.txt not found in resources/ovpn");
                return false;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String fileUsername = reader.readLine();
            String filePassword = reader.readLine();
            return username.equals(fileUsername) && password.equals(filePassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

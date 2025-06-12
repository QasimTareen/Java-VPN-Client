package com.vpn.network;

public class VPNServer {
    private final String name;
    private final String ovpnPath;

    public VPNServer(String name, String ovpnPath) {
        this.name = name;
        this.ovpnPath = ovpnPath;
    }

    public String getName() {
        return name;
    }

    public String getOvpnPath() {
        return ovpnPath;
    }
}

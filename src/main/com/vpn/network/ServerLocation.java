package com.vpn.network;

import java.util.ArrayList;
import java.util.List;

public class ServerLocation {
    public static List<VPNServer> getAllServers() {
        List<VPNServer> servers = new ArrayList<>();
        servers.add(new VPNServer("Canada", "ovpn/vpnbook-ca196-udp53.ovpn"));
        servers.add(new VPNServer("Germany", "ovpn/vpnbook-de220-tcp443.ovpn"));
        servers.add(new VPNServer("Poland", "ovpn/vpnbook-pl140-udp25000.ovpn"));
        servers.add(new VPNServer("USA", "ovpn/vpnbook-us178-tcp80.ovpn"));
        return servers;
    }
}

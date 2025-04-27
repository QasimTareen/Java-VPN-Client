package vpn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VPNClient {
    public static void main(String[] args) {
        VPNClient client1 = new VPNClient();
        client1.G_u_I();
    }

    public void G_u_I()
    {
        JFrame frame = new JFrame("VPN CLIENT ");
        frame.setSize(300,200);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter the VPN Server ID : ");
        JTextField serverField = new JTextField(20);
        JButton connectButton = new JButton("Connect");
        JLabel statusLabel = new JLabel("Status : Not Connected :( ");

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String server_IP = serverField.getText();
                if(!server_IP.isEmpty())
                {
                    statusLabel.setText("Status: Connected to "+server_IP);
                    VPNLogManager.log("Connected to server : "+server_IP);
                }
                else
                {
                    statusLabel.setText("status : No IP Detected/Entered ");
                    VPNLogManager.log("No IP Entered ");
                }
            }
        });

        frame.add(label);
        frame.add(serverField);
        frame.add(connectButton);
        frame.add(statusLabel);

        frame.setVisible(true);
    }

    public void connect() {
        System.out.println(" Connecting to VPN... ");
        VPNLogManager.log("Connecting to VPN .... ");
    }

    public void disconnect() {
        System.out.println("Disconnecting from the VPN .... ");
        VPNLogManager.log("Disconnecting from VPN ... ");
    }
}

package vpn;

import javax.swing.*;
import java.awt.*;

public class VPNGUI {
    private VPNClient client = new VPNClient();

    public void launch() {
        JFrame frame = new JFrame("VPN Client");
        frame.setLayout(new FlowLayout());

        JButton connectButton = new JButton("Connect");
        JTextField messageField = new JTextField(20);
        JButton sendButton = new JButton("Send");
        connectButton.addActionListener(e -> {
            try {
                client.connect(VPNConfig.SERVER_IP, VPNConfig.SERVER_PORT);
            } catch (Exception ex) {
                VPNLogger.log("Connection failed: " + ex.getMessage());
            }
        });

        sendButton.addActionListener(e -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                try {
                    client.sendData(message);
                    VPNLogger.log("Message sent: " + message);
                } catch (Exception ex) {
                    VPNLogger.log("Failed to send: " + ex.getMessage());
                }
            }
        });

        frame.add(connectButton);
        frame.add(messageField);
        frame.add(sendButton);

        frame.setSize(350, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new VPNGUI().launch();
    }
}

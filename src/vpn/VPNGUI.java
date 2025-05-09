package vpn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VPNGUI {
    private VPNClient client = new VPNClient();
    private JLabel statusLabel;
    private Timer connectingAnimation;
    private boolean connectingDotVisible = true;

    public void launch() {
        JFrame frame = new JFrame("🚀 Stylish VPN Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("vpn_icon.png"));
            frame.setIconImage(icon.getImage());
        } catch (Exception ignored) {}

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(173, 216, 230), 0, getHeight(), new Color(135, 206, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Secure VPN Client", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(0, 100, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(titleLabel, gbc);

        statusLabel = new JLabel("Status: Disconnected", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusLabel.setForeground(Color.DARK_GRAY);
        gbc.gridy = 1;
        backgroundPanel.add(statusLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        JButton connectButton = createStyledButton("🔗 Connect");
        JButton sendButton = createStyledButton("✉️ Send");

        buttonPanel.add(connectButton);
        buttonPanel.add(sendButton);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        backgroundPanel.add(buttonPanel, gbc);

        JTextField messageField = new JTextField(20);
        messageField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        messageField.setBackground(new Color(220, 240, 255));
        messageField.setForeground(Color.BLACK);
        gbc.gridy = 3;
        backgroundPanel.add(messageField, gbc);

        frame.add(backgroundPanel);
        frame.setResizable(false);
        frame.setVisible(true);

        connectButton.addActionListener(e -> {
            statusLabel.setForeground(Color.ORANGE);
            startConnectingAnimation();
            new Thread(() -> {
                try {
                    client.connect(VPNConfig.SERVER_IP, VPNConfig.SERVER_PORT);
                    stopConnectingAnimation();
                    statusLabel.setText("Status: Connected");
                    statusLabel.setForeground(Color.GREEN.darker());
                } catch (Exception ex) {
                    stopConnectingAnimation();
                    VPNLogger.log("Connection failed: " + ex.getMessage());
                    statusLabel.setText("Status: Connection Failed");
                    statusLabel.setForeground(Color.RED);
                }
            }).start();
        });

        sendButton.addActionListener(e -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                try {
                    client.sendData(message);
                    VPNLogger.log("Message sent: " + message);
                    messageField.setText("");
                } catch (Exception ex) {
                    VPNLogger.log("Failed to send: " + ex.getMessage());
                }
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(50, 205, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(34, 139, 34));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(50, 205, 50));
            }
        });
        return button;
    }

    private void startConnectingAnimation() {
        connectingAnimation = new Timer(500, e -> {
            if (connectingDotVisible) {
                statusLabel.setText("Status: Connecting...");
            } else {
                statusLabel.setText("Status: Connecting");
            }
            connectingDotVisible = !connectingDotVisible;
        });
        connectingAnimation.start();
    }

    private void stopConnectingAnimation() {
        if (connectingAnimation != null) {
            connectingAnimation.stop();
        }
    }

    public static void main(String[] args) {
        new VPNGUI().launch();
    }
}

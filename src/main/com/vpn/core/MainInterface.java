package com.vpn.core;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.List;

class MainInterfaceScreen {

    private static final VPNClient vpnClient = new VPNClient();

    public static Scene createMainScene(Stage stage, String mode) {
        Image background = new Image(MainInterfaceScreen.class.getResource("/assets/background-3.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(600);
        backgroundView.setFitHeight(400);

        // Toggle Button Design
        ToggleButton toggleButton = new ToggleButton("Connect");
        toggleButton.setFont(Font.font("Arial", 18));
        toggleButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 30px;");
        toggleButton.setPrefWidth(180);
        toggleButton.setPrefHeight(50);

        toggleButton.setOnMouseEntered(e ->
                toggleButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-background-radius: 30px;")
        );
        toggleButton.setOnMouseExited(e ->
                toggleButton.setStyle(toggleButton.isSelected()
                        ? "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 30px;"
                        : "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 30px;")
        );

        // Server List with Animation
        ComboBox<String> serverList = new ComboBox<>();
        List<String> servers = mode.equals("SIGN_IN")
                ? List.of("vpnbook-ca196-udp53.ovpn", "vpnbook-de220-tcp443.ovpn", "vpnbook-pl140-udp25000.ovpn", "vpnbook-us178-tcp80.ovpn")
                : List.of("vpnbook-us178-tcp80.ovpn");

        serverList.setItems(FXCollections.observableArrayList(servers));
        serverList.getSelectionModel().selectFirst();
        serverList.setStyle("-fx-background-radius: 10; -fx-font-size: 14px;");
        serverList.setPrefWidth(300);

        // Fade animation
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), serverList);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Scale on hover
        serverList.setOnMouseEntered(e -> {
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), serverList);
            scaleUp.setToX(1.05);
            scaleUp.setToY(1.05);
            scaleUp.play();
        });

        serverList.setOnMouseExited(e -> {
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), serverList);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);
            scaleDown.play();
        });

        toggleButton.setOnAction(e -> {
            try {
                String selectedServer = serverList.getValue();
                File ovpnFile = copyResourceToTempFile("/ovpn/" + selectedServer);
                File credentialsFile = copyResourceToTempFile("/ovpn/credentials.txt");

                if (toggleButton.isSelected()) {
                    toggleButton.setText("Disconnect");
                    toggleButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 30px;");
                    vpnClient.connectToVPN(ovpnFile.getAbsolutePath(), credentialsFile.getAbsolutePath());
                } else {
                    toggleButton.setText("Connect");
                    toggleButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 30px;");
                    vpnClient.disconnectVPN();
                }
            } catch (Exception ex) {
                ex.printStackTrace(); // Replace with ErrorHandler if needed
            }
        });

        VBox content = new VBox(30, serverList, toggleButton);
        content.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(backgroundView, content);
        return new Scene(root, 600, 400);
    }

    private static File copyResourceToTempFile(String resourcePath) throws IOException {
        InputStream in = MainInterfaceScreen.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + resourcePath);
        }

        File tempFile = File.createTempFile("vpn_", "_" + new File(resourcePath).getName());
        tempFile.deleteOnExit();

        try (OutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }

        return tempFile;
    }
}

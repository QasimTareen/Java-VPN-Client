package com.vpn.core;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        ToggleButton toggleButton = new ToggleButton("Connect");
        toggleButton.setFont(Font.font("Arial", 18));
        toggleButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 30px;");
        toggleButton.setPrefWidth(180);
        toggleButton.setPrefHeight(50);

        toggleButton.setOnMouseEntered(e ->
                toggleButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-background-radius: 30px;"));
        toggleButton.setOnMouseExited(e ->
                toggleButton.setStyle(toggleButton.isSelected()
                        ? "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 30px;"
                        : "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 30px;"));

        ComboBox<String> serverList = new ComboBox<>();
        List<String> servers = mode.equals("SIGN_IN")
                ? List.of("vpnbook-ca196-udp53.ovpn", "vpnbook-de220-tcp443.ovpn", "vpnbook-pl140-udp25000.ovpn", "vpnbook-us178-tcp80.ovpn")
                : List.of("vpnbook-us178-tcp80.ovpn");

        serverList.setItems(FXCollections.observableArrayList(servers));
        serverList.getSelectionModel().selectFirst();
        serverList.setStyle("-fx-background-radius: 10; -fx-font-size: 14px;");
        serverList.setPrefWidth(300);

        // Fade-in effect
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), serverList);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

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
                ex.printStackTrace(); // Add logging or alert box if needed
            }
        });

        // Back Button
        Button backButton = new Button("← Back");
        backButton.setFont(Font.font("Arial", 14));
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            vpnClient.disconnectVPN(); // Optional: clean up
            stage.setScene(ModeSelectionScreen.createModeSelectionScene(stage));
        });

        VBox content = new VBox(20, backButton, serverList, toggleButton);
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

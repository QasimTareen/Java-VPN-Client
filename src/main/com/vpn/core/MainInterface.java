package com.vpn.core;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

class MainInterfaceScreen {

    private static final VPNClient vpnClient = new VPNClient();

    public static Scene createMainScene(Stage stage, String mode) {
        Image background = new Image(MainInterfaceScreen.class.getResource("/assets/background-3.jpg").toExternalForm());
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(600);
        backgroundView.setFitHeight(400);

        Button backButton = new Button("← Back");
        backButton.setFont(Font.font(14));
        backButton.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-background-radius: 10;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white; -fx-background-radius: 10;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-background-radius: 10;"));
        backButton.setOnAction(e -> stage.setScene(ModeSelectionScreen.createModeSelectionScene(stage)));

        HBox backBox = new HBox(backButton);
        backBox.setAlignment(Pos.TOP_LEFT);
        backBox.setPadding(new Insets(10));

        // Map of server names to OVPN files and flag images
        Map<String, ServerData> serverMap = new LinkedHashMap<>();
        if (mode.equals("SIGN_IN")) {
            serverMap.put("Canada", new ServerData("vpnbook-ca196-udp53.ovpn", "/assets/Canada.jpg"));
            serverMap.put("Germany", new ServerData("vpnbook-de220-tcp443.ovpn", "/assets/Germany.jpg"));
            serverMap.put("Poland", new ServerData("vpnbook-pl140-udp25000.ovpn", "/assets/Poland.jpg"));
            serverMap.put("United States", new ServerData("vpnbook-us178-tcp80.ovpn", "/assets/USA.jpg"));
        } else {
            serverMap.put("United States", new ServerData("vpnbook-us178-tcp80.ovpn", "/assets/USA.jpg"));
        }

        ObservableList<String> serverNames = FXCollections.observableArrayList(serverMap.keySet());
        ComboBox<String> serverList = new ComboBox<>(serverNames);
        serverList.getSelectionModel().selectFirst();
        serverList.setPrefWidth(300);
        serverList.setStyle("-fx-background-color: #34495e; -fx-background-radius: 10; -fx-text-fill: white; -fx-font-size: 14px;");

        // Custom cell factory to add flag icons
        serverList.setCellFactory(lv -> new ListCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String country, boolean empty) {
                super.updateItem(country, empty);
                if (empty || country == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ServerData data = serverMap.get(country);
                    if (data != null) {
                        imageView.setImage(new Image(MainInterfaceScreen.class.getResourceAsStream(data.flagPath)));
                        imageView.setFitWidth(20);
                        imageView.setFitHeight(15);
                        setGraphic(imageView);
                        setText(country);
                        setTextFill(Color.WHITE);
                        setFont(Font.font(14));
                        setBackground(new Background(new BackgroundFill(Color.web("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }
            }
        });

        // Button Cell for selected item
        serverList.setButtonCell(new ListCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String country, boolean empty) {
                super.updateItem(country, empty);
                if (empty || country == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    ServerData data = serverMap.get(country);
                    if (data != null) {
                        imageView.setImage(new Image(MainInterfaceScreen.class.getResourceAsStream(data.flagPath)));
                        imageView.setFitWidth(20);
                        imageView.setFitHeight(15);
                        setGraphic(imageView);
                        setText(country);
                        setTextFill(Color.WHITE);
                        setFont(Font.font(14));
                        setBackground(new Background(new BackgroundFill(Color.web("#34495e"), CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                }
            }
        });

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

        Button connectButton = new Button("Connect");
        connectButton.setFont(Font.font("Arial", 18));
        connectButton.setPrefWidth(200);
        connectButton.setPrefHeight(55);
        connectButton.setStyle("-fx-background-color: linear-gradient(to right, #00b09b, #96c93d); -fx-text-fill: white; -fx-background-radius: 30px;");
        connectButton.setOnMouseEntered(e -> connectButton.setStyle("-fx-background-color: linear-gradient(to right, #11998e, #38ef7d); -fx-text-fill: white; -fx-background-radius: 30px;"));
        connectButton.setOnMouseExited(e -> connectButton.setStyle("-fx-background-color: linear-gradient(to right, #00b09b, #96c93d); -fx-text-fill: white; -fx-background-radius: 30px;"));

        Label statusLabel = new Label("");
        statusLabel.setFont(Font.font("Arial", 14));
        statusLabel.setTextFill(Color.WHITE);

        connectButton.setOnAction(e -> {
            if (connectButton.getText().equals("Connect")) {
                connectButton.setDisable(true);
                connectButton.setText("Connecting...");
                statusLabel.setText("Establishing connection...");
                ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), connectButton);
                scaleTransition.setToX(1.1);
                scaleTransition.setToY(1.1);

                FadeTransition fadeStatus = new FadeTransition(Duration.seconds(0.5), statusLabel);
                fadeStatus.setFromValue(0);
                fadeStatus.setToValue(1);

                SequentialTransition seq = new SequentialTransition(scaleTransition, fadeStatus);
                seq.play();

                PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
                pause.setOnFinished(ev -> {
                    try {
                        String selectedCountry = serverList.getValue();
                        ServerData serverData = serverMap.get(selectedCountry);
                        File ovpnFile = copyResourceToTempFile("/ovpn/" + serverData.ovpnFile);
                        File credentialsFile = copyResourceToTempFile("/ovpn/credentials.txt");
                        vpnClient.connectToVPN(ovpnFile.getAbsolutePath(), credentialsFile.getAbsolutePath());

                        connectButton.setText("Disconnect");
                        connectButton.setDisable(false);
                        connectButton.setStyle("-fx-background-color: linear-gradient(to right, #ff416c, #ff4b2b); -fx-text-fill: white; -fx-background-radius: 30px;");
                        statusLabel.setText("Connected to " + selectedCountry);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        statusLabel.setText("Connection failed.");
                        connectButton.setText("Connect");
                        connectButton.setDisable(false);
                    }
                });
                pause.play();

            } else {
                vpnClient.disconnectVPN();
                connectButton.setText("Connect");
                connectButton.setStyle("-fx-background-color: linear-gradient(to right, #00b09b, #96c93d); -fx-text-fill: white; -fx-background-radius: 30px;");
                statusLabel.setText("Disconnected.");
            }
        });

        VBox content = new VBox(20, serverList, connectButton, statusLabel);
        content.setAlignment(Pos.CENTER);

        BorderPane layout = new BorderPane();
        layout.setTop(backBox);
        layout.setCenter(content);

        StackPane root = new StackPane(backgroundView, layout);
        return new Scene(root, 600, 400);
    }

    private static File copyResourceToTempFile(String resourcePath) throws IOException {
        InputStream in = MainInterfaceScreen.class.getResourceAsStream(resourcePath);
        if (in == null) throw new FileNotFoundException("Resource not found: " + resourcePath);

        File tempFile = File.createTempFile("vpn_", "_" + new File(resourcePath).getName());
        tempFile.deleteOnExit();

        try (OutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) out.write(buffer, 0, len);
        }
        return tempFile;
    }

    // Helper class to store server info
    private static class ServerData {
        String ovpnFile;
        String flagPath;

        ServerData(String ovpnFile, String flagPath) {
            this.ovpnFile = ovpnFile;
            this.flagPath = flagPath;
        }
    }
}

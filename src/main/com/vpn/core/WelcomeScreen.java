package com.vpn.core;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WelcomeScreen {

    public static Scene createWelcomeScene(Stage primaryStage) {
        Image background = new Image("assets/background-1.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(600);
        backgroundView.setFitHeight(400);


        Label title = new Label("Secure VPN");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setStyle("-fx-text-fill: white;");


        Image logo = new Image("assets/vpn.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitHeight(100);
        logoView.setPreserveRatio(true);


        Button getStarted = new Button("Get Started");
        getStarted.setFont(Font.font(18));
        getStarted.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 20;");
        getStarted.setOnAction(e -> {
            primaryStage.setScene(ModeSelectionScreen.createModeSelectionScene(primaryStage));
        });


        VBox vbox = new VBox(20, title, logoView, getStarted);
        vbox.setAlignment(Pos.CENTER);

        StackPane layout = new StackPane(backgroundView, vbox);
        return new Scene(layout, 600, 400);
    }
}

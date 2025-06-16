package com.vpn.core;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        getStarted.setOnAction(e -> primaryStage.setScene(ModeSelectionScreen.createModeSelectionScene(primaryStage)));

        Label footer = new Label("Developed by Galos Team");
        footer.setFont(Font.font("Segoe UI", FontPosture.ITALIC, 14));
        footer.setStyle("-fx-text-fill: white; -fx-background-color: rgba(0,0,0,0.25); -fx-padding: 5 15 5 15; -fx-background-radius: 15;");

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), footer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(1.5), footer);
        scaleIn.setFromX(0.8);
        scaleIn.setFromY(0.8);
        scaleIn.setToX(1);
        scaleIn.setToY(1);

        SequentialTransition animation = new SequentialTransition(fadeIn, scaleIn);
        animation.play();

        VBox vbox = new VBox(15, title, logoView, getStarted, footer);
        vbox.setAlignment(Pos.CENTER);
        vbox.setTranslateY(-20);

        StackPane layout = new StackPane(backgroundView, vbox);
        return new Scene(layout, 600, 400);
    }
}

package com.vpn.core;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.vpn.auth.Authenticator;
import com.vpn.utils.ErrorHandler;

public class ModeSelectionScreen {

    public static Scene createModeSelectionScene(Stage primaryStage) {
        Image background = new Image("assets/background-2.jpg");
        ImageView backgroundView = new ImageView(background);
        backgroundView.setFitWidth(600);
        backgroundView.setFitHeight(400);

        // Buttons
        Button signInButton = new Button("Sign In");
        styleButton(signInButton);

        Button guestModeButton = new Button("Guest Mode");
        styleButton(guestModeButton);

        signInButton.setOnAction(e -> showLoginDialog(primaryStage));
        guestModeButton.setOnAction(e ->
                primaryStage.setScene(MainInterfaceScreen.createMainScene(primaryStage, "GUEST"))
        );

        VBox vbox = new VBox(20, signInButton, guestModeButton);
        vbox.setAlignment(Pos.CENTER);

        StackPane layout = new StackPane(backgroundView, vbox);
        return new Scene(layout, 600, 400);
    }

    private static void styleButton(Button button) {
        button.setFont(Font.font(16));
        button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 10;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-background-radius: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 10;"));
        button.setPrefWidth(200);
        button.setPrefHeight(40);
    }

    private static void showLoginDialog(Stage primaryStage) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Sign In");
        dialog.setHeaderText(null);

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Load background and logo
        ImageView backgroundView = new ImageView(new Image("assets/background-4.jpg"));
        backgroundView.setFitWidth(400);
        backgroundView.setFitHeight(300);

        ImageView logoView = new ImageView(new Image("assets/login.jpg"));
        logoView.setFitHeight(80);
        logoView.setPreserveRatio(true);

        // Fields
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        VBox contentBox = new VBox(20, logoView, grid);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPrefSize(400, 300);

        StackPane layeredPane = new StackPane(backgroundView, contentBox);
        dialog.getDialogPane().setContent(layeredPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new String[]{usernameField.getText(), passwordField.getText()};
            }
            return null;
        });

        dialog.showAndWait().ifPresent(credentials -> {
            boolean valid = Authenticator.validate(credentials[0], credentials[1]);
            if (valid) {
                primaryStage.setScene(MainInterfaceScreen.createMainScene(primaryStage, "SIGN_IN"));
            } else {
                ErrorHandler.handle("Invalid credentials. Access denied.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Invalid Credentials");
                alert.setContentText("Please enter the correct username and password.");
                alert.showAndWait();
            }
        });
    }
}

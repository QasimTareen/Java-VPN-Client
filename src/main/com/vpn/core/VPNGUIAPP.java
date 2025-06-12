package com.vpn.core;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VPNGUIAPP extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene welcomeScene = WelcomeScreen.createWelcomeScene(primaryStage);
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("SecureVPN");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

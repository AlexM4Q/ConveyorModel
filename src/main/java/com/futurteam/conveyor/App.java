package com.futurteam.conveyor;

import com.futurteam.conveyor.services.ResourcesService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class App extends Application {

    @NotNull
    private static final String APPLICATION_NAME = "Лабораторная работа #002#";

    public static void main(@NotNull final String[] args) {
        launch(args);
    }

    public void start(@NotNull final Stage primaryStage) throws IOException {
        @NotNull final Parent root = FXMLLoader.load(ResourcesService.START_LAYOUT_FXML);
        @NotNull final Scene scene = new Scene(root);
        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

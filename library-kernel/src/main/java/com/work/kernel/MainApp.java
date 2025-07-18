package com.work.kernel;

import com.work.kernel.controller.KernelController; 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlLocation = getClass().getResource("/view/kernel-view.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        Parent root = loader.load();

        KernelController controller = loader.getController();

        controller.loadPlugins();

        Scene scene = new Scene(root, 1024, 768);
        stage.setTitle("Library System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
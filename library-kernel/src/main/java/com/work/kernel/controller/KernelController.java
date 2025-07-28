package com.work.kernel.controller;

import com.work.api.LibraryPlugin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ServiceLoader;

public class KernelController {

    @FXML
    private VBox pluginMenu; 

    @FXML
    private StackPane contentArea; 

    public void loadPlugins() {
        ServiceLoader<LibraryPlugin> loader = ServiceLoader.load(LibraryPlugin.class);

        System.out.println("Searching for plugins...");
        for (LibraryPlugin plugin : loader) {
            System.out.println("Plugin found: " + plugin.getName());
            Button pluginButton = new Button(plugin.getName());
            pluginButton.setMaxWidth(Double.MAX_VALUE);
            pluginButton.getStyleClass().add("sidebar-button");

            pluginButton.setOnAction(e -> contentArea.getChildren().setAll(plugin.getView()));
            
            pluginMenu.getChildren().add(pluginButton);
        }
    }
}
package com.work.kernel.controller;

import com.work.api.LibraryPlugin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ServiceLoader;

public class KernelController {

    @FXML
    private VBox pluginMenu; // Injetado do FXML (a barra lateral)

    @FXML
    private StackPane contentArea; // Injetado do FXML (a área central)

    /**
     * Este método é chamado a partir da MainApp para iniciar a descoberta de plugins.
     */
    public void loadPlugins() {
        ServiceLoader<LibraryPlugin> loader = ServiceLoader.load(LibraryPlugin.class);

        System.out.println("Searching for plugins...");
        for (LibraryPlugin plugin : loader) {
            System.out.println("Plugin found: " + plugin.getName());
            Button pluginButton = new Button(plugin.getName());
            pluginButton.setMaxWidth(Double.MAX_VALUE); // Faz o botão ocupar toda a largura
            pluginButton.getStyleClass().add("sidebar-button"); // Adiciona uma classe de estilo

            // A ação do botão agora atualiza a área de conteúdo central.
            pluginButton.setOnAction(e -> contentArea.getChildren().setAll(plugin.getView()));
            
            pluginMenu.getChildren().add(pluginButton);
        }
    }
}
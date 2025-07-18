package com.work.users;

import com.work.api.LibraryPlugin;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;

public class UserManagementPlugin implements LibraryPlugin {

    @Override
    public String getName() {
        return "User Management";
    }

    @Override
    public Pane getView() {
        try {
            URL fxmlLocation = getClass().getResource("view/user-management-view.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Cannot find FXML file.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new Pane(new Label("Error loading User Management plugin: " + e.getMessage()));
        }
    }
}
package com.work.books;

import com.work.api.LibraryPlugin;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;

public class BookManagementPlugin implements LibraryPlugin {

    @Override
    public String getName() {
        return "Books Management";
    }

    @Override
    public Pane getView() {
        try {
            URL fxmlLocation = getClass().getResource("view/book-management-view.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Cannot find FXML file.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new Pane(new Label("Error loading Books Management plugin: " + e.getMessage()));
        }
    }
}
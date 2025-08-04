package com.work.loan;

import com.work.api.LibraryPlugin;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;

public class LoanManagementPlugin implements LibraryPlugin {

    @Override
    public String getName() {
        return "Loans Management";
    }

    @Override
    public Pane getView() {
        try {
            URL fxmlLocation = getClass().getResource("view/loan-management-view.fxml");
            if (fxmlLocation == null) {
                throw new IOException("Cannot find FXML file.");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new Pane(new Label("Error loading loans Management plugin: " + e.getMessage()));
        }
    }
}
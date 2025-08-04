package com.work.report;

import com.work.api.LibraryPlugin;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;

public class ReportPlugin implements LibraryPlugin {

    @Override
    public String getName() {
        return "Reports";
    }

    @Override
    public Pane getView() {
        try {
            URL fxmlLocation = getClass().getResource("/com/work/report/view/report-view.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new Pane(new Label("Error loading Reports plugin: " + e.getMessage()));
        }
    }
}
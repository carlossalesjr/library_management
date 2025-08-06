package com.work.api.controller;

import com.work.api.dao.GenericDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import java.util.List;

public abstract class GenericController<T> {

    protected GenericDAO<T> dao;
    protected T selectedEntity;

    protected abstract void configureTableColumns();
    protected abstract void populateForm(T entity);
    protected abstract void clearForm();
    protected abstract T getEntityFromForm();
    protected abstract void setEntityToForm(T entity);

    @FXML
    public void initialize() {
        configureTableColumns();
        getTableView().getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    this.selectedEntity = newValue;
                    populateForm(newValue);
                }
        );
        refreshTable();
    }

    @FXML
    protected void handleSave() {
        T entity = getEntityFromForm();
        if (entity != null) {
            dao.save(entity);
            refreshTable();
            clearForm();
        }
    }

    @FXML
    protected void handleDelete() {
        if (selectedEntity != null) {
            dao.delete(getEntityId(selectedEntity)); 
            refreshTable();
            clearForm();
        } else {
            showAlert("Selection Error", "No item selected for deletion.");
        }
    }
    
    @FXML
    protected void handleClear() {
        clearForm();
    }

    public void refreshTable() {
        List<T> items = dao.findAll();
        getTableView().setItems(FXCollections.observableArrayList(items));
    }
    protected abstract TableView<T> getTableView();
    protected abstract Long getEntityId(T entity);

    protected void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package com.work.users.controller;

import com.work.api.controller.GenericController;
import com.work.api.dao.UserDAO;
import com.work.api.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserManagementController extends GenericController<User> {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, Long> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private Label formHeaderLabel;

    public UserManagementController() {
        this.dao = new UserDAO();
    }

    @Override
    protected void configureTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    @Override
    protected void populateForm(User user) {
        if (user != null) {
            formHeaderLabel.setText("Edit User");
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        }
    }

    @Override
    protected void clearForm() {
        userTable.getSelectionModel().clearSelection();
        selectedEntity = null;
        formHeaderLabel.setText("Add New User");
        nameField.clear();
        emailField.clear();
    }

    @Override
    protected User getEntityFromForm() {
        String name = nameField.getText();
        String email = emailField.getText();
        if (name.trim().isEmpty() || email.trim().isEmpty()) {
            showAlert("Validation Error", "Name and Email fields cannot be empty.");
            return null;
        }

        User user = (selectedEntity == null) ? new User() : selectedEntity;
        user.setName(name);
        user.setEmail(email);
        return user;
    }
    
    @Override
    protected void setEntityToForm(User entity) {
        // Este método seria usado se precisássemos de definir a entidade a partir de fora
    }

    @Override
    protected TableView<User> getTableView() {
        return userTable;
    }

    @Override
    protected Long getEntityId(User entity) {
        return entity.getId();
    }
}
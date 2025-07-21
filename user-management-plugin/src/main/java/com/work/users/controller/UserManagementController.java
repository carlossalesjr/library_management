package com.work.users.controller;

import com.work.users.dao.UserDAO;
import com.work.users.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;

public class UserManagementController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn; 
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton; 
    @FXML
    private Label formHeaderLabel;

    private UserDAO userDAO;
    private User selectedUser; 

    @FXML
     public void initialize() {
        this.userDAO = new UserDAO();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> populateForm(newValue)
        );
        refreshTable();
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.trim().isEmpty() || email.trim().isEmpty()) {
            showAlert("Validation Error", "Name and Email fields cannot be empty.");
            return;
        }

        if (selectedUser == null) {
            User newUser = new User(name, email);
            userDAO.save(newUser);
        } else { 
            selectedUser.setName(name);
            selectedUser.setEmail(email);
            userDAO.save(selectedUser);
        }

        refreshTable();
        clearForm();
    }

    @FXML
    private void handleDelete() {
        if (selectedUser == null) {
            showAlert("Selection Error", "Please select a user to delete.");
            return;
        }
        userDAO.delete(selectedUser.getId());
        refreshTable();
        clearForm();
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void populateForm(User user) {
        this.selectedUser = user;
        if (user != null) {
            formHeaderLabel.setText("Edit User");
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        }
    }

     private void clearForm() {
        userTable.getSelectionModel().clearSelection();
        this.selectedUser = null;
        formHeaderLabel.setText("Add New User");
        nameField.clear();
        emailField.clear();
    }

    private void refreshTable() {
        userTable.setItems(FXCollections.observableArrayList(userDAO.findAll()));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
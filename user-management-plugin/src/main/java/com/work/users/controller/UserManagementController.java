package com.work.users.controller;

import com.work.api.controller.GenericController;
import com.work.api.dao.UserDAO;
import com.work.api.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
    @FXML private ComboBox<String> sortComboBox;

    public UserManagementController() {
        this.dao = new UserDAO();
    }

    @Override
    public void initialize() {
        super.initialize();
        setupSortComboBox();
    }
    
    private void setupSortComboBox() {
        sortComboBox.setItems(FXCollections.observableArrayList("Default (ID)", "Name"));
        sortComboBox.getSelectionModel().selectFirst();
        sortComboBox.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> refreshTable()
        );
    }

    @Override
    public void refreshTable() {
        String selection = sortComboBox.getSelectionModel().getSelectedItem();
        UserDAO.SortBy criteria = UserDAO.SortBy.ID;

        if ("Name".equals(selection)) {
            criteria = UserDAO.SortBy.NAME;
        } 

        getTableView().setItems(FXCollections.observableArrayList(((UserDAO) dao).findAll(criteria)));
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
        getTableView().getSelectionModel().clearSelection();
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
    protected void setEntityToForm(User entity) {}

    @Override
    protected TableView<User> getTableView() {
        return userTable;
    }

    @Override
    protected Long getEntityId(User entity) {
        return entity.getId();
    }
}
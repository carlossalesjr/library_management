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

    // --- Injeção de Componentes do FXML ---
    // A anotação @FXML liga estas variáveis aos componentes com o mesmo 'fx:id' no ficheiro FXML.
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn; // Nome correto
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
    private Button deleteButton; // Adicione um botão com fx:id="deleteButton" no seu FXML
    @FXML
    private Button clearButton; // Adicione um botão com fx:id="clearButton" no seu FXML
    @FXML
    private Label formHeaderLabel;

    private UserDAO userDAO;
    private User selectedUser; // Para saber qual utilizador está selecionado para edição/exclusão

    /**
     * O método initialize() é chamado automaticamente pelo JavaFX depois do FXML ser carregado.
     * É o local perfeito para configurar a nossa tela.
     */
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

    /**
     * Método chamado quando o botão "Save" é clicado.
     * Ele lida tanto com a criação de um novo utilizador como com a atualização de um existente.
     */
    @FXML
    private void handleSave() {
        String name = nameField.getText();
        String email = emailField.getText();

        if (name.trim().isEmpty() || email.trim().isEmpty()) {
            showAlert("Validation Error", "Name and Email fields cannot be empty.");
            return;
        }

        if (selectedUser == null) { // Se não há nenhum utilizador selecionado, é um novo registo.
            User newUser = new User(name, email);
            userDAO.save(newUser);
        } else { // Se há um utilizador selecionado, é uma atualização.
            selectedUser.setName(name);
            selectedUser.setEmail(email);
            userDAO.save(selectedUser);
        }

        refreshTable();
        clearForm();
    }

    /**
     * Método chamado quando o botão "Delete" é clicado.
     */
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

    /**
     * Limpa o formulário e a seleção.
     */
    @FXML
    private void handleClear() {
        clearForm();
    }

    /**
     * Preenche os campos de texto com os dados do utilizador selecionado na tabela.
     * @param user O utilizador que foi selecionado.
     */
    private void populateForm(User user) {
        this.selectedUser = user;
        if (user != null) {
            formHeaderLabel.setText("Edit User"); // Muda o título do formulário
            nameField.setText(user.getName());
            emailField.setText(user.getEmail());
        }
    }

    /**
     * Limpa os campos de texto e anula a seleção.
     */
     private void clearForm() {
        userTable.getSelectionModel().clearSelection(); // Limpa a seleção na tabela
        this.selectedUser = null;
        formHeaderLabel.setText("Add New User"); // Restaura o título
        nameField.clear();
        emailField.clear();
    }

    /**
     * Busca todos os utilizadores no banco e atualiza a tabela.
     */
    private void refreshTable() {
        userTable.setItems(FXCollections.observableArrayList(userDAO.findAll()));
    }

    /**
     * Um método utilitário para mostrar alertas ao utilizador.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package com.work.loan.controller;

import com.work.api.dao.BookDAO;
import com.work.api.dao.LoanDAO;
import com.work.api.dao.UserDAO;
import com.work.api.model.Book;
import com.work.api.model.Loan;
import com.work.api.model.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class LoanManagementController {

    @FXML private ComboBox<User> userComboBox;
    @FXML private ComboBox<Book> bookComboBox;
    @FXML private TableView<Loan> loanTable;
    @FXML private TableColumn<Loan, String> loanBookTitleColumn;
    @FXML private TableColumn<Loan, String> loanUserNameColumn;
    @FXML private TableColumn<Loan, LocalDateTime> loanDateColumn;
    @FXML private TableColumn<Loan, String> statusColumn;
    @FXML private RadioButton activeFilterRadio;
    @FXML private RadioButton returnedFilterRadio;
    @FXML private RadioButton allFilterRadio;
    @FXML private ToggleGroup filterToggleGroup;
    @FXML private Button returnButton;
    @FXML private Button lendButton;

    private UserDAO userDAO;
    private BookDAO bookDAO;
    private LoanDAO loanDAO;

    @FXML
    public void initialize() {
        this.userDAO = new UserDAO();
        this.bookDAO = new BookDAO();
        this.loanDAO = new LoanDAO();

        activeFilterRadio.setToggleGroup(filterToggleGroup);
        returnedFilterRadio.setToggleGroup(filterToggleGroup);
        allFilterRadio.setToggleGroup(filterToggleGroup);
        activeFilterRadio.setSelected(true);
        filterToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> refreshLoanTable());

        configureComboBoxes();
        loadUsersIntoComboBox();
        loadAvailableBooksIntoComboBox();
        setupLoanTableColumns();
        refreshLoanTable();
    }
    
    private void configureComboBoxes() {
        userComboBox.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user == null ? null : user.getName();
            }
            @Override
            public User fromString(String string) { return null; }
        });

        bookComboBox.setConverter(new StringConverter<Book>() {
            @Override
            public String toString(Book book) {
                return book == null ? null : book.getTitle();
            }
            @Override
            public Book fromString(String string) { return null; }
        });
    }

    private void loadUsersIntoComboBox() {
        userComboBox.setItems(FXCollections.observableArrayList(userDAO.findAll()));
    }

    private void loadAvailableBooksIntoComboBox() {
        List<Book> availableBooks = bookDAO.findAll().stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .collect(Collectors.toList());
        bookComboBox.setItems(FXCollections.observableArrayList(availableBooks));
    }

    private void setupLoanTableColumns() {
        loanBookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("book"));
        loanUserNameColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void refreshLoanTable() {
        List<Loan> loans;
        if (returnedFilterRadio.isSelected()) {
            loans = loanDAO.findReturnedLoans();
        } else if (allFilterRadio.isSelected()) {
            loans = loanDAO.findAll();
        } else {
            loans = loanDAO.findActiveLoans();
        }
        loanTable.setItems(FXCollections.observableArrayList(loans));
    }

    @FXML
    private void handleLendBook() {
        User selectedUser = userComboBox.getSelectionModel().getSelectedItem();
        Book selectedBook = bookComboBox.getSelectionModel().getSelectedItem();

        if (selectedUser == null || selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please select a user and a book.");
            return;
        }

        boolean success = loanDAO.lendBook(selectedUser, selectedBook);
        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book '" + selectedBook.getTitle() + "' loaned to " + selectedUser.getName() + ".");
            refreshAll();
        } else {
            showAlert(Alert.AlertType.ERROR, "Loan Error", "Could not loan the book. No copies available.");
        }
    }

    @FXML
    private void handleReturnBook() {
        Loan selectedLoan = loanTable.getSelectionModel().getSelectedItem();

        if (selectedLoan == null) {
            showAlert(Alert.AlertType.WARNING, "Selection Error", "Please select a loan from the table to return.");
            return;
        }

        loanDAO.returnBook(selectedLoan);
        showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully.");
        refreshAll();
    }
    
    private void refreshAll(){
        refreshLoanTable();
        loadAvailableBooksIntoComboBox();
        userComboBox.getSelectionModel().clearSelection();
        bookComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
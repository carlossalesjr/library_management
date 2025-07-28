package com.work.books.controller;

import com.work.books.dao.BookDAO;
import com.work.books.model.Book;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class BookManagementController {
    
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Long> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, Integer> publishedYearColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, Integer> availableCopiesColumn;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField publishedYearField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField availableCopiesField;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button clearButton; 
    @FXML
    private Label formHeaderLabel;

    private BookDAO bookDAO = new BookDAO();
    private Book selectedBook; 

    @FXML
     public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publishedYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
        
        bookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> populateForm(newValue)
        );
        refreshTable();
     }

     @FXML
     private void handleSave() {
         String title = titleField.getText();
         String author = authorField.getText();
         int publishedYear = 0;

         if (title.trim().isEmpty() || author.trim().isEmpty() || publishedYearField.getText().trim().isEmpty()) {
             showAlert("Validation Error", "Title, Author, and Published Year fields cannot be empty.");
             return;
         }

         if (selectedBook == null) {
             String isbn = isbnField.getText();
             int availableCopies = 0;
             try {
                 publishedYear = Integer.parseInt(publishedYearField.getText());
             } catch (NumberFormatException e) {
                 showAlert("Validation Error", "Published Year must be a number.");
                 return;
             }
             try {
                 availableCopies = Integer.parseInt(availableCopiesField.getText());
             } catch (NumberFormatException e) {
                 showAlert("Validation Error", "Available Copies must be a number.");
                 return;
             }
             Book newBook = new Book(title, author, isbn, publishedYear, availableCopies);
             bookDAO.save(newBook);
         } else {
             selectedBook.setTitle(title);
             selectedBook.setAuthor(author);
             selectedBook.setIsbn(isbnField.getText());
             selectedBook.setPublishedYear(Integer.parseInt(publishedYearField.getText()));
             selectedBook.setAvailableCopies(Integer.parseInt(availableCopiesField.getText()));
             bookDAO.save(selectedBook);
         }
         refreshTable();
         clearForm();
        }

     @FXML
     private void handleDelete() {
         if (selectedBook != null) {
             bookDAO.delete(selectedBook);
             refreshTable();
             clearForm();
         } else {
             showAlert("Selection Error", "No book selected for deletion.");
         }
     }

     @FXML
     private void handleClear() {
         clearForm();
     }

     private void populateForm(Book book) {
         if (book != null) {
             selectedBook = book;
             titleField.setText(book.getTitle());
             authorField.setText(book.getAuthor());
             publishedYearField.setText(String.valueOf(book.getPublishedYear()));
             isbnField.setText(book.getIsbn());
             availableCopiesField.setText(String.valueOf(book.getAvailableCopies()));
         } else {
             clearForm();
         }
    }

    private void clearForm() {
         selectedBook = null;
         titleField.clear();
         authorField.clear();
         publishedYearField.clear();
         isbnField.clear();
         availableCopiesField.clear();
    }

    private void refreshTable() {
         List<Book> books = bookDAO.findAll();
         bookTable.setItems(FXCollections.observableArrayList(books));
         bookTable.refresh();
    }

    private void showAlert(String title, String message) {
         Alert alert = new Alert(AlertType.WARNING);
         alert.setTitle(title);
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.showAndWait();
     }
}
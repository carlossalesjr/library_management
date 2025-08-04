package com.work.books.controller;

import com.work.api.controller.GenericController;
import com.work.api.dao.BookDAO;
import com.work.api.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BookManagementController extends GenericController<Book> {

    @FXML private TableView<Book> bookTable;
    @FXML private TableColumn<Book, Long> idColumn;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, Integer> publishedYearColumn;
    @FXML private TableColumn<Book, Integer> availableCopiesColumn;
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private TextField isbnField;
    @FXML private TextField publishedYearField;
    @FXML private TextField availableCopiesField;
    @FXML private Label formHeaderLabel;

    public BookManagementController() {
        this.dao = new BookDAO();
    }

    @Override
    protected void configureTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        publishedYearColumn.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        availableCopiesColumn.setCellValueFactory(new PropertyValueFactory<>("availableCopies"));
    }

    @Override
    protected void populateForm(Book book) {
        if (book != null) {
            formHeaderLabel.setText("Edit Book");
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            isbnField.setText(book.getIsbn());
            publishedYearField.setText(String.valueOf(book.getPublishedYear()));
            availableCopiesField.setText(String.valueOf(book.getAvailableCopies()));
        }
    }

    @Override
    protected void clearForm() {
        getTableView().getSelectionModel().clearSelection();
        selectedEntity = null;
        formHeaderLabel.setText("Add New Book");
        titleField.clear();
        authorField.clear();
        isbnField.clear();
        publishedYearField.clear();
        availableCopiesField.clear();
    }

    @Override
    protected Book getEntityFromForm() {
        String title = titleField.getText();
        String author = authorField.getText();

        if (title.trim().isEmpty() || author.trim().isEmpty()) {
            showAlert("Validation Error", "Title and Author fields cannot be empty.");
            return null;
        }

        try {
            int publishedYear = Integer.parseInt(publishedYearField.getText());
            int availableCopies = Integer.parseInt(availableCopiesField.getText());
            String isbn = isbnField.getText();

            Book book = (selectedEntity == null) ? new Book() : selectedEntity;
            book.setTitle(title);
            book.setAuthor(author);
            book.setIsbn(isbn);
            book.setPublishedYear(publishedYear);
            book.setAvailableCopies(availableCopies);
            return book;
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Published Year and Available Copies must be valid numbers.");
            return null;
        }
    }

    @Override
    protected void setEntityToForm(Book entity) {
        // Not used 
    }

    @Override
    protected TableView<Book> getTableView() {
        return bookTable;
    }

    @Override
    protected Long getEntityId(Book entity) {
        return entity.getBookId();
    }
}
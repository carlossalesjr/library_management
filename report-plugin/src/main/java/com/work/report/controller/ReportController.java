package com.work.report.controller;

import com.work.api.dao.LoanDAO;
import com.work.api.model.Loan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;

public class ReportController {

    @FXML
    private TableView<Loan> reportTable;
    @FXML
    private TableColumn<Loan, String> loanBookTitleColumn;
    @FXML
    private TableColumn<Loan, String> loanBookAuthorColumn;
    @FXML
    private TableColumn<Loan, String> loanUserNameColumn;
    @FXML
    private TableColumn<Loan, LocalDateTime> loanDateColumn;
    @FXML
    private TableColumn<Loan, String> statusColumn;
    @FXML
    private TextField searchField;

    private LoanDAO loanDAO;
    private ObservableList<Loan> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        this.loanDAO = new LoanDAO();
        
        setupTableColumns();
        loadDataAndSetupFiltering();
    }
    
    private void setupTableColumns() {
        loanBookTitleColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getBook().getTitle())
        );
        loanBookAuthorColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getBook().getAuthor())
        );
        loanUserNameColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getUser().getName())
        );
        loanDateColumn.setCellValueFactory(
            cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getLoanDate())
        );
        statusColumn.setCellValueFactory(
            cellData -> new SimpleStringProperty(cellData.getValue().getReturnDate() != null ? "Returned" : "Active")
        );

        reportTable.setItems(FXCollections.observableArrayList(loanDAO.findAll()));
    }
    private void loadDataAndSetupFiltering() {
        masterData.setAll(loanDAO.findAll());

        FilteredList<Loan> filteredData = new FilteredList<>(masterData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(loan -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (loan.getBook().getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (loan.getBook().getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (loan.getUser().getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        reportTable.setItems(filteredData);
    }
}
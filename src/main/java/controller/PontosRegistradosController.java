package controller;

import dao.RegistroPontoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RegistroPonto;

import java.sql.SQLException;

public class PontosRegistradosController {
    @FXML
    private TableView tabelaPontos;
    @FXML
    private TableColumn<RegistroPonto, String> columnCodigo;
    @FXML
    private TableColumn<RegistroPonto, String> columnCPF;
    @FXML
    private TableColumn<RegistroPonto, String> columnInicioExpediente;
    @FXML
    private TableColumn<RegistroPonto, String> columnInicioIntervalo;
    @FXML
    private TableColumn<RegistroPonto, String> columnFimIntervalo;
    @FXML
    private TableColumn<RegistroPonto, String> columnFimExpediente;

    private ObservableList<RegistroPonto> pontosList() throws SQLException {
        return FXCollections.observableArrayList(RegistroPontoDAO.listAll());
    }

    private void loadTable() throws SQLException {
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("cpfColaborador"));
        columnInicioExpediente.setCellValueFactory(new PropertyValueFactory<>("inicioExpediente"));
        columnInicioIntervalo.setCellValueFactory(new PropertyValueFactory<>("inicioIntervalo"));
        columnFimIntervalo.setCellValueFactory(new PropertyValueFactory<>("fimIntervalo"));
        columnFimExpediente.setCellValueFactory(new PropertyValueFactory<>("fimExpediente"));
        tabelaPontos.setItems(pontosList());
    }

    @FXML
    public void initialize() throws SQLException {
        loadTable();
    }
}

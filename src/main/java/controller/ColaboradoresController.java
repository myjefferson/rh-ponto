package controller;

import com.sun.tools.javac.Main;
import dao.ColaboradorDAO;
import interactions.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Colaborador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class ColaboradoresController extends MainPainelController {
    @FXML
    private TableView tabelaColaboradores;
    @FXML
    private TableColumn<Colaborador, String> columnCodigo;
    @FXML
    private TableColumn<Colaborador, String> columnNome;
    @FXML
    private TableColumn<Colaborador, Integer> columnCPF;
    @FXML
    private TableColumn<Colaborador, String> columnTelefone;
    @FXML
    private TableColumn<Colaborador, String> columnCargo;
    @FXML
    private TableColumn<Colaborador, String> columnEmail;
    @FXML
    private TableColumn<Colaborador, String> columnDataNascimento;
    @FXML
    private Pane paneEditar;
    @FXML
    private TextField textNome;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textCargo;
    @FXML
    private TextField textCPF;
    @FXML
    private DatePicker dataNascimento;
    @FXML
    private Button btnRemover;
    @FXML
    private Button btnAlterar;
    @FXML
    private TextField textTelefone;
    @FXML
    private ToggleGroup isAdmin;
    @FXML
    private RadioButton colaborador;
    @FXML
    private RadioButton admin;

    private ObservableList<Colaborador> colaboradoresList() throws SQLException {
        return FXCollections.observableArrayList(ColaboradorDAO.listAll());
    }

    private void loadTable() throws SQLException {
        columnCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        columnCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tabelaColaboradores.setItems(colaboradoresList());
    }

    public void deleteColaborador() throws SQLException {
        Alert alertConfirm = Alerts.alertConfirm();
        alertConfirm.setAlertType(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Tem certeza que deseja excluir?");
        alertConfirm.setContentText("Você vai excluir um colaborador, têm certeza?");

        Optional<ButtonType> result = alertConfirm.showAndWait();
        if(result.get() == ButtonType.OK){
            int index = tabelaColaboradores.getSelectionModel().getSelectedIndex();
            Colaborador colaboradores = (Colaborador)tabelaColaboradores.getItems().get(index);
            ColaboradorDAO.delete(colaboradores.getCodigo());

            tabelaColaboradores.getItems().removeAll(
                tabelaColaboradores.getSelectionModel().getSelectedItems()
            );

            Alerts.alertInfo("Colaborador removido com sucesso!");
        }
    }

    public void showEditarColaborador(){
        readButtonsUpdateRemove();

        paneEditar.setVisible(true);
        int index = tabelaColaboradores.getSelectionModel().getSelectedIndex();
        Colaborador colaboradores = (Colaborador)tabelaColaboradores.getItems().get(index);
        textNome.setText(colaboradores.getNome());
        textCPF.setText(colaboradores.getCpf());
        textTelefone.setText(colaboradores.getTelefone());
        textEmail.setText(colaboradores.getEmail());
        textCargo.setText(colaboradores.getCargo());
        if(colaboradores.getIsAdmin().equals("S")){
            admin.setSelected(true);
            colaborador.setSelected(false);
        } else {
            admin.setSelected(false);
            colaborador.setSelected(true);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dataNascimento.setValue(LocalDate.parse(colaboradores.getDataNascimento(), formatter));
    }

    public void hideEditarColaborador(){
        paneEditar.setVisible(false);
        textNome.setText("");
        textCPF.setText("");
        textTelefone.setText("");
        textEmail.setText("");
        textCargo.setText("");
        dataNascimento.getEditor().clear();
    }

    public String isColaboradorAdmin(){
        RadioButton radio = (RadioButton) isAdmin.getSelectedToggle();
        if(radio.getId() != null){
            return "S";
        }else{
            return "N";
        }
    }

    public void alterarColaborador(){
        try{
            String nascimento = dataNascimento.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int index = tabelaColaboradores.getSelectionModel().getSelectedIndex();
            Colaborador colaboradores = (Colaborador)tabelaColaboradores.getItems().get(index);

            ColaboradorDAO.update(
                    colaboradores.getCodigo(),
                    textNome.getText(),
                    textCPF.getText(),
                    textTelefone.getText(),
                    textEmail.getText(),
                    textCargo.getText(),
                    nascimento,
                    isColaboradorAdmin()
            );
            Alerts.alertInfo("Usuário alterado com sucesso!");
            hideEditarColaborador();
        }catch (Exception e) {
            Alerts.alertError("Por favor, preencha todos os campos");
        }
    }

    public void noReadButtonsUpdateRemove(){
        btnRemover.setDisable(false);
        btnAlterar.setDisable(false);
    }

    public void readButtonsUpdateRemove(){
        btnRemover.setDisable(true);
        btnAlterar.setDisable(true);
    }

    @FXML
    public void initialize() throws SQLException {
        loadTable();
    }
}



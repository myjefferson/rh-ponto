package controller;

import dao.ColaboradorDAO;
import interactions.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CadastrarColaboradorController{
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
    private TextField textTelefone;
    @FXML
    private ToggleGroup isAdmin;


    public String isColaboradorAdmin(){
        RadioButton radio = (RadioButton) isAdmin.getSelectedToggle();
        if(radio.getId() != null){
            return "S";
        }else{
            return "N";
        }
    }
    public void cadastrarColaborador(){
        try{
            String nascimento = dataNascimento.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            ColaboradorDAO.create(
                    textNome.getText(),
                    textCPF.getText(),
                    textTelefone.getText(),
                    textEmail.getText(),
                    textCargo.getText(),
                    nascimento,
                    isColaboradorAdmin()
            );
            Alerts.alertInfo("Usuário Cadastrado");
            //switchSceneMainPonto(new ActionEvent());
        }catch (Exception e) {
            Alerts.alertError("Por favor, preencha todos os campos");
        }
    }
}

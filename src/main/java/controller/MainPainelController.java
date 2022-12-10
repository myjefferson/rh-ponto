package controller;

import dao.ColaboradorDAO;
import dao.RegistroPontoDAO;
import interactions.Alerts;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Colaborador;
import model.RegistroPonto;

import javax.sound.midi.SysexMessage;
import java.io.IOException;

import java.sql.Array;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainPainelController {
    private Stage stage;
    private Scene scene;

    @FXML
    private Label hourLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField textFieldCPF;
    @FXML
    private TextField textPassword;
    @FXML
    private TextField textFieldInicioExpediente;
    @FXML
    private TextField textFieldInicioIntervalo;
    @FXML
    private TextField textFieldFimIntervalo;
    @FXML
    private TextField textFieldFimExpediente;


    public void alterarColaboradoresView(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/AlterarColaborador.fxml"));
        borderPane.setCenter(view);
    }

    public void fieldsCleanAllPontosRegistrados(){
        textFieldInicioExpediente.setText("");
        textFieldInicioIntervalo.setText("");
        textFieldFimIntervalo.setText("");
        textFieldFimExpediente.setText("");
    }

    public void fieldsPontosRegistrados(String inicioExpediente, String inicioIntervalo, String fimIntervalo, String fimExpediente){
        fieldsCleanAllPontosRegistrados();
        textFieldInicioExpediente.setText(inicioExpediente);
        textFieldInicioIntervalo.setText(inicioIntervalo);
        textFieldFimIntervalo.setText(fimIntervalo);
        textFieldFimExpediente.setText(fimExpediente);
    }

    public void confirmarPonto() throws SQLException {
        String cpf = textFieldCPF.getText();
        List findOneRegistroPonto = RegistroPontoDAO.findOneRegistroPonto(cpf, currentDate());

//        try{
            if(findOneRegistroPonto != null) {
                int countResgistered = 0;
                for (int countPontos = 0; countPontos < findOneRegistroPonto.size(); countPontos++) {

                    if(countPontos < 4 && findOneRegistroPonto.get(countPontos) != null){
                        countResgistered++;
                    }

                    if (countResgistered == 4) {
                        Alerts.alertInfo("Você concluiu o expediente. Até o outro dia!");
                        fieldsPontosRegistrados(
                                (String) findOneRegistroPonto.get(0),
                                (String) findOneRegistroPonto.get(1),
                                (String) findOneRegistroPonto.get(2),
                                (String) findOneRegistroPonto.get(3)
                        );
                        return;
                    }
                }
            }

            if(ColaboradorDAO.findOneColaborador(cpf) != null){
                if(findOneRegistroPonto != null){
                    Alerts.alertInfo("Ponto registrado");
                    if(findOneRegistroPonto.get(1) == null){ //Início Intervalo
                        RegistroPontoDAO.updateRegistroPonto("inicio_intervalo", cpf, findOneRegistroPonto.get(4).toString());
                        fieldsPontosRegistrados(findOneRegistroPonto.get(0).toString(), currentDate(), "", "");
                        return;
                    }
                    if(findOneRegistroPonto.get(2) == null){ //Fim Intervalo
                        RegistroPontoDAO.updateRegistroPonto("fim_intervalo", cpf, findOneRegistroPonto.get(4).toString());
                        fieldsPontosRegistrados(findOneRegistroPonto.get(0).toString(), findOneRegistroPonto.get(1).toString(), currentDate(), "");
                        return;
                    }
                    if(findOneRegistroPonto.get(3) == null) { //Fim Expediente
                        RegistroPontoDAO.updateRegistroPonto("fim_expediente", cpf, findOneRegistroPonto.get(4).toString());
                        fieldsPontosRegistrados(findOneRegistroPonto.get(0).toString(), findOneRegistroPonto.get(1).toString(), findOneRegistroPonto.get(2).toString(), currentDate());
                        return;
                    }
                }else{
                    Alerts.alertInfo("Ponto registrado");
                    RegistroPontoDAO.insertRegistroPonto(cpf);
                    fieldsPontosRegistrados(currentDate(),"", "", "");
                }
            }else {
                Alerts.alertError("Colaborador não encontrado");
            }
//        }catch (Exception e){
//            Alerts.alertError("Colaborador não encontrado.");
//        }
    }
    public void switchViewMainPonto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainPonto.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void colaboradoresView(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/Colaboradores.fxml"));
        borderPane.setCenter(view);
    }
    public void cadastroColaboradoresView(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/CadastroColaborador.fxml"));
        borderPane.setCenter(view);
    }

    public void pontosRegistrandosView(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/view/PontosRegistrados.fxml"));
        borderPane.setCenter(view);
    }

    public static Colaborador adminLogado;
    public void loginAdmin(ActionEvent event){
        try {
            List<Colaborador> colaboradorList = ColaboradorDAO.listAll();
//            System.out.println("Total de usu�rios na lista: " + colaboradorList.size());
            //Validar login e carregar tela
            String login = textFieldCPF.getText();
            String password = textPassword.getText();

            if(login.replaceAll(" ", "").isEmpty() || password.replaceAll(" ", "").isEmpty()){
               Alerts.alertError("Por favor, preencha os campos.");
               return;
            }

            boolean status = false;
            //percorrer a lista e achar o login
            for(Colaborador c : colaboradorList){
                if(login.equals(c.getCpf()) && password.equals(c.getSenha()) && c.getIsAdmin().equals("S")){
                    status = true;
                    adminLogado = c;

                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainPainel.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Painel RH Ponto");
                    stage.show();
                    break;
                }else{
                    status = false;
                }
            }
            if(!status){
                Alerts.alertError("Login de administrador incorreto!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchViewLoginAdmin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginAdmin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() throws SQLException {
        saldacaoDay();
        runHoursPainel();
        dateLabel.setText(currentDate());
    }
    public void runHoursPainel(){
        Thread timerThread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000); //1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String hours = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    hourLabel.setText(hours);
                });
            }
        });   timerThread.start();
    }
    public String currentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    @FXML
    private Label saudacaoLabel;
    public void saldacaoDay(){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
            int hour = Integer.parseInt(simpleDateFormat.format(new Date()));
            if(hour <= 11)
                saudacaoLabel.setText("Bom dia");
            if(hour >= 12 && hour <= 18)
                saudacaoLabel.setText("Boa tarde");
            if(hour >= 19)
                saudacaoLabel.setText("Boa noite");
        }catch (Exception e){}
    }
}

package dao;

import database.DbConnection;
import model.RegistroPonto;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegistroPontoDAO {
    public static List<RegistroPonto> listAll() throws SQLException {
        Connection connection = DbConnection.getConnectionSqlite();
        Statement stmt;
        ResultSet rst;
        stmt = connection.createStatement();
        rst = stmt.executeQuery("select * from registros_ponto");
        ArrayList<RegistroPonto> listPontos = new ArrayList<RegistroPonto>();
        //Pegar os itens do resultset e inserir na lista
        while(rst.next()){
            RegistroPonto rp = new RegistroPonto();
            rp.setCodigo(rst.getInt("codigo"));
            rp.setCpfColaborador(rst.getString("cpf_colaborador"));
            rp.setInicioExpediente(rst.getString("inicio_expediente"));
            rp.setInicioIntervalo(rst.getString("inicio_intervalo"));
            rp.setFimIntervalo(rst.getString("fim_intervalo"));
            rp.setFimExpediente(rst.getString("fim_expediente"));
            listPontos.add(rp);
        }
        rst.close();
        connection.close();
        return listPontos;
    }

    public static List findOneRegistroPonto(String CpfColaborador, String DtCadadtro){
        try {
            Connection connection = DbConnection.getConnectionSqlite();
            Statement stmt;
            List result = new ArrayList();
            stmt = connection.createStatement();
            ResultSet query = stmt.executeQuery("SELECT * FROM registros_ponto WHERE cpf_colaborador = '" + CpfColaborador + "' AND dt_cadastro = '" + DtCadadtro +"'");
            result.add(query.getString("inicio_expediente"));
            result.add(query.getString("inicio_intervalo"));
            result.add(query.getString("fim_intervalo"));
            result.add(query.getString("fim_expediente"));
            result.add(query.getString("dt_cadastro"));
            query.close();
            connection.close();
            return result;
        }catch(Exception e){
            return null;
        }
    }

    public static List<RegistroPonto> insertRegistroPonto(String CpfColaborador) throws SQLException {
        Date dataHoraAtual = new Date();
        String registroDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataHoraAtual);
        String registroData = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);

        Connection connection = DbConnection.getConnectionSqlite();
        String save = "INSERT INTO registros_ponto (cpf_colaborador, inicio_expediente, dt_cadastro) VALUES(?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(save);
        pstmt.setString(1, CpfColaborador);
        pstmt.setString(2, registroDataHora);
        pstmt.setString(3, registroData);
        pstmt.execute();
        connection.close();
        return null;
    }

    public static List<RegistroPonto> updateRegistroPonto(String Column, String CpfColaborador, String DtCadadtro) throws SQLException{
        Date dataHoraAtual = new Date();
        String registroDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataHoraAtual);

        Connection connection = DbConnection.getConnectionSqlite();
        String update = "UPDATE registros_ponto SET " + Column + " = '"+ registroDataHora +"' WHERE cpf_colaborador = '" + CpfColaborador + "' AND dt_Cadastro = '" + DtCadadtro + "'";
        PreparedStatement pstmt = connection.prepareStatement(update);
        pstmt.execute();
        connection.close();
        return null;
    }
}

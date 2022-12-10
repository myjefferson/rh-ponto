package dao;

import database.DbConnection;
import model.Colaborador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {
    public static void create(String Nome, String CPF, String Telefone, String Email, String Cargo, String dataNascimento, String isAdmin) throws SQLException {
        Connection connection = DbConnection.getConnectionSqlite();
        String save = "INSERT INTO colaboradores (nome, cpf, telefone, email, cargo, dataNascimento, admin) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(save);
        pstmt.setString(1, Nome);
        pstmt.setString(2, CPF);
        pstmt.setString(3, Telefone);
        pstmt.setString(4, Email);
        pstmt.setString(5, Cargo);
        pstmt.setString(6, dataNascimento);
        pstmt.setString(7, isAdmin);
        pstmt.execute();
        connection.close();
    }

    public static void update(int codigo, String Nome, String CPF, String Telefone, String Email, String Cargo, String dataNascimento, String isAdmin) throws SQLException {
        Connection connection = DbConnection.getConnectionSqlite();
        String save = "UPDATE colaboradores SET nome = ?, cpf = ?, telefone = ?, email = ?, cargo = ?, dataNascimento = ?, admin = ? WHERE codigo = ?";
        PreparedStatement pstmt = connection.prepareStatement(save);
        pstmt.setString(1, Nome);
        pstmt.setString(2, CPF);
        pstmt.setString(3, Telefone);
        pstmt.setString(4, Email);
        pstmt.setString(5, Cargo);
        pstmt.setString(6, dataNascimento);
        pstmt.setString(7, isAdmin);
        pstmt.setInt(8, codigo);
        pstmt.execute();
        connection.close();
    }

    public static void delete(int Codigo) throws SQLException{
        Connection connection = DbConnection.getConnectionSqlite();
        String save = "DELETE FROM colaboradores WHERE codigo = ?";
        PreparedStatement pstmt = connection.prepareStatement(save);
        pstmt.setInt(1, Codigo);
        pstmt.execute();
        connection.close();
    }

    public static List<Colaborador> listAll() throws SQLException {
        Connection connection = DbConnection.getConnectionSqlite();
        Statement stmt;
        ResultSet rst;
        stmt = connection.createStatement();
        rst = stmt.executeQuery("select * from colaboradores");
        ArrayList<Colaborador> listColaboradores = new ArrayList<Colaborador>();
        //Pegar os itens do resultset e inserir na lista
        while(rst.next()){
            Colaborador c = new Colaborador();
            c.setCodigo(rst.getInt("codigo"));
            c.setNome(rst.getString("nome"));
            c.setCpf(rst.getString("cpf"));
            c.setTelefone(rst.getString("telefone"));
            c.setEmail(rst.getString("email"));
            c.setCargo(rst.getString("cargo"));
            c.setDataNascimento(rst.getString("dataNascimento"));
            c.setIsAdmin(rst.getString("admin"));
            c.setSenha(rst.getString("senha"));
            listColaboradores.add(c);
        }
        rst.close();
        connection.close();
        return listColaboradores;
    }

    public static List<Colaborador> findOneColaborador(String CpfColaborador){
        try{
            Connection connection = DbConnection.getConnectionSqlite();
            List result = new ArrayList();
            Statement stmt = connection.createStatement();
            ResultSet query = stmt.executeQuery("SELECT * FROM colaboradores WHERE cpf = " + CpfColaborador);
            result.add(query.getInt("codigo"));
            result.add(query.getString("nome"));
            result.add(query.getString("cpf"));
            result.add(query.getString("telefone"));
            result.add(query.getString("email"));
            result.add(query.getString("cargo"));
            result.add(query.getString("dataNascimento"));
            query.close();
            connection.close();
            return result;
        }catch (Exception e){
            return null;
        }
    }
}

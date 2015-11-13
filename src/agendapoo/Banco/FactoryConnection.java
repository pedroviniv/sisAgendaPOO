/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por se conectar com o banco de dados postgresql.
 * @author kieckegard
 */
public class FactoryConnection
{
    private final static String url="jdbc:postgresql://localhost:5432/AgendaPOO2";
    private final static String username="postgres";
    private final static String password="123456";
    private static Connection conn;
    
    /**
     * Método estático responsável por retornar uma conexão contendo a conexão com o banco
     * de dados postgresql.
     * @return - Instância de Connection
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException{
        if(conn == null)
            conn = DriverManager.getConnection(url,username,password);
        return conn;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kieckegard
 */
public class DAOConnection
{
    private final static String url="jdbc:postgresql://localhost:5432/AgendaPOO";
    private final static String username="postgres";
    private final static String password="123456";
    private static Connection conn;
    
    public static Connection getConnection() throws SQLException{
        if(conn == null)
            conn = DriverManager.getConnection(url,username,password);
        return conn;
    }
}

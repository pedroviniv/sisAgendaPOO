/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author kieckegard
 */
public class DAOConnection
{
    private static final String user="postgres";
    private static final String senha="123456";
    private static final String url="jdbc:postgresql://localhost:5432/sisAgendaPOO";
    private static Connection conn;
    
    public static Connection getConnection() throws SQLException{
        if(conn == null)
            conn = DriverManager.getConnection(url, user, senha);
        return conn;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sisagendapoo.Model.Usuario;

/**
 *
 * @author kieckegard
 */
public class UsuarioDAO implements DAO<Usuario>
{
    private PreparedStatement pstm;
    
    @Override
    public void add(Usuario u) throws SQLException{
        String sql = "INSERT INTO USUARIO VALUES(?,?,?,?,?)";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(2, u.getNome());
        pstm.setString(1, u.getEmail());
        pstm.setString(3, u.getPassword());
        pstm.setString(4, u.getTelefone());
        pstm.setDate(5, java.sql.Date.valueOf(u.getDataNascimento()));
        pstm.executeUpdate();
    }
    
    @Override
    public void remove(Usuario u) throws SQLException{
        String sql = "DELETE FROM USUARIO u WHERE u.email=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1, u.getEmail());
        pstm.executeUpdate();
    }
    
    @Override
    public void update(Usuario u) throws SQLException
    {
        
    }
    
    @Override
    public List<Usuario> list() throws SQLException{
        List<Usuario> list = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String password = rs.getString("senha");
            String telefone = rs.getString("telefone");
            LocalDate nascimento = rs.getDate("dataNascimento").toLocalDate();
            Usuario u = new Usuario(nome,email,password,telefone,nascimento);
            list.add(u);
        }
        return list;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO.JDBC;

import agendapoo.DAO.DAO;
import agendapoo.DAO.IAtividadeDAO;
import agendapoo.Model.Usuario;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class UsuarioDAOjdbc implements DAO<Usuario>
{

    private PreparedStatement pstm;
    
    @Override
    public void add(Usuario obj) throws SQLException, IOException, ClassNotFoundException
    {
        String sql = "INSERT INTO USUARIO VALUES(?,?,?,?,?)";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1, obj.getEmail());
        pstm.setString(2, obj.getSenha());
        pstm.setString(3, obj.getNome());
        pstm.setDate(4, java.sql.Date.valueOf(obj.getDataNascimento()));
        pstm.setString(5, obj.getTelefone());
        pstm.executeUpdate();
    }

    @Override
    public void delete(Usuario obj) throws SQLException, IOException, ClassNotFoundException
    {
        //Instanciate atividadeDAO
        IAtividadeDAO atividadeDAO = new AtividadeDAOjdbc();
        //which will remove all the User's Activities.
        atividadeDAO.deleteByUser(obj);
        
        //And THEN will remove the current User. :)
        String sql = "DELETE FROM USUARIO WHERE email=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1, obj.getEmail());
        pstm.executeUpdate();
    }

    @Override
    public void update(Usuario obj) throws SQLException, IOException, ClassNotFoundException
    {
        String sql = "UPDATE USUARIO SET senha=?,dataNascimento=?,telefone=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1, obj.getSenha());
        pstm.setDate(2, java.sql.Date.valueOf(obj.getDataNascimento()));
        pstm.setString(3, obj.getTelefone());
        pstm.executeUpdate();
    }

    @Override
    public List<Usuario> list() throws SQLException, IOException, ClassNotFoundException
    {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            String senha = rs.getString("senha");
            String telefone = rs.getString("telefone");
            LocalDate dataNascimento = rs.getDate("dataNascimento").toLocalDate();
            Usuario u = new Usuario(nome,email,senha,telefone,dataNascimento);
            usuarios.add(u);
        }
        rs.close();
        pstm.close();
        return usuarios;
    }
    
}

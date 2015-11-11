/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO.JDBC;

import agendapoo.Banco.FactoryConnection;
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
 * Classe responsável pela manipulação de Usuários no banco de dados utilizando jdbc.
 * @author kieckegard
 */
public class UsuarioDAOjdbc implements DAO<Usuario>
{

    private PreparedStatement pstm;
    
    /**
     * Método responsável por persistir um determinado usuário no banco de dados.
     * @param usuario - instância de usuário
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void add(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        String sql = "INSERT INTO USUARIO VALUES(?,?,?,?,?)";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, usuario.getEmail());
        pstm.setString(2, usuario.getSenha());
        pstm.setString(3, usuario.getNome());
        pstm.setDate(4, java.sql.Date.valueOf(usuario.getDataNascimento()));
        pstm.setString(5, usuario.getTelefone());
        pstm.executeUpdate();
    }
    
    /**
     * Método responsável por deletar um usuário no banco de dados.
     * @param usuario - instância de Usuario
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void delete(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        //Instanciate atividadeDAO
        IAtividadeDAO atividadeDAO = new AtividadeDAOjdbc();
        //which will remove all the User's Activities.
        atividadeDAO.deleteByUser(usuario);
        
        //And THEN will remove the current User. :)
        String sql = "DELETE FROM USUARIO WHERE email=?";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, usuario.getEmail());
        pstm.executeUpdate();
    }

    /**
     * Método responsável por atualizar os dados de um usuário no banco de dados.
     * A instância de usuário passada por parâmetro já deve estar atualizada com
     * os novos dados.
     * @param usuario - instância de Usuario já com os dados atualizados.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void update(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        String sql = "UPDATE USUARIO SET senha=?,dataNascimento=?,telefone=?";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, usuario.getSenha());
        pstm.setDate(2, java.sql.Date.valueOf(usuario.getDataNascimento()));
        pstm.setString(3, usuario.getTelefone());
        pstm.executeUpdate();
    }

    /**
     * Método responsável por pegar do arquivo uma Lista contendo todos os usuários cadastrados no sistema.
     * @return List de Usuário com todos os usuário salvos no arquivo.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public List<Usuario> list() throws SQLException, IOException, ClassNotFoundException
    {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
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

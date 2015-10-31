/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Control;

import agendapoo.DAO.ARQUIVO.UsuarioDAOarquivo;
import agendapoo.DAO.DAO;
import agendapoo.DAO.JDBC.UsuarioDAOjdbc;
import agendapoo.Exceptions.EmailJaCadastradoException;
import agendapoo.Exceptions.InvalidTimeRangeException;
import agendapoo.Exceptions.TimeInterferenceException;
import agendapoo.Exceptions.UsuarioNaoExistenteException;
import agendapoo.Model.Atividade;
import agendapoo.Model.TipoAtividade;
import agendapoo.Model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author kieckegard
 */
public class ControlUsuario implements Login
{
    private final DAO<Usuario> usuarioDAO;
    private final AtividadeController controlAtividade;
    
    public ControlUsuario(){
        controlAtividade = new ControlAtividade();
        usuarioDAO = new UsuarioDAOjdbc();
    }
    
    public boolean isEmailValid(String email) throws SQLException, IOException, ClassNotFoundException{
        for(Usuario u : usuarioDAO.list()){
            if(u.getEmail().equals(email))
                return false;
        }return true;
    }

    public void cadastraUsuario(String nome, String email, String senha, String telefone, String dataNascimento) throws SQLException, IOException, ClassNotFoundException, DateTimeParseException, EmailJaCadastradoException
    {
        if(isEmailValid(email)){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Usuario u = new Usuario(nome,email,senha,telefone,LocalDate.parse(dataNascimento, dtf));
            usuarioDAO.add(u);
            
        }else throw new EmailJaCadastradoException("Já existe um usuário usando esse email.\nPor favor cadastra-se usando um e-mail diferente!");
    }
    
    public void removeUsuario(Usuario usuario) throws SQLException, IOException, ClassNotFoundException{
        usuarioDAO.delete(usuario);
    }
    
    public void cadastraAtividade(String descricao, String local, String data, String horaInicio, String horaFim, List<String> convidados, TipoAtividade tipo, Usuario u) throws SQLException, IOException, ClassNotFoundException, InvalidTimeRangeException, TimeInterferenceException, EmailException{
        controlAtividade.cadastraAtividade(descricao, local, data, horaInicio, horaFim, convidados, tipo, u);
    }
    
    public void removeAtividade(Atividade a) throws SQLException, IOException, ClassNotFoundException, EmailException{
        controlAtividade.deletaAtividade(a);
    }
    
    public void atualizaAtividade(Atividade a) throws SQLException, IOException, ClassNotFoundException, EmailException, TimeInterferenceException, InvalidTimeRangeException
    {
        controlAtividade.atualizaAtividade(a);
    }
    
    public List<Atividade> getAtividadesPorUsuario(Usuario u) throws SQLException, IOException, ClassNotFoundException
    {
        return controlAtividade.getAtividadesByUser(u);
    }
    
    @Override
    public Usuario autentica(String email, String senha) throws SQLException, IOException, ClassNotFoundException, UsuarioNaoExistenteException{
        List<Usuario> usuarios = usuarioDAO.list();
        boolean existeUsuario=false;
        for(Usuario u : usuarios){
            if(u.getEmail().equals(email)){
                existeUsuario=true;
                if(u.autentica(senha))
                    return u;
            }
        }
        if(!existeUsuario) throw new UsuarioNaoExistenteException("Não existe um usário com o email "+email+"...");
        return null;
    }
    
    public void atualizaUsuario(Usuario u) throws SQLException, IOException, ClassNotFoundException{
        usuarioDAO.update(u);
    }
}

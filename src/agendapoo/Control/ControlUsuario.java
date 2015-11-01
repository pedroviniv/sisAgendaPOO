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
 * Classe responsável por todas as funcionalidades que o usuário pode fazer.
 * usuarioDAO - Interface com os métodos add,delete,update e list para a manipulação de usuários no banco de dados
 * ou arquivo.
 * controlAtividade - Classe com todos os métodos para manipular atividades no banco de dados ou arquivo.
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
    
    
    /**
     * Método responsável por verificar se o e-mail passado por parâmetro ainda não existe, ou seja, se já não há 
     * algum usuário cadastrado com esse e-mail.
     * @param email - valor em String contendo o e-mail
     * @return True, caso não haja nenhum usuário cadastrado com o e-mail, False, caso exista.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    private boolean isEmailValid(String email) throws SQLException, IOException, ClassNotFoundException{
        for(Usuario u : usuarioDAO.list()){
            if(u.getEmail().equals(email))
                return false;
        }return true;
    }
    /**
     * Método responsável por realizar o cadastro do usuário no sistema, salvando-o no Banco de dados ou arquivo.
     * @param nome - String contendo o nome do usuário
     * @param email - String contendo o email do usuário
     * @param senha - String contendo a senha do usuário
     * @param telefone - String contendo o telefone do usuário
     * @param dataNascimento - String contendo a data de nascimento do usuário (no padrão brasileiro)
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws DateTimeParseException - é lançada caso exista erro na formatação da string da data de nascimento.
     * @throws EmailJaCadastradoException - é lançada caso já exista um usuário cadastrado com o e-mail passado por parâmetro.
     */
    public void cadastraUsuario(String nome, String email, String senha, String telefone, String dataNascimento) throws SQLException, IOException, ClassNotFoundException, DateTimeParseException, EmailJaCadastradoException
    {
        if(isEmailValid(email)){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Usuario u = new Usuario(nome,email,senha,telefone,LocalDate.parse(dataNascimento, dtf));
            usuarioDAO.add(u);
            
        }else throw new EmailJaCadastradoException("Já existe um usuário usando esse email.\nPor favor cadastra-se usando um e-mail diferente!");
    }
    
    /**
     * Método responsável por remover um usuário através de uma instância de objeto do usuário.
     * A verificação é feita através do e-mail do usuário, utilizando-o como chave primária.
     * @param usuario - Instancia de Usuário
     * @throws SQLException 
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void removeUsuario(Usuario usuario) throws SQLException, IOException, ClassNotFoundException{
        usuarioDAO.delete(usuario);
    }
    
    public void cadastraAtividade(String descricao, String local, String data, String horaInicio, String horaFim, List<String> convidados, TipoAtividade tipo, Usuario u) throws SQLException, IOException, ClassNotFoundException, InvalidTimeRangeException, TimeInterferenceException, EmailException{
        controlAtividade.cadastraAtividade(descricao, local, data, horaInicio, horaFim, convidados, tipo, u);
    }
    
    /**
     * Remove atividade do sistema através de uma instancia de atividade passada por parâmetro.
     * @param atividade - instancia de atividade
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws EmailException - é lançado caso haja problemas ao enviar um e-mail a lista de convidados.
     */
    public void removeAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, EmailException{
        controlAtividade.deletaAtividade(atividade);
    }
    
    /**
     * Atualiza as informações de atividade, as novas informações de atividade devem estar contidas numa instância 
     * de atividade.
     * @param atividade - instancia de atividade já alterada com as novas informações
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws EmailException - é lançada caso haja problema na hora de enviar emails para a lista de convidados
     * @throws TimeInterferenceException - é lançada caso a novo horário seja inválido, ou seja, haja uma interferência
     * de horários com outras atividades cadastradas.
     * @throws InvalidTimeRangeException - é lançada caso o horário inicial seja maior que o horário final.
     */
    public void atualizaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, EmailException, TimeInterferenceException, InvalidTimeRangeException
    {
        controlAtividade.atualizaAtividade(atividade);
    }
    
    /**
     * Método responsável por retornar todas as atividades cadastradas por o usuário passado por parâmetro.
     * @param usuario - instancia de usuario
     * @return - List contendo todas as atividades cadastradas pelo usuario
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public List<Atividade> getAtividadesPorUsuario(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        return controlAtividade.getAtividadesByUser(usuario);
    }
    
    /**
     * Método responsável pela credencial do usuário, verifica se existe um usuário com o e-mail passado por parâmetro e logo em seguida, 
     * caso realmente exista um usuário cadastrado com o e-mail, verifica se se a senha desse e-mail bate com a senha passada por parâmetro.
     * Caso o usuário não exista, o método lançará a exceção UsuarioNaoExistenteException, caso o usuário exista, porém a senha não bate com 
     * a senha passada por parâmetro, o método retornará um valor NULL.
     * @param email - valor em String do email do usuário
     * @param senha - valor em String da senha do usuário
     * @return - instancia de usuario caso haja usuario e login tenha sido realizado com sucesso, 
     * null caso haja uma instancia de usuário, porém a senha esteja errada.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws UsuarioNaoExistenteException - é lançada caso não haja nenhum usuário cadastrado com o e-mail passado
     * por parâmetro.
     */
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
    
    /**
     * Atualiza informações do usuário no banco de dados ou arquivo, os novos dados de usuários já devem
     * estar dentro da instância de usuário.
     * @param usuario - instancia de usuário já com as informações atualizadas.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public void atualizaUsuario(Usuario usuario) throws SQLException, IOException, ClassNotFoundException{
        usuarioDAO.update(usuario);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Control;

import agendapoo.Exceptions.UsuarioNaoExistenteException;
import agendapoo.Model.Usuario;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe responsável pelo Login do usuário no sistema.
 * login - Interface com o método de autenticação de Usuário
 * loggedUser - Usuario logado no sistema.
 * @author kieckegard
 */
public class ControlSistema
{
    private Usuario loggedUser;
    private Login login;
    
    public ControlSistema(){
        login = new ControlUsuario();
    }

    /**
     * Método responsável por autenticar e retornar um objeto de usuário de acordo com as credenciais informadas (email e senha).
     * @param email - String contendo o e-mail especificado
     * @param senha - String contendo a senha especificado
     * @return Objeto de Usuario, caso encontre um usuário e a senha passada por parâmetro seja referente ao usuário encontrado, Null caso 
     * encontre o usuário porém a senha não bata com a senha do usuário encontrado.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws UsuarioNaoExistenteException - Exceção lançada caso não encontre um usuário com o e-mail especificado.
     */
    public Usuario logIn(String email, String senha) throws SQLException, IOException, ClassNotFoundException, UsuarioNaoExistenteException
    {
        Usuario u = login.autentica(email, senha);
        this.loggedUser = u;
        return u;
    }
    
    /**
     * @return Usuario logado no sistema.
     */
    public Usuario getLoggedUser(){
        return loggedUser;
    }

   
    
}

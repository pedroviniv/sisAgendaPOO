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
 *
 * @author kieckegard
 */
public class ControlSistema
{
    private Usuario loggedUser;
    private Login login;
    
    public ControlSistema(){
        login = new ControlUsuario();
    }

    public Usuario logIn(String email, String senha) throws SQLException, IOException, ClassNotFoundException, UsuarioNaoExistenteException
    {
        Usuario u = login.autentica(email, senha);
        this.loggedUser = u;
        return u;
    }
    
    public Usuario getLoggedUser(){
        return loggedUser;
    }

   
    
}

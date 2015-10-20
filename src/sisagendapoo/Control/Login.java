/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Control;

import Exceptions.UsuarioNaoExistenteException;
import java.sql.SQLException;
import sisagendapoo.Model.Usuario;

/**
 *
 * @author kieckegard
 */
public interface Login
{
    Usuario logIn(String email, String senha) throws UsuarioNaoExistenteException, SQLException;
    Usuario getLoggedUser();
}

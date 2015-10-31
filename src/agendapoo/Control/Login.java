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
public interface Login
{
    Usuario autentica(String email, String senha) throws SQLException, ClassNotFoundException, IOException, UsuarioNaoExistenteException;
}

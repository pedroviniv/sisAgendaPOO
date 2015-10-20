/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Control;

import Exceptions.EmailJaCadastradoException;
import java.sql.SQLException;

/**
 *
 * @author kieckegard
 */
public interface RegisterUser
{
    void cadastraUsuario(String nome, String email, String senha, String telefone, String nascimento) throws SQLException, EmailJaCadastradoException;
}

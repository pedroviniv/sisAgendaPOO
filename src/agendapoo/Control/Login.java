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
    /**
     * Método responsável pela credencial do usuário, verifica se existe um usuário com o e-mail passado por parâmetro e logo em seguida, 
     * caso realmente exista um usuário cadastrado com o e-mail, verifica se se a senha desse e-mail bate com a senha passada por parâmetro.
     * Caso o usuário não exista, o método lançará a exceção UsuarioNaoExistenteException, caso o usuário exista, porém a senha não bate com 
     * a senha passada por parâmetro, o método retornará um valor NULL.
     * @param email - valor em String do email do usuário
     * @param senha - valor em String da senha do usuário
     * @return - Objeto de Usuário
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws UsuarioNaoExistenteException 
     */
    Usuario autentica(String email, String senha) throws SQLException, ClassNotFoundException, IOException, UsuarioNaoExistenteException;
}

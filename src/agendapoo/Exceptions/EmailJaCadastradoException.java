/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Exceptions;

/**
 * Exceção que será lançada caso o usuário tente cadastrar um e-mail já existente no banco de dados
 * ou arquivo.
 * @author kieckegard
 */
public class EmailJaCadastradoException extends Exception
{
    public EmailJaCadastradoException(String msg){
        super(msg);
    }
}

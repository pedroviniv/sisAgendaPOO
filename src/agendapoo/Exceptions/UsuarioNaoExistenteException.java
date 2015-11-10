/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Exceptions;

/**
 * Exceção que será lançada quando, quando tenta-se logar usando credenciais não existentes.
 * @author kieckegard
 */
public class UsuarioNaoExistenteException extends RuntimeException
{
    public UsuarioNaoExistenteException(String msg){
        super(msg);
    }
}

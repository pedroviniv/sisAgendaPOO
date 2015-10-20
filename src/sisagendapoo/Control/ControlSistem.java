/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Control;

import Exceptions.EmailJaCadastradoException;
import Exceptions.UsuarioNaoExistenteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import sisagendapoo.DAO.UsuarioDAO;
import sisagendapoo.Model.Usuario;

/**
 *
 * @author kieckegard
 */
public class ControlSistem implements Login, RegisterUser
{
    private Usuario loggedUser;
    
    public ControlSistem(){
        
    }
    
    @Override
    public Usuario logIn(String email, String senha) throws UsuarioNaoExistenteException, SQLException{
        System.out.println(email);
        UsuarioDAO dao = new UsuarioDAO();
        boolean existeUsuario = false;
        for(Usuario u : dao.list()){
            if(email.equals(u.getEmail())){
                existeUsuario = true;
                if(u.getPassword().equals(senha)){
                    loggedUser = u;
                    return u;
                }
            }    
        }
        if(!existeUsuario) throw new UsuarioNaoExistenteException("Não existe nenhum usuário com o email "+email);
        return null;
    }
    
    @Override
    public Usuario getLoggedUser(){
        return loggedUser;
    }
    
    private boolean isEmailValid(String email) throws SQLException{
        UsuarioDAO dao = new UsuarioDAO();
        for(Usuario u : dao.list())
            if(u.getEmail().equals(email))
                return false;
        return true;
    }
    
    @Override
    public void cadastraUsuario(String nome, String email, String senha, String telefone, String data) throws SQLException, EmailJaCadastradoException
    {
        if(isEmailValid(email)){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(data, dtf);
            Usuario u = new Usuario(nome,email,senha,telefone,dataNascimento);
            UsuarioDAO dao = new UsuarioDAO();
            dao.add(u);
        }
        else
            throw new EmailJaCadastradoException("Já existe uma conta cadastrada no sistema com esse email!");
    }
}

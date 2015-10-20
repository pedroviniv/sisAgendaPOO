/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author kieckegard
 */
public class Usuario
{
    private String nome;
    private String email;
    private String password;
    private String telefone;
    private LocalDate dataNascimento;
    private Set<String> changedAttributes;
    
    public Usuario(String nome, String email, String senha, String telefone, LocalDate dataNascimento){
        this.nome = nome;
        this.email = email;
        this.password = senha;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        changedAttributes = new HashSet<>();
    }
    
    public Usuario()
    {
        
    }
    
    public String getNome(){
        return nome;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getTelefone(){
        return telefone;
    }
    
    public String getPassword(){
        return password;
    }
    
    public LocalDate getDataNascimento(){
        return dataNascimento;
    }
    
    public String getFormattedDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        return dataNascimento.format(dtf);
    }
    
    public boolean verifica(String senha){
        return this.password.equals(senha);
    }
    
    public void setNome(String nome){
        this.nome=nome;
        changedAttributes.add("nome");
    }
    
    public void setSenha(String senha){
        this.password=senha;
        changedAttributes.add("senha");
    }
    
    public void setTelefone(String telefone){
        this.telefone=telefone;
        changedAttributes.add("telefone");
    }
    
    public void setEmail(String email){
        this.email=email;
        changedAttributes.add("email");
    }
    
    public Set<String> getChangedAttributes(){
        return changedAttributes;
    }

    @Override
    public String toString()
    {
        return "Usuario{" + "nome=" + nome + ", email=" + email + ", password=" + password + ", telefone=" + telefone + ", dataNascimento=" + dataNascimento + ", changedAttributes=" + changedAttributes + '}';
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 *
 * @author kieckegard
 */
public class Usuario implements Serializable
{
    private String nome;
    private final String email;
    private String senha;
    private String telefone;
    private LocalDate dataNascimento;
    
    public Usuario(String nome, String email, String senha, String telefone, LocalDate dataNascimento){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    public String getNome()
    {
        return nome;
    }

    public String getEmail()
    {
        return email;
    }

    public String getSenha()
    {
        return senha;
    }

    public String getTelefone()
    {
        return telefone;
    }
    
    public LocalDate getDataNascimento(){
        return dataNascimento;
    }
    
    public String getFormattedDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(dtf);
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }
    
    public boolean autentica(String senha){
        return this.senha.equals(senha);
    }

    public void setTelefone(String telefone)
    {
        this.telefone = telefone;
    }
    
    public void setDataNascimento(LocalDate dataNascimento){
        this.dataNascimento = dataNascimento;
    }
    
    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,31)
                .append(nome)
                .append(senha)
                .append(dataNascimento)
                .append(telefone)
                .append(email).toHashCode();
    }
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Usuario)) return false;
        if(o == this) return true;
        
        Usuario aux = (Usuario)o;
        
        return new EqualsBuilder()
                .append(nome, aux.getNome())
                .append(senha, aux.getSenha())
                .append(telefone, aux.getTelefone())
                .append(dataNascimento, aux.getDataNascimento())
                .append(email, aux.getEmail()).isEquals();
    }
    
    
}

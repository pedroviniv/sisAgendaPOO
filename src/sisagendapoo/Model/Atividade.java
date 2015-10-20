/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author kieckegard
 */
public class Atividade implements Comparable<Atividade>
{
    private int id;
    private String descricao; //
    private LocalDate data; //
    private LocalTime horaInicio; //
    private LocalTime horaFim; //
    private String local; //
    private List<String> convidados;
    private TipoAtividade tipo; //
    private Usuario usuario;

    public Atividade(LocalDate data, LocalTime horaInicio, LocalTime horaFim, String local, TipoAtividade tipo, String descricao, Usuario usuario)
    {
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.tipo = tipo;
        this.descricao = descricao;
        this.convidados = new ArrayList<>();
        this.usuario = usuario;
        this.local=local;
    }
    
    public Atividade()
    {
        
    }
    
    public LocalDate getData(){
        return data;
    }

    public int getId()
    {
        return id;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public LocalTime getHoraInicio()
    {
        return horaInicio;
    }

    public LocalTime getHoraFim()
    {
        return horaFim;
    }
    
    public String getConvidadosString(){
        String result="";
        for(String s : convidados){
            result+=' '+s;
        }
        return result;
    }

    public List<String> getConvidados()
    {
        return convidados;
    }

    public TipoAtividade getTipo()
    {
        return tipo;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }
    
    public String getLocal(){
        return local;
    }
    
    public void setConvidao(List<String> convidados)
    {
        this.convidados = convidados;
    }
    
    public void setId(int id){
        this.id = id;
    }

    @Override
    public int compareTo(Atividade o)
    {
        Period p = Period.between(data, o.data);
        return p.getDays();
    }
    
    public String getFormattedDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(dtf);
    }

    @Override
    public String toString()
    {
        return "Atividade{" + "id=" + id + ", descricao=" + descricao + ", data=" + data + ", horaInicio=" + horaInicio + ", horaFim=" + horaFim + ", convidados=" + convidados + ", tipo=" + tipo + ", usuario=" + usuario + '}';
    }
    
    
    
    
}

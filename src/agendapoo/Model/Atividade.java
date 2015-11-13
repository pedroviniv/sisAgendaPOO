/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Classe modelo de Atividade com todos os atributos referentes a mesma.
 * @author kieckegard
 */
public class Atividade implements Serializable, Comparable<Atividade>
{
    private UUID id;
    private String descricao;
    private String local;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private List<String> convidados;
    private final Usuario usuario;
    private TipoAtividade tipo;
    
    public Atividade(String descricao, String local, LocalDate data, LocalTime horaInicio, LocalTime horaFim, TipoAtividade tipo, Usuario u){
        id = UUID.randomUUID();
        this.descricao = descricao;
        this.local = local;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        convidados = new ArrayList<>();
        this.tipo = tipo;
        this.usuario = u;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }
    
    public TipoAtividade getTipo(){
        return tipo;
    }
    
    public void setTipo(TipoAtividade tipo){
        this.tipo = tipo;
    }

    public String getLocal()
    {
        return local;
    }
    
    public String getId(){
        return id.toString();
    }
    
    public void setId(String id){
        this.id = UUID.fromString(id);
    }
    
    public Usuario getUsuario(){
        return usuario;
    }

    public void setLocal(String local)
    {
        this.local = local;
    }

    public LocalDate getData()
    {
        return data;
    }
    
    /**
     * LocalDate possui o formato da data seguindo esse modelo "yyyy-MM-dd", esse
     * método tem como objetivo retornar a String da LocalDate com sua data
     * no formato "dd/MM/yyyy".
     * @return String contendo a data formatada no padrão dd/MM/yyyy.
     */
    public String getFormattedDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.data.format(dtf);
    }

    public void setData(LocalDate data)
    {
        this.data = data;
    }

    public LocalTime getHoraInicio()
    {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio)
    {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim()
    {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim)
    {
        this.horaFim = horaFim;
    }

    public List<String> getConvidados()
    {
        return convidados;
    }
    
    /**
     * Método responsável por retornar todos os convidados da atividade em String.
     * @return String contendo todos os convidados da atividade.
     */
    public String getConvidadosString(){
        String output = "";
        for(String convidado : convidados)
            output += convidado+" ";
        return output;
    }

    public void setConvidados(List<String> convidados)
    {
        this.convidados = convidados;
    }
    
    @Override
    public boolean equals(Object o){
        if(!(o instanceof Atividade)) return false;
        if(o == this) return true;
        
        Atividade aux = (Atividade)o;
        return new EqualsBuilder()
                .append(descricao, aux.getDescricao())
                .append(local, aux.getLocal())
                .append(data, aux.getData())
                .append(horaInicio, aux.getHoraInicio())
                .append(horaFim, aux.getHoraFim())
                .append(convidados, aux.getConvidados())
                .append(id, aux.getId()).isEquals();
    }
    
    
    @Override
    public int hashCode(){
        return new HashCodeBuilder(17,31).append(descricao)
                .append(local)
                .append(data)
                .append(horaInicio)
                .append(horaFim)
                .append(convidados)
                .append(usuario)
                .append(id).toHashCode();
    }

    @Override
    public int compareTo(Atividade o)
    {
        Period p = Period.between(o.data, data);
        if(p.getDays()==0)
            return this.horaInicio.compareTo(o.getHoraInicio());
        return p.getDays();
    }
    
    
    
}

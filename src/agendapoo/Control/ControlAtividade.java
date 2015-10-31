/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Control;

import agendapoo.DAO.ARQUIVO.AtividadeDAOarquivo;
import agendapoo.DAO.JDBC.AtividadeDAOjdbc;
import agendapoo.DAO.IAtividadeDAO;
import agendapoo.Exceptions.InvalidTimeRangeException;
import agendapoo.Exceptions.TimeInterferenceException;
import agendapoo.Model.Atividade;
import agendapoo.Model.TipoAtividade;
import agendapoo.Model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author kieckegard
 */
public class ControlAtividade implements AtividadeController
{
    private final EmailSender sender;
    private final IAtividadeDAO dao;

    public ControlAtividade(){
        dao = new AtividadeDAOjdbc();
        sender = new ControlEmail();
    }
    
    
    @Override
    public void cadastraAtividade(String descricao, String local, String data, String horaInicio, String horaFim, List<String> convidados, TipoAtividade tipo, Usuario u) throws SQLException, IOException, ClassNotFoundException, TimeInterferenceException, InvalidTimeRangeException, EmailException
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data_aux = LocalDate.parse(data,dtf);
        LocalTime start = LocalTime.parse(horaInicio);
        LocalTime end = LocalTime.parse(horaFim);
        if(start.compareTo(end)==0)
            throw new TimeInterferenceException("Ué, sua atividade vai durar 0 minutos? Por favor, ajeita isso!");
        if(isTimeRangeValid(start,end))
            throw new InvalidTimeRangeException("Ué, sua atividade vai durar um tempo negativo? Por favor, ajeita isso!");
        if(isValidTimeByUser(data_aux,start,end,u)){
            Atividade a = new Atividade(descricao,local,data_aux,start,end,tipo,u);
            a.setConvidados(convidados);
            dao.add(a);
            if(!convidados.isEmpty())
                sender.sendEmail(a, a.getUsuario(),TipoEmail.CREATE);
        }else throw new TimeInterferenceException("Houve um choque de horário com outras atividades cadastradas. \nPor favor, selecione um outro horário!");
    }

    @Override
    public void deletaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, EmailException
    {
        dao.delete(atividade);
        if(!atividade.getConvidados().isEmpty())
            sender.sendEmail(atividade, atividade.getUsuario(),TipoEmail.DELETE);
    }

    @Override
    public void atualizaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, TimeInterferenceException, EmailException, InvalidTimeRangeException
    {
        if(atividade.getHoraInicio().compareTo(atividade.getHoraFim())==0)
            throw new TimeInterferenceException("Ué, sua atividade vai durar 0 minutos? Por favor, ajeita isso!");
        if(isTimeRangeValid(atividade.getHoraInicio(),atividade.getHoraFim()))
            throw new InvalidTimeRangeException("Ué, sua atividade vai durar um tempo negativo? Por favor, ajeita isso!");
        if(isValidTimeUpdate(atividade)){
            dao.update(atividade);
            if(!atividade.getConvidados().isEmpty())
                sender.sendEmail(atividade, atividade.getUsuario(),TipoEmail.UPDATE);
        }else throw new TimeInterferenceException("Houve um choque de horário com outras atividades cadastradas. \nPor favor, selecione um outro horário!");
    }
    
    private boolean isValidTimeUpdate(Atividade current) throws SQLException, IOException, ClassNotFoundException{
        boolean isValid = false;
        List<Atividade> atividades = dao.list(current.getUsuario());
        List<Atividade> sameDay = new ArrayList<>();
        LocalTime start = current.getHoraInicio();
        LocalTime end = current.getHoraFim();
        
        atividades.stream().forEach((a) ->
        {
            Period p = Period.between(a.getData(), current.getData());
            if ((p.getDays() == 0) && (!a.getId().equals(current.getId())))
            {
                sameDay.add(a);
            }
        });
        
        if(!sameDay.isEmpty()){
            for(Atividade a : sameDay)
               if((start.isBefore(a.getHoraInicio()) && end.isBefore(a.getHoraInicio())) || (start.isAfter(a.getHoraFim()) && end.isAfter(a.getHoraFim())))
                   isValid = true;
               else{
                   isValid = false;
                   break;
               }
            return isValid;
        }
        return true;
    }
    
    private boolean isTimeRangeValid(LocalTime start, LocalTime end) throws InvalidTimeRangeException{
        return start.isAfter(end);
    }
    
    private boolean isValidTimeByUser(LocalDate data, LocalTime start, LocalTime end, Usuario u) throws SQLException, IOException, ClassNotFoundException{
        boolean isValid = false;
        List<Atividade> atividades = dao.list(u);
        List<Atividade> sameDay = new ArrayList<>();
        
        atividades.stream().forEach((a) ->
        {
            Period p = Period.between(a.getData(), data);
            if (p.getDays() == 0)
            {
                sameDay.add(a);
            }
        });
        
        if(!sameDay.isEmpty()){
            for(Atividade a : sameDay)
               if((start.isBefore(a.getHoraInicio()) && end.isBefore(a.getHoraInicio())) || (start.isAfter(a.getHoraFim()) && end.isAfter(a.getHoraFim())))
                   isValid = true;
               else{
                   isValid = false;
                   break;
               }
            return isValid;
        }
        return true;
    }
    
    public Atividade getAtividadeById(Usuario u, String id) throws SQLException, IOException, ClassNotFoundException{
        List<Atividade> atividades = dao.list(u);
        for(Atividade a : atividades){
            if(a.getId().equals(id))
                return a;
        }
        return null;
    }
    
    @Override
    public List<Atividade> getAtividadesByUser(Usuario u) throws SQLException, IOException, ClassNotFoundException{
        List<Atividade> list = dao.list(u);
        Collections.sort(list);
        return list;
    }
    
}

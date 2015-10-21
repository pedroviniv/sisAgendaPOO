/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Control;

import Exceptions.TimeInterferenceException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sisagendapoo.DAO.AtividadeDAO;
import sisagendapoo.DAO.IAtividadeDAO;
import sisagendapoo.Model.Atividade;
import sisagendapoo.Model.Usuario;


/**
 *
 * @author kieckegard
 */
public class ControlAtividade
{
    private final IAtividadeDAO dao;
    
    public ControlAtividade()
    {
        dao = new AtividadeDAO();
    }
 
    public List<Atividade> atividadesByUser(Usuario u) throws SQLException{
        return dao.list(u);
    }
    
    public List<Atividade> orderByDate(List<Atividade> list){
        Collections.sort(list);
        return list;
    }
    
    private boolean isValidTimeByUser(LocalDate data, LocalTime start, LocalTime end, Usuario u) throws SQLException, IOException{
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
    
    public void cadastraAtividade(Atividade a, Usuario u) throws SQLException, IOException{
        if(isValidTimeByUser(a.getData(),a.getHoraInicio(), a.getHoraFim(),u)){
            dao.add(a);
        }
        else throw new TimeInterferenceException("Há Interferência no horário estabelicido para essa Atividade!"
                + "\n Por favor, selecione um outro horário!");
    }
    
    public void removeAtividade(Atividade a) throws SQLException, IOException{
        dao.remove(a);
    }
    
    public Atividade getAtividadeById(int id, Usuario u) throws SQLException{
        return dao.getAtividadeById(id, u);
    }
    
    public void atualizaAtividade(Atividade a) throws SQLException, IOException{
        dao.update(a);
    }
    
 
    
}

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
 * Classe que terá a implementação de todos os métodos de AtividadeController e mais alguns que se farão necessário para
 * operações com Atividade.
 * EmailSender - Interface com o método de enviar Email
 * IAtividadeDAO - Interface com os métodos para manipulação de Atividades no banco de dados ou arquivo
 * @author kieckegard
 */
public class ControlAtividade implements AtividadeController
{
    private final EmailSender sender;
    private final IAtividadeDAO dao;

    public ControlAtividade(){
        dao = new AtividadeDAOarquivo();
        sender = new ControlEmail();
    }
    
    
    /**
     * Método responsável por construir um objeto de Atividade e logo em seguida, Cadastra-lo no banco, seja usando arquivo ou um sgbd.
     * @param descricao - A descrição da atividade
     * @param local - O local em que a atividade será realizada
     * @param data - A data que a atividade será realizada
     * @param horaInicio - O horário que a atividade começa.
     * @param horaFim - O horário que a atividade termina.
     * @param convidados - Os convidados que participarão da atividade e serão notificados por e-mail sobre a atividade.
     * @param tipo - O tipo da atividade, podendo ser PROFISSIONAL, ACADEMICO ou PESSOAL.
     * @param u - O Usuário que está cadastrando a atividade.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InvalidTimeRangeException - A exceção é lançada caso o horário inicial da atividade seja maior que o horário final.
     * @throws TimeInterferenceException - A exceção é lançado caso o horário da atividade entre em conflito com outras atividades cadastradas.
     * @throws EmailException - A exceção é lançada caso não consiga enviar os e-mails para os convidados da atividade.
     */
    @Override
    public void cadastraAtividade(String descricao, String local, String data, String horaInicio, String horaFim, List<String> convidados, TipoAtividade tipo, Usuario u) throws SQLException, IOException, ClassNotFoundException, TimeInterferenceException, InvalidTimeRangeException, EmailException
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data_aux = LocalDate.parse(data,dtf);
        LocalTime start = LocalTime.parse(horaInicio);
        LocalTime end = LocalTime.parse(horaFim);
        if(start.compareTo(end)==0)
            throw new TimeInterferenceException("Ué, sua atividade vai durar 0 minutos? Por favor, ajeita isso!");
        if(!isTimeRangeValid(start,end))
            throw new InvalidTimeRangeException("Ué, sua atividade vai durar um tempo negativo? Por favor, ajeita isso!");
        if(isValidTimeByUser(data_aux,start,end,u)){
            Atividade a = new Atividade(descricao,local,data_aux,start,end,tipo,u);
            a.setConvidados(convidados);
            dao.add(a);
            if(!convidados.isEmpty())
                sender.sendEmail(a, a.getUsuario(),TipoEmail.CREATE);
        }else throw new TimeInterferenceException("Houve um choque de horário com outras atividades cadastradas. \nPor favor, selecione um outro horário!");
    }

    /**
     * Remove a atividade passada por parâmetro do Banco de dados ou arquivo.
     * @param atividade - Objeto de Atividade
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws EmailException - A exceção é lançada caso o controlador não consiga enviar a mensagem aos convidados avisando sobre a remorção da atividade.
     */
    @Override
    public void deletaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, EmailException
    {
        dao.delete(atividade);
        if(!atividade.getConvidados().isEmpty())
            sender.sendEmail(atividade, atividade.getUsuario(),TipoEmail.DELETE);
    }

    /**
     * Atualiza os atributos de atividade no banco de dados ou arquivo.
     * @param atividade - Objeto de Atividade
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InvalidTimeRangeException - A exceção é lançada caso o horário inicial da atividade seja maior que o horário final.
     * @throws TimeInterferenceException - A exceção é lançado caso o horário da atividade entre em conflito com outras atividades cadastradas.
     * @throws EmailException - A exceção é lançada caso não consiga enviar os e-mails para os convidados avisando sobre a atualização da atividade.
     */
    @Override
    public void atualizaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, TimeInterferenceException, EmailException, InvalidTimeRangeException
    {
        if(atividade.getHoraInicio().compareTo(atividade.getHoraFim())==0)
            throw new TimeInterferenceException("Ué, sua atividade vai durar 0 minutos? Por favor, ajeita isso!");
        if(!isTimeRangeValid(atividade.getHoraInicio(),atividade.getHoraFim()))
            throw new InvalidTimeRangeException("Ué, sua atividade vai durar um tempo negativo? Por favor, ajeita isso!");
        if(isValidTimeUpdate(atividade)){
            dao.update(atividade);
            if(!atividade.getConvidados().isEmpty())
                sender.sendEmail(atividade, atividade.getUsuario(),TipoEmail.UPDATE);
        }else throw new TimeInterferenceException("Houve um choque de horário com outras atividades cadastradas. \nPor favor, selecione um outro horário!");
    }
    
    /**
     * Verifica se o horário inserido após uma atualização é válido, ou seja, se entra em conflito
     * com outras atividades, caso entre em conflito com outras atividades retornará False, caso contrário,
     * True, Como é uma atualização nesse método a verificação ignorará o horário que tiver no banco dessa mesma atividade.
     * @param current - Atividade que foi atualizada e que terá seu horário verificado.
     * @return - valor booleano indicando se o horário é válido ou não.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
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
    
    /**
     * Verifica se o horário inserido após uma inserção é válido, ou seja, se entra em conflito
     * com outras atividades, caso entre em conflito com outras atividades retornará False, caso contrário,
     * @param start - LocalTime contendo o horário inicial da atividade
     * @param end - LocalTime contendo o horário final da atividade
     * @return - valor booleano indicando se o horário inicial é maior ou não que o horário final.
     * @throws InvalidTimeRangeException 
     */
    private boolean isTimeRangeValid(LocalTime start, LocalTime end) throws InvalidTimeRangeException{
        return !start.isAfter(end);
    }
    
    /**
      * Verifica se o horário inserido após uma inserção é válido, ou seja, se entra em conflito
     *  com outras atividades, caso entre em conflito com outras atividades retornará False, caso contrário,
     * retornará True.
     * @param data - LocalDate contendo a data da atividade
     * @param start - LocalTime contendo o horário inicial da atividade
     * @param end - LocalTime contendo o horário final da atividade
     * @param u - Usuário que está adicionando a atividade (faz-se necessário por verificar se o horário é válido apenas considerando as atividades
     * daquele usuário)
     * @return - valor booleano indicando se o horário definido para atividade é válido ou não.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
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
    
    /**
     * Procura uma atividade no banco de dados ou arquivo que contenha a Id passada por parâmetro, o usuário faz-se necessário para poder
     * filtrar logo pelas atividades do usuário e em seguida procurar na lista de atividades do usuário a Id passada por parâmetro.
     * @param usuario - usuario logado no sistema
     * @param id - identificação da atividade
     * @return - Objeto de Atividade ou nulo, caso não encontre nenhuma atividade com o Id especificado.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public Atividade getAtividadeById(Usuario usuario, String id) throws SQLException, IOException, ClassNotFoundException{
        List<Atividade> atividades = dao.list(usuario);
        for(Atividade a : atividades){
            if(a.getId().equals(id))
                return a;
        }
        return null;
    }
    
     /**
     * Retorna uma Lista contendo todas as atividades cadastradas no banco de dados ou arquivo referente ao Usuário passado por parâmetro.
     * @param u
     * @return Lista de Objetos Atividade
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public List<Atividade> getAtividadesByUser(Usuario u) throws SQLException, IOException, ClassNotFoundException{
        List<Atividade> list = dao.list(u);
        Collections.sort(list);
        return list;
    }
    
}

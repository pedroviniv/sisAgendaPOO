/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Control;

import agendapoo.Exceptions.InvalidTimeRangeException;
import agendapoo.Exceptions.TimeInterferenceException;
import agendapoo.Model.Atividade;
import agendapoo.Model.TipoAtividade;
import agendapoo.Model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author kieckegard
 */
public interface AtividadeController
{
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
    void cadastraAtividade(String descricao, String local, String data, String horaInicio, String horaFim, List<String> convidados,TipoAtividade tipo, Usuario u) throws SQLException, IOException, ClassNotFoundException, InvalidTimeRangeException, TimeInterferenceException, EmailException;
    /**
     * Remove a atividade passada por parâmetro do Banco de dados ou arquivo.
     * @param atividade - Objeto de Atividade
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws EmailException - A exceção é lançada caso o controlador não consiga enviar a mensagem aos convidados avisando sobre a remorção da atividade.
     */
    void deletaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, EmailException;
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
    void atualizaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, InvalidTimeRangeException, TimeInterferenceException, EmailException;
    /**
     * Retorna uma Lista contendo todas as atividades cadastradas no banco de dados ou arquivo referente ao Usuário passado por parâmetro.
     * @param u
     * @return Lista de Objetos Atividade
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    List<Atividade> getAtividadesByUser(Usuario u) throws SQLException, IOException, ClassNotFoundException;
}

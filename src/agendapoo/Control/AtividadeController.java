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
    void cadastraAtividade(String descricao, String local, String data, String horaInicio, String horaFim, List<String> convidados,TipoAtividade tipo, Usuario u) throws SQLException, IOException, ClassNotFoundException, InvalidTimeRangeException, TimeInterferenceException, EmailException;
    void deletaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, EmailException;
    void atualizaAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException, InvalidTimeRangeException, TimeInterferenceException, EmailException;
    List<Atividade> getAtividadesByUser(Usuario u) throws SQLException, IOException, ClassNotFoundException;
}

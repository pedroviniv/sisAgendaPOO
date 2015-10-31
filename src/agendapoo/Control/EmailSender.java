/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Control;

import agendapoo.Model.Atividade;
import agendapoo.Model.Usuario;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author kieckegard
 */
public interface EmailSender
{
    void sendEmail(Atividade atividade, Usuario u, TipoEmail tipo) throws EmailException;
}

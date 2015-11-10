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
 * Interface com o método para enviar o e-mail para os convidados
 * @author kieckegard
 */
public interface EmailSender
{
    /**
     * Envia e-mail para cada um dos convidados da atividade a identificação de quem enviou o e-mail é obtida através do usuário passado por parâmetro
     * e o TipoEmail Vai definir se a mensagem vai ser do tipo Remorção, Atualização ou Criação de uma atividade, gerando uma mensagem específica para
     * cada tipo.
     * @param atividade - Objeto de Atividade
     * @param usuario - Objeto do Usuário que fez essa atividade
     * @param tipoAtividade - Enum to Tipo da Atividade podendo ser CREATE,REMOVE OR UPDATE.
     * @throws EmailException 
     */
    void sendEmail(Atividade atividade, Usuario usuario, TipoEmail tipoAtividade) throws EmailException;
}

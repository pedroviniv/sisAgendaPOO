/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Control;

/**
 * Enum com os possíveis tipos de e-mail que será enviado para os clientes.
 * @author kieckegard
 */
public enum TipoEmail
{
    /**
     * O email pode ser to tipo CREATE, que é quando o e-mail acaba de ser criado
     * e será enviado um email aos convidados notificando-os que a atividade acaba de ser criada.
     */
    CREATE,
    /**
     * Pode ser to tipo UPDATE, que é quando a atividade foi atualizada, ou seja, o usuário alterou os 
     * dados da atividade e será enviado um email aos convidados notificando-os que a atividade acaba de ser
     * atualizada juntamento com os dados atualizados.
     */
    UPDATE,
    /**
     * Pode ser do tipo DELETE, que é quando a atividade foi deletada e será enviado um
     * e-mail aos convidados, notificando-os sobre a remorção da atividade.
     */
    DELETE;
}

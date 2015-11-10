/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Exceptions;

/**
 * Exceção que será lançada caso haja problemas referente ao horário da atividade, podendo ser lançado
 * tanto no momento de cadastro quanto no momento de atualização.
 * Essa exceção é específica para problemas referente a possíveis interferência entre o horário que você tá querendo 
 * colocar na atividade e o horário das atividades que já estão cadastradas.
 * @author kieckegard
 */
public class TimeInterferenceException extends Exception
{
    public TimeInterferenceException(String msg){
        super(msg);
    }
}

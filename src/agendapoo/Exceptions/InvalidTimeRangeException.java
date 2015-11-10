/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Exceptions;

/**
 * Exceção que será lançada caso haja problemas referente ao horário da atividade, podendo ser lançado
 * tanto no momento de cadastro quanto no momento de atualização.
 * Essa exceção é específica para problemas referente a diferença entre horários (horário inicial e final),
 * por exemplo, quando o horário inicial é maior que o horário final, quando os horários são iguais, etc.
 * @author kieckegard
 */
public class InvalidTimeRangeException extends Exception
{
    public InvalidTimeRangeException(String msg){
        super(msg);
    }
}

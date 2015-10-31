/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.Control;

import agendapoo.Model.Atividade;
import agendapoo.Model.Usuario;
import java.util.List;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author kieckegard
 */
public class ControlEmail implements EmailSender
{
    private SimpleEmail email;
    
    public ControlEmail(){
        email = new SimpleEmail();
        setConfigs();
    }
    
    private void setConfigs()
    {
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);
    }
    
    private void setTituloEmail(Atividade a, TipoEmail tipo)
    {
        String top = "";
        switch (tipo)
        {
            case CREATE:
                top = "[Nova Atividade!] ";
                break;
            case UPDATE:
                top = "[Atualização de Atividade!] ";
                break;
            case DELETE:
                top = "[Atividade desmarcada!] ";
                break;
        }
        String subject = top + a.getDescricao();
        email.setSubject(subject);
    }
    
    private String getMessage(Atividade a)
    {
        String msg = "Atividade!!!" + "\n" + a.getDescricao() + "\nLocal: " + a.getLocal() + "\nData: ["+a.getFormattedDate()+"]\nHorário: [start: " + a.getHoraInicio().toString()
                + "] | [end: " + a.getHoraFim().toString() + "] ";
        return msg;
    }
    
    public String[] toStringArray(List<String> list){
        String[] convidados = new String[list.size()];
        int i=0;
        for(String s : list){
            convidados[i]=s;
            i++;
        }
        return convidados;
    }
    
    @Override
    public void sendEmail(Atividade atividade, Usuario u, TipoEmail tipo) throws EmailException
    {
        setTituloEmail(atividade, tipo);
        System.out.println("Enviando email... "+email.getSubject());
        email.setFrom(u.getEmail(), u.getNome());
        System.out.println("De: ["+u.getEmail()+"] | ["+u.getNome()+"]");
        setTituloEmail(atividade, tipo);
        String msg = getMessage(atividade);
        System.out.println("[Messagem: "+msg+"]");
        email.setMsg(msg);
        email.setAuthentication("sysagenda@gmail.com","Sisagendapoo");
        email.addTo(toStringArray(atividade.getConvidados()));
        email.send();
    }

    
    
}

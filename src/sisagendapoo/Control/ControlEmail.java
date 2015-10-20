/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Control;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sisagendapoo.Model.Atividade;

/**
 *
 * @author kieckegard
 */
public class ControlEmail
{

    private Properties props;
    private Session session;
    private MimeMessage generateMailMessage;

    public ControlEmail()
    {
        props = new Properties();
        settingProperties();
    }

    private void settingProperties()
    {
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        session = Session.getDefaultInstance(props, null);
    }
    
    private void sendingEmail(String emailTo, String emailCC, String senha, Atividade a) throws MessagingException{
        generateMailMessage = new MimeMessage(session);
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(emailCC));
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
        generateMailMessage.setSubject(a.getDescricao());
        String emailBody ="<html><p>Comparecer no(a) <strong>"+a.getLocal()+"</strong> para o evento <strong>'"+a.getDescricao()+"'</strong> marcado para <br><strong>"+a.getFormattedDate()+". De "+a.getHoraInicio().toString()+" às "+a.getHoraFim()+"<strong></br></html></p>";
        generateMailMessage.setContent(emailCC, "text/html");
        
        Transport transport = session.getTransport("smtp");
        
        transport.connect("smtp.gmail.com","pfernandesvasconcelos",senha);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
    
    public void sendingEmails(String email, String senha, Atividade a) throws MessagingException
    {
        for(String emailTo : a.getConvidados()){
            sendingEmail(emailTo,email,senha,a);
        }
    }
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.View;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.mail.MessagingException;
import sisagendapoo.Control.ControlEmail;
import sisagendapoo.DAO.AtividadeDAO;
import sisagendapoo.Model.Atividade;
import sisagendapoo.Model.TipoAtividade;
import sisagendapoo.Model.Usuario;

/**
 *
 * @author kieckegard
 */
public class SisAgendaPOO
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        /*AtividadeDAO dao = new AtividadeDAO();
        Usuario u = new Usuario("Pedro Arthur","pedroviniv@hotmail.com","123456","(83)99366-5101)",LocalDate.parse("1996-12-26"));
        try{
            List<Atividade> atividades = dao.list(u);
            for(Atividade a : atividades)
            {
                int i=0;
                System.out.println(a.getDescricao());
                System.out.println("Inicio: "+a.getHoraInicio().toString());
                System.out.println("Fim: "+a.getHoraFim().toString());
                for(String email : a.getConvidados()){
                    i++;
                    System.out.println("convidado "+i+" : "+email);
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }*/
        
        Usuario u = new Usuario("Pedro Arthur","pfernandesvasconcelos@gmail.com","wib17r618mpafv1","(83)99366-5101)",LocalDate.parse("1996-12-26"));   
        Atividade a = new Atividade(LocalDate.now(),LocalTime.of(15, 30),LocalTime.of(16, 00),"Em casa",TipoAtividade.ACADEMICO,"Estudar Banco de dados",u);
       
        a.getConvidados().add("pafernandesvasconcelos@hotmail.com");
        a.getConvidados().add("pedroviniv@hotmail.com");
        ControlEmail ce = new ControlEmail();
        
        try{
            ce.sendingEmails(u.getEmail(), u.getPassword(), a);
        }
        catch(MessagingException me){
            me.printStackTrace();
        }
    }
    
}

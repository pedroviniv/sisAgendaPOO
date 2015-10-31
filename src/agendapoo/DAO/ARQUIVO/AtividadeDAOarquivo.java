/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO.ARQUIVO;

import agendapoo.DAO.IAtividadeDAO;
import agendapoo.Model.Atividade;
import agendapoo.Model.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public class AtividadeDAOarquivo implements IAtividadeDAO
{
    private File arquivo;
    
    public AtividadeDAOarquivo(){
        try{
        arquivo = new File("Data//Atividade//Atividade.data");
        if(!arquivo.exists()) arquivo.createNewFile();
        }catch(IOException io){
            System.out.println("Erro ao criar arquivo!");
        }
    }
    
    @Override
    public List<Atividade> list(Usuario u) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades;
        ObjectInputStream ois;
        if(arquivo.length()>0){
            ois = new ObjectInputStream(new FileInputStream(arquivo));
            atividades = (ArrayList<Atividade>)ois.readObject();
            ois.close();
            for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
                Atividade a = i.next();
                if(!a.getUsuario().getEmail().equals(u.getEmail())){
                    i.remove();
                }
            }
        }else atividades = new ArrayList<>();
        return atividades;
        
    }

    @Override
    public void add(Atividade obj) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = this.list();
        atividades.add(obj);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
        os.close();
    }

    @Override
    public void delete(Atividade obj) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = this.list();
        for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
            Atividade a = i.next();
            if(a.getDescricao().equals(obj.getDescricao())){
                i.remove();
            }
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
        os.close();
    }
    
    @Override
    public void deleteByUser(Usuario obj) throws SQLException, IOException, ClassNotFoundException{
        List<Atividade> atividades = this.list();
        for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
            Atividade a = i.next();
            if(a.getUsuario().getEmail().equals(obj.getEmail()))
                i.remove();
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
        os.close();
    }

    @Override
    public void update(Atividade obj) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = this.list();
        for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
            Atividade a = i.next();
            if(obj.getId().equals(a.getId()))
            {
                a.setConvidados(obj.getConvidados());
                a.setData(obj.getData());
                a.setLocal(obj.getLocal());
                a.setDescricao(obj.getDescricao());
                a.setHoraFim(obj.getHoraFim());
                a.setHoraInicio(obj.getHoraInicio());
                a.setConvidados(obj.getConvidados());
                a.setTipo(obj.getTipo());
            }
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
    }

    @Override
    public List<Atividade> list() throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades;
        ObjectInputStream ois;
        if(arquivo.length()>0){
            ois = new ObjectInputStream(new FileInputStream(arquivo));
            atividades = (ArrayList<Atividade>)ois.readObject();
            ois.close();
        }else atividades = new ArrayList<>();
        return atividades;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO.ARQUIVO;

import agendapoo.DAO.DAO;
import agendapoo.DAO.IAtividadeDAO;
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
public class UsuarioDAOarquivo implements DAO<Usuario>
{
    private File arquivo;
    
    public UsuarioDAOarquivo(){
        arquivo = new File("Data//Usuario//Usuario.data");
        try{
            if(!arquivo.exists()) arquivo.createNewFile();
        }catch(IOException io){
            System.out.println("Erro ao criar arquivo!");
        }
    }
    
    @Override
    public void add(Usuario obj) throws SQLException, IOException, ClassNotFoundException
    {
        List<Usuario> lista = this.list();
        lista.add(obj);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(lista);
        os.close();
    }

    @Override
    public void delete(Usuario obj) throws SQLException, IOException, ClassNotFoundException
    {
        //primeira deleta todas as atividades relacionadas com o usuário a ser deletado
        IAtividadeDAO dao = new AtividadeDAOarquivo();
        dao.deleteByUser(obj);
        
        //Finalmente deleta o usuário
        List<Usuario> lista = this.list();
        for(Iterator<Usuario> i = lista.iterator(); i.hasNext();){
            Usuario u = i.next();
            if(u.getEmail().equals(obj.getEmail()))
                i.remove();
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(lista);
        os.close();
    }

    @Override
    public void update(Usuario obj) throws SQLException, IOException, ClassNotFoundException
    {
        List<Usuario> lista = this.list();
        for(Usuario u : lista){
            if(u.getEmail().equals(obj.getEmail()))
            {
                u.setNome(obj.getNome());
                u.setSenha(obj.getSenha());
                u.setTelefone(obj.getTelefone());
            }
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(lista);
        os.close();
    }

    @Override
    public List<Usuario> list() throws SQLException, IOException, ClassNotFoundException
    {
        List<Usuario> lista;
        ObjectInputStream ois;
        if(this.arquivo.length()>0){
            ois = new ObjectInputStream(new FileInputStream(arquivo));
            lista = (ArrayList<Usuario>)ois.readObject();
            ois.close();
        }else
            lista = new ArrayList<>();
        return lista;
    }
    
}

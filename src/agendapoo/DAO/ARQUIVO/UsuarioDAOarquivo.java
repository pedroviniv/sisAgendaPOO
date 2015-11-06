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
 * Classe responsável pela persistência e manipulação de usuários em arquivo binário.
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
    
    /**
     * Método responsável por persistir um determinado usuário no arquivo.
     * @param usuario - instância de usuário
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void add(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        List<Usuario> lista = this.list();
        lista.add(usuario);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(lista);
        os.close();
    }

    /**
     * Método responsável por deletar um usuário no arquivo.
     * @param usuario - instância de usuário que deve ser deletado
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void delete(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        //primeira deleta todas as atividades relacionadas com o usuário a ser deletado
        IAtividadeDAO dao = new AtividadeDAOarquivo();
        dao.deleteByUser(usuario);
        
        //Finalmente deleta o usuário
        List<Usuario> lista = this.list();
        for(Iterator<Usuario> i = lista.iterator(); i.hasNext();){
            Usuario u = i.next();
            if(u.getEmail().equals(usuario.getEmail()))
                i.remove();
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(lista);
        os.close();
    }

    /**
     * Método responsável por atualizar os dados de um usuário no arquivo.
     * A instância de usuário passada por parâmetro já deve estar atualizada com
     * os novos dados.
     * @param usuario - Instância de Usuario já com os dados atualizados que deverá 
     * atualizar no arquivo.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void update(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        List<Usuario> lista = this.list();
        for(Usuario u : lista){
            if(u.getEmail().equals(usuario.getEmail()))
            {
                u.setNome(usuario.getNome());
                u.setSenha(usuario.getSenha());
                u.setTelefone(usuario.getTelefone());
            }
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(lista);
        os.close();
    }
    
    /**
     * Método responsável por pegar do arquivo uma Lista contendo todos os usuários cadastrados no sistema.
     * @return List de Usuário com todos os usuário salvos no arquivo.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
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

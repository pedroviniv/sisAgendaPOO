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
 * Classe responsável por adicionar, remover, atualizar e listar atividades
 * em arquivo.
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
    
    /**
     * Método responsável por retornar uma Lista de atividades de um determinado Usuário.
     * @param usuario - Instância de Usuario cujas Atividades devem ser retornadas.
     * @return - List de Atividade contendo todas as atividades do usuário passado por parâmetro.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public List<Atividade> list(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades;
        ObjectInputStream ois;
        if(arquivo.length()>0){
            ois = new ObjectInputStream(new FileInputStream(arquivo));
            atividades = (ArrayList<Atividade>)ois.readObject();
            ois.close();
            for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
                Atividade a = i.next();
                if(!a.getUsuario().getEmail().equals(usuario.getEmail())){
                    i.remove();
                }
            }
        }else atividades = new ArrayList<>();
        return atividades;
        
    }
    
    /**
     * Persiste uma nova atividade no arquivo
     * @param atividade - Instância de atividade que você deseja persistir.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void add(Atividade atividade) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = this.list();
        atividades.add(atividade);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
        os.close();
    }

    /**
     * Método responsável por deletar uma atividade do arquivo onde estão salvo todas as atividades.
     * @param atividade - Instância da atividade que deve ser deletada.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void delete(Atividade atividade) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = this.list();
        for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
            Atividade a = i.next();
            if(a.getDescricao().equals(atividade.getDescricao())){
                i.remove();
            }
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
        os.close();
    }
    
    /**
     * Método responsável por deletar todas as atividades que um determinado usuário possuir.
     * @param usuario - instância de Usuário cujas atividades devem ser deletadas.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void deleteByUser(Usuario usuario) throws SQLException, IOException, ClassNotFoundException{
        List<Atividade> atividades = this.list();
        for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
            Atividade a = i.next();
            if(a.getUsuario().getEmail().equals(usuario.getEmail()))
                i.remove();
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
        os.close();
    }
    /**
     * Método responsável por atualizar os dados de uma atividade no arquivo.
     * @param atividade - Instância de atividade com os dados já atualizados.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void update(Atividade atividade) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = this.list();
        for(Iterator<Atividade> i = atividades.iterator(); i.hasNext();){
            Atividade a = i.next();
            if(atividade.getId().equals(a.getId()))
            {
                a.setConvidados(atividade.getConvidados());
                a.setData(atividade.getData());
                a.setLocal(atividade.getLocal());
                a.setDescricao(atividade.getDescricao());
                a.setHoraFim(atividade.getHoraFim());
                a.setHoraInicio(atividade.getHoraInicio());
                a.setConvidados(atividade.getConvidados());
                a.setTipo(atividade.getTipo());
            }
        }
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(arquivo));
        os.writeObject(atividades);
    }

    /**
     * Método responsável por pegar uma Lista de atividades contendo todas as atividades
     * cadastradas por todos os usuários no arquivo.
     * @return - List de Atividade contendo todas as atividades cadastradas no sistema.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
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

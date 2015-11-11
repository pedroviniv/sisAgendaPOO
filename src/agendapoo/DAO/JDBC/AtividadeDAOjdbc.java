/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO.JDBC;

import agendapoo.Banco.FactoryConnection;
import agendapoo.DAO.IAtividadeDAO;
import agendapoo.Model.Atividade;
import agendapoo.Model.TipoAtividade;
import agendapoo.Model.Usuario;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *  Classe responsável pela persistência e manipulação de atividades persistidas no 
 *  Banco de dados. 
 * @author kieckegard
 */
public class AtividadeDAOjdbc implements IAtividadeDAO

{

    private PreparedStatement pstm;
    
    /**
     * Método responsável por puxar todas as atividades do banco de dados que foram
     * cadastradas pelo usuário passado por parâmetro.
     * @param usuario - Usuário cujas atividades devem ser recuperadas
     * @return - List de Atividade com todas as atividades cadastradas pelo usuário
     * passado por parâmetro.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public List<Atividade> list(Usuario usuario) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM Atividade WHERE email_usuario=?";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, usuario.getEmail());
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            //descricao,data,local,horaInicio,horaFim,convidados
            String descricao = rs.getString("descricao");
            String local = rs.getString("local");
            LocalDate data = rs.getDate("data").toLocalDate();
            LocalTime horaInicio = rs.getTime("horaInicio").toLocalTime();
            LocalTime horaFim = rs.getTime("horaFim").toLocalTime();
            String id = rs.getString("id");
            TipoAtividade tipo = TipoAtividade.valueOf(rs.getString("tipo"));
            Atividade a = new Atividade(descricao,local,data,horaInicio,horaFim,tipo,usuario);
            a.setId(id);
            List<String> convidados = getConvidados(a.getId());
            a.setConvidados(convidados);
            atividades.add(a);
        }
        rs.close();
        pstm.close();
        return atividades;
    }
    
    public List<String> getConvidados(String id) throws SQLException{
        List<String> convidados = new ArrayList<>();
        String sql = "SELECT email FROM Atividade_Convidado WHERE id_atividade = ?";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            convidados.add(rs.getString("email"));
        }
        rs.close();
        pstm.close();
        return convidados;
    }

    @Override
    public void add(Atividade obj) throws SQLException, IOException, ClassNotFoundException
    {
       //descricao,data,local,horaInicio,horaFim,convidados
        String sql = "INSERT INTO Atividade VALUES(?,?,?,?,?,?,?,?)";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, obj.getId());
        pstm.setString(2, obj.getDescricao());
        pstm.setString(3, obj.getLocal());
        pstm.setDate(4, java.sql.Date.valueOf(obj.getData()));
        pstm.setTime(5, java.sql.Time.valueOf(obj.getHoraInicio()));
        pstm.setTime(6, java.sql.Time.valueOf(obj.getHoraFim()));
        pstm.setString(7, obj.getUsuario().getEmail());
        pstm.setString(8, obj.getTipo().name());
        
        
        pstm.executeUpdate();
        addConvidados(obj);
    }
    
    /**
     * Método responsável por salvar os convidados de uma atividade no banco
     * à tabela Atividade_convidado que possui a relação entre todas as atividades 
     * e seus respectivos convidados.
     * @param atividade - Instância da Atividade com, Atividade possui uma Lista de Convidados
     * que é acessada através do método get.
     * @throws SQLException 
     */
    private void addConvidados(Atividade atividade) throws SQLException{
        for(String convidado : atividade.getConvidados()){
            String sql = "INSERT INTO Atividade_Convidado VALUES(?,?)";
            pstm = FactoryConnection.getConnection().prepareCall(sql);
            pstm.setString(1, atividade.getId());
            pstm.setString(2, convidado);
            pstm.executeUpdate();
        }
        pstm.close();
    }

    /**
     * Método responsável por deletar uma atividade persistida no banco de dados.
     * A procura da atividade passada por parâmetro no banco é feita através do atributo Id
     * de Atividade.
     * @param atividade - Instância de atividade que deve ser removida do banco.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void delete(Atividade atividade) throws SQLException, IOException, ClassNotFoundException
    {
        deleteConvidados(atividade);
        String sql = "DELETE FROM ATIVIDADE WHERE id = ?";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, atividade.getId());
        pstm.executeUpdate();
    }

    /**
     * Método responsável por atualizar todos os dados que foram alterados da instância de Atividade
     * no banco de dados.
     * A procura da atividade passada por parâmetro no banco é feita através do atributo Id
     * de Atividade.
     * @param atividade - Instância de atividade que deve ser atualizada no banco.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Override
    public void update(Atividade atividade) throws SQLException, IOException, ClassNotFoundException
    {
        //Deleta convidados anteriores
        this.deleteConvidados(atividade);
        //Adiciona convidados atualizados
        this.addConvidados(atividade);
        //Atualiza dados de atividade
        String sql = "UPDATE ATIVIDADE SET descricao=?, local=?, data=?, horaInicio=?, horaFim=?, tipo=? WHERE id = ?";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1,atividade.getDescricao());
        pstm.setString(2,atividade.getLocal());
        pstm.setDate(3, java.sql.Date.valueOf(atividade.getData()));
        pstm.setTime(4, java.sql.Time.valueOf(atividade.getHoraInicio()));
        pstm.setTime(5, java.sql.Time.valueOf(atividade.getHoraFim()));
        pstm.setString(6, atividade.getTipo().name());
        pstm.executeUpdate();
    }

    @Override
    public List<Atividade> list() throws SQLException, IOException, ClassNotFoundException
    {
        throw new UnsupportedOperationException("Not implemented yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @param a
     * @throws SQLException 
     */
    public void deleteConvidados(Atividade a) throws SQLException{
        String sql = "DELETE FROM ATIVIDADE_CONVIDADO WHERE id_atividade = ?";
        pstm = FactoryConnection.getConnection().prepareCall(sql);
        pstm.setString(1, a.getId());
        pstm.executeUpdate();
    }

    @Override
    public void deleteByUser(Usuario u) throws SQLException, IOException, ClassNotFoundException
    {
        //pega lista que são do usuario u
        List<Atividade> atividades = list(u);
        //percorre lista resultante
        for(Atividade a : atividades){
            //deleta convidados de a
            deleteConvidados(a);
            //delete a
            delete(a);
        }
    }
    
}

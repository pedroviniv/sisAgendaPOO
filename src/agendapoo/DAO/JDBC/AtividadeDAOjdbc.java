/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO.JDBC;

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
 *
 * @author kieckegard
 */
public class AtividadeDAOjdbc implements IAtividadeDAO

{

    private PreparedStatement pstm;
    
    @Override
    public List<Atividade> list(Usuario u) throws SQLException, IOException, ClassNotFoundException
    {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM Atividade WHERE email_usuario=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1, u.getEmail());
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
            Atividade a = new Atividade(descricao,local,data,horaInicio,horaFim,tipo,u);
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
        pstm = DAOConnection.getConnection().prepareCall(sql);
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
        pstm = DAOConnection.getConnection().prepareCall(sql);
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
    
    private void addConvidados(Atividade a) throws SQLException{
        for(String convidado : a.getConvidados()){
            String sql = "INSERT INTO Atividade_Convidado VALUES(?,?)";
            pstm = DAOConnection.getConnection().prepareCall(sql);
            pstm.setString(1, a.getId());
            pstm.setString(2, convidado);
            pstm.executeUpdate();
        }
        pstm.close();
    }

    @Override
    public void delete(Atividade obj) throws SQLException, IOException, ClassNotFoundException
    {
        deleteConvidados(obj);
        String sql = "DELETE FROM ATIVIDADE WHERE id = ?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1, obj.getId());
        pstm.executeUpdate();
    }

    @Override
    public void update(Atividade obj) throws SQLException, IOException, ClassNotFoundException
    {
        //Deleta convidados anteriores
        this.deleteConvidados(obj);
        //Adiciona convidados atualizados
        this.addConvidados(obj);
        //Atualiza dados de atividade
        String sql = "UPDATE ATIVIDADE SET descricao=?, local=?, data=?, horaInicio=?, horaFim=?, tipo=? WHERE id = ?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1,obj.getDescricao());
        pstm.setString(2,obj.getLocal());
        pstm.setDate(3, java.sql.Date.valueOf(obj.getData()));
        pstm.setTime(4, java.sql.Time.valueOf(obj.getHoraInicio()));
        pstm.setTime(5, java.sql.Time.valueOf(obj.getHoraFim()));
        pstm.setString(6, obj.getTipo().name());
        pstm.executeUpdate();
    }

    @Override
    public List<Atividade> list() throws SQLException, IOException, ClassNotFoundException
    {
        throw new UnsupportedOperationException("Not implemented yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void deleteConvidados(Atividade a) throws SQLException{
        String sql = "DELETE FROM ATIVIDADE_CONVIDADO WHERE id_atividade = ?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
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

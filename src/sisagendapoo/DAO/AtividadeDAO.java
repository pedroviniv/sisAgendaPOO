/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import sisagendapoo.Model.Atividade;
import sisagendapoo.Model.TipoAtividade;
import sisagendapoo.Model.Usuario;

/**
 *
 * @author kieckegard
 */
public class AtividadeDAO implements IAtividadeDAO
{

    private PreparedStatement pstm;
    private PreparedStatement pstm2;

    @Override
    public void add(Atividade obj) throws SQLException
    {
        String sql = "INSERT INTO Atividade(descricao,data,horaInicio,horaFinal,local,tipo,usuario_email) VALUES(?,?,?,?,?,?,?)";
        pstm = DAOConnection.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pstm.setString(1, obj.getDescricao());
        pstm.setDate(2, java.sql.Date.valueOf(obj.getData()));
        pstm.setTime(3, java.sql.Time.valueOf(obj.getHoraInicio()));
        pstm.setTime(4, java.sql.Time.valueOf(obj.getHoraFim()));
        pstm.setString(5, obj.getLocal());
        pstm.setString(6, obj.getTipo().name());
        pstm.setString(7, obj.getUsuario().getEmail());
        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        int id = 0;
        if (rs.next())
        {
            id = rs.getInt("id");
        }
        obj.setId(id);
        pstm.close();
        /*
         preciso pegar a id que o banco gerou pra atividade e setar na atividade,
         caso eu não faça isso o método abaixo não vai funcionar, afinal, ele usa 
         o id da atividade pra poder pegar os apenas os convidados dessa atividade.
         */
        addConvidados(obj);
    }

    public void addConvidados(Atividade obj) throws SQLException
    {
        System.out.println(obj.getConvidados());
        for (String email : obj.getConvidados())
        {
            System.out.println(email);
            String sql = "INSERT INTO Atividade_Convidado VALUES(?,?)";
            pstm = DAOConnection.getConnection().prepareCall(sql);
            pstm.setInt(1, obj.getId());
            pstm.setString(2, email);
            pstm.executeUpdate();
        }
        pstm.close();
    }

    @Override
    public void remove(Atividade obj) throws SQLException
    {
        removeAtividadeConvidados(obj.getId());
        String sql = "DELETE FROM Atividade WHERE id=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setInt(1, obj.getId());
        pstm.executeUpdate();
        pstm.close();
    }
    
    public void removeAtividadeConvidados(int id) throws SQLException{
        String sql = "DELETE FROM ATIVIDADE_CONVIDADO WHERE atividade_id=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setInt(1,id);
        pstm.executeUpdate();
    }

    @Override
    public void update(Atividade obj) throws SQLException
    {
        removeAtividadeConvidados(obj.getId());
        addConvidados(obj);
        String sql = "UPDATE ATIVIDADE SET horaInicio=?, horaFinal=?, data=?, local=?, tipo=? WHERE id=?";
        
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setTime(1, java.sql.Time.valueOf(obj.getHoraInicio()));
        pstm.setTime(2, java.sql.Time.valueOf(obj.getHoraFim()));
        pstm.setDate(3, java.sql.Date.valueOf(obj.getData()));
        pstm.setString(4, obj.getLocal());
        pstm.setString(5, obj.getTipo().name());
        pstm.setInt(6, obj.getId());
        pstm.executeUpdate();
    }

    @Override
    public List<Atividade> list() throws SQLException
    {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM Atividade a JOIN Usuario u ON a.usuario_email = u.email";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        ResultSet rs = pstm.executeQuery();
        while (rs.next())
        {
            String email = rs.getString("email");
            String nome = rs.getString("nome");
            String usuario = rs.getString("usuario");
            String senha = rs.getString("Senha");
            LocalDate nascimento = rs.getDate("dataNascimento").toLocalDate();
            String descricao = rs.getString("descricao");
            String tipo = rs.getString("tipo");
            //etc
            Usuario u = new Usuario();
            Atividade a = new Atividade();
            atividades.add(a);
        }
        pstm.close();
        return atividades;
    }

    public List<String> getConvidados(int atividadeId) throws SQLException
    {
        List<String> convidados = new ArrayList<>();
        String sql = "SELECT email FROM ATIVIDADE_CONVIDADO WHERE atividade_id=?";
        pstm2 = DAOConnection.getConnection().prepareCall(sql);
        pstm2.setInt(1, atividadeId);
        ResultSet rs2 = pstm2.executeQuery();
        while (rs2.next())
        {
            convidados.add(rs2.getString("email"));
        }
        pstm2.close();
        return convidados;
    }

    @Override
    public List<Atividade> list(Usuario u) throws SQLException
    {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM Atividade a WHERE a.usuario_email=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setString(1, u.getEmail());
        ResultSet rs = pstm.executeQuery();
        while (rs.next())
        {
            int id = rs.getInt("id");
            String descricao = rs.getString("descricao");
            LocalDate data = rs.getDate("data").toLocalDate();
            LocalTime horaInicio = rs.getTime("horaInicio").toLocalTime();
            LocalTime horaFim = rs.getTime("horaFinal").toLocalTime();
            List<String> convidados = getConvidados(id);
            String tipo = rs.getString("tipo");
            String local = rs.getString("local");
            TipoAtividade type = TipoAtividade.valueOf(tipo);
            Atividade a = new Atividade(data, horaInicio, horaFim, local, type, descricao, u);
            a.setId(id);
            a.setConvidao(convidados);
            atividades.add(a);
            System.out.println(a.getDescricao());
        }
        pstm.close();
        return atividades;
    }

    @Override
    public Atividade getAtividadeById(int id,Usuario u) throws SQLException
    {
        String sql = "SELECT * FROM Atividade WHERE id=?";
        pstm = DAOConnection.getConnection().prepareCall(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();  
        List<String> convidados = getConvidados(id);
        if(rs.next()){
            String tipo = rs.getString("tipo");
            String local = rs.getString("local");
            String descricao = rs.getString("descricao");
            LocalDate data = rs.getDate("data").toLocalDate();
            LocalTime horaInicio = rs.getTime("horaInicio").toLocalTime();
            LocalTime horaFim = rs.getTime("horaFinal").toLocalTime();
            Atividade a = new Atividade(data,horaInicio,horaFim,local,TipoAtividade.valueOf(tipo),descricao,u);
            a.setConvidao(convidados);
            a.setId(id);
            return a;
        }return null;
    }

}

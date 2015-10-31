/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO;

import agendapoo.Model.Atividade;
import agendapoo.Model.Usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public interface IAtividadeDAO extends DAO<Atividade>
{
    List<Atividade> list(Usuario u) throws SQLException, IOException, ClassNotFoundException;
    void deleteByUser(Usuario u) throws SQLException, IOException, ClassNotFoundException;
}

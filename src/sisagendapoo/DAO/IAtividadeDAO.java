/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.DAO;

import java.sql.SQLException;
import java.util.List;
import sisagendapoo.Model.Atividade;
import sisagendapoo.Model.Usuario;

/**
 *
 * @author kieckegard
 */
public interface IAtividadeDAO extends DAO<Atividade>
{
    List<Atividade> list(Usuario u) throws SQLException;
}

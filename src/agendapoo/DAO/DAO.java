/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kieckegard
 */
public interface DAO<T>
{
    void add(T obj) throws SQLException, IOException, ClassNotFoundException;
    void delete(T obj) throws SQLException, IOException, ClassNotFoundException;
    void update(T obj) throws SQLException, IOException, ClassNotFoundException;
    List<T> list() throws SQLException, IOException, ClassNotFoundException;
}

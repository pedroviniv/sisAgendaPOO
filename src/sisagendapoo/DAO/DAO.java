/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.DAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kieckegard
 * @param <T>
 */
public interface DAO<T>
{
    void add(T obj) throws SQLException, IOException;
    void remove(T obj)throws SQLException, IOException;
    void update(T obj)throws SQLException, IOException;
    List<T> list()throws SQLException, IOException, ClassNotFoundException;
}

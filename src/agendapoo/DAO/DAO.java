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
    /**
     * escritura de método responsável por persistir um objeto qualquer
     * @param obj - Instancia de um objeto a ser salvo
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    void add(T obj) throws SQLException, IOException, ClassNotFoundException;
    /**
     * escritura de método responsável por deletar um objeto que estiver persistido.
     * @param obj - Instância do objeto a ser deletado
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    void delete(T obj) throws SQLException, IOException, ClassNotFoundException;
    /**
     * Escritura de método responsável por atualizar as informações de um objeto persistido.
     * @param obj - Instância do objeto com as informações atualizadas.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    void update(T obj) throws SQLException, IOException, ClassNotFoundException;
    /**
     * Retorna uma lista de objetos persistidos.
     * @return List de Object 
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    List<T> list() throws SQLException, IOException, ClassNotFoundException;
}

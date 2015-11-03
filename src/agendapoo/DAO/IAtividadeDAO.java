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
    /**
     * Escritura de método responsável por retornar uma Lista de Atividades de um Usuário.
     * @param usuario - Instância do Usuario o qual você obter as atividades
     * @return List de Atividade do Usuario passado por parâmetro
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    List<Atividade> list(Usuario usuario) throws SQLException, IOException, ClassNotFoundException;
    /**
     * Escritura de método responsável por deletar todas as atividades persistidas do Usuário passado
     * passado por parâmetro.
     * @param usuario - Instância do usuário cujas atividades devem ser deletadas.
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    void deleteByUser(Usuario usuario) throws SQLException, IOException, ClassNotFoundException;
}

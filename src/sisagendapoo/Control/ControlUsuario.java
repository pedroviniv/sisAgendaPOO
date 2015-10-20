/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Control;

import Exceptions.TimeInterferenceException;
import java.io.IOException;
import java.sql.SQLException;
import sisagendapoo.Model.Atividade;
import sisagendapoo.Model.Usuario;

/**
 *
 * @author kieckegard
 */
public class ControlUsuario
{
    private final ControlAtividade atividadeControl;
   
    public ControlUsuario()
    {
        atividadeControl = new ControlAtividade();
    }
    
    public void cadastraAtividade(Atividade a, Usuario u) throws SQLException, IOException, TimeInterferenceException
    {
        atividadeControl.cadastraAtividade(a, u);
    }
    
    public void removeAtividade(Atividade a) throws SQLException, IOException, TimeInterferenceException{
        atividadeControl.removeAtividade(a);
    }
}

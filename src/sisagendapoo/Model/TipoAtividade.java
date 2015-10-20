/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.Model;

import java.awt.Color;

/**
 *
 * @author kieckegard
 */
public enum TipoAtividade
{

    ACADEMICO
            {
                public Color getColor()
                {
                    return Color.blue;
                }
            }
   ,PROFISSIONAL
            {
                public Color getColor()
                {
                    return Color.red;
                }
            }
   ,PESSOAL
           {
                public Color getColor()
                {
                    return Color.yellow;
                }
           }

}

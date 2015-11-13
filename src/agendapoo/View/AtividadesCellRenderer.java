/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.View;

import agendapoo.Model.Atividade;
import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Classe que herda de DefaultTableCellRenderer e sobrescreve o método getTableCellRendererComponent no intuito
 * de costumizar a criação de todas as células da tabela seguindo as regras de negócio da minha aplicação.
 * @author kieckegard
 */
public class AtividadesCellRenderer extends DefaultTableCellRenderer
{
    private final JTable jTableAtividades;
    
    /**
     * Recebe por parâmetro a tabela que terá a renderização modificada e seta na
     * jTableAtividades;
     * @param tabela 
     */
    public AtividadesCellRenderer(JTable tabela){
        this.jTableAtividades = tabela;
    }
    
    /**
     * Método responsável por retornar o componente referente a renderização da tabela,
     * De acordo com o tipo da atividade ele seta uma cor no background diferente e caso 
     * o compromisso esteja atrasado, ou seja, a hora de início da atividade seja menor que a hora atual
     * a cor fica amarela indicando atraso, caso você tenha perdido um compromisso a coluna que indica as horas 
     * da atividade ficam vermelha indicando que o compromisso foi perdido.
     * PROFISSIONAL - rgba(202,175,215)
     * ACADEMICO - rgba(168,187,220)
     * PESSOAL - rgba(173,222,201)
     * ATRASO - rgba(213,224,140)
     * PERDA DO COMPROMISSO - rgba(213,58,58)
     * @param table
     * @param value
     * @param isSelected
     * @param hasFocus
     * @param row
     * @param col
     * @return Component com as alterações de cores da tabela.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        
                DefaultTableModel dataModel = (DefaultTableModel)jTableAtividades.getModel();
                Atividade a = (Atividade)dataModel.getValueAt(row, 0);
                switch(a.getTipo()){
                    case PROFISSIONAL:
                        if(isSelected)
                            setBackground(new Color(108,87,117));
                        else
                            setBackground(new Color(202,175,215));
                        break;
                    case ACADEMICO:
                        if(isSelected)
                            setBackground(new Color(97,127,172));
                        else
                            setBackground(new Color(168,187,220));
                        break;
                    case PESSOAL:
                        if(isSelected)
                            setBackground(new Color(93,171,137));
                        else
                            setBackground(new Color(173,222,201));
                        break;
                }
                Period p = Period.between(LocalDate.now(),a.getData());
                if(p.getDays() == 0){
                    if(a.getHoraInicio().isBefore(LocalTime.now()) && a.getHoraFim().isAfter(LocalTime.now())){
                        if(col==2 || col==3)
                            setBackground(new Color(213,224,140));
                    }
                    else if(a.getHoraFim().isBefore(LocalTime.now())){
                        if(col==2 || col==3){
                            setBackground(new Color(213,58,58));
                        }
                    }
                }
                else if(p.getDays()<0)
                   if(col > 0 && col < 4)
                        setBackground(new Color(213,58,58)); 
                return this;
    }
}

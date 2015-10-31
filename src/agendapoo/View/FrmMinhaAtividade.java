/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.View;

import agendapoo.Control.AtividadeController;
import agendapoo.Control.ControlAtividade;
import agendapoo.Exceptions.InvalidTimeRangeException;
import agendapoo.Exceptions.TimeInterferenceException;
import datechooser.model.exeptions.IncompatibleDataExeption;
import datechooser.model.multiple.PeriodSet;
import java.awt.Color;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import agendapoo.Model.Atividade;
import agendapoo.Model.TipoAtividade;
import java.time.format.DateTimeParseException;
import javax.swing.ImageIcon;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author kieckegard
 */
public class FrmMinhaAtividade extends javax.swing.JFrame
{
    
    private final Color colorRed;
    private final Color colorGreen;
    private final Color colorBlue;
    private final Atividade selectedAtividade;
    private DefaultListModel<String> listModel;
    private final FrmUsuario usuarioMain;
    private String currentEmail;
    
    public FrmMinhaAtividade(Atividade a, FrmUsuario janela)
    {
        initComponents();
        setFrameIcon();
        colorGreen = new Color(121,175,108);
        colorRed = new Color(185,108,108);
        colorBlue = new Color(97,128,176);
        this.selectedAtividade = a;
        loadAtividadeDescricao();
        loadSelectedAtividade(selectedAtividade);
        this.usuarioMain = janela;
    }
    
    private void setFrameIcon(){
        ImageIcon icon = new ImageIcon("src//agendapoo//imgs//file_edit.png");
        this.setIconImage(icon.getImage());
    }
    
    private void loadAtividadeDescricao(){
        lblDescricao.setText(selectedAtividade.getDescricao());
        if(selectedAtividade.getTipo() == TipoAtividade.ACADEMICO)
            lblDescricao.setForeground(colorBlue);
        else if(selectedAtividade.getTipo() == TipoAtividade.PROFISSIONAL)
            lblDescricao.setForeground(colorRed);
        else
            lblDescricao.setForeground(colorGreen);
    }
    
    private void loadSelectedAtividade(Atividade selectedAtividade){
        textLocal.setText(selectedAtividade.getLocal());
        textHoraInicio.setText(selectedAtividade.getHoraInicio().toString());
        textHoraFinal.setText(selectedAtividade.getHoraFim().toString());
        loadListConvidados(selectedAtividade.getConvidados());
        comboTipoAtividade.setSelectedItem(selectedAtividade.getTipo().name());
        loadDate();
    }
    
    private void loadDate(){
        //pick an istance of Calendar w/ the current System Time
        Calendar calendar = Calendar.getInstance();
        //picks an instance of Date w/ my SelectedAtividade's LocalDate value.
        Date d = Date.valueOf(selectedAtividade.getData());
        //Setting the Calendar time to my Date
        calendar.setTime(d);
        //Instanciate a new PeriodSet
        PeriodSet p = new PeriodSet();
        //Added my calendar w/ my new Date to my PeriodSet
        p.add(calendar);
        //finally setDefaultPeriods to my new PeriodSet time wich throws a RuntimeException
        try{
            textData.setDefaultPeriods(p);
        }catch(IncompatibleDataExeption ide){
            JOptionPane.showMessageDialog(this, ide.getMessage());
        }
    }
    
    private void loadListConvidados(List<String> convidados){
        listModel = new DefaultListModel<>();
        for(String email : convidados){
            listModel.addElement(email);
        }
        jListConvidados.setModel(listModel);
    }
    
    private void updateAcessFields(){
        textData.setEnabled(true);
        textEmail.setEnabled(true);
        textHoraFinal.setEditable(true);
        textHoraInicio.setEditable(true);
        textLocal.setEditable(true);
        jListConvidados.setEnabled(true);
        comboTipoAtividade.setEnabled(true);
    }
    
    private void updateAcessButtons(){
        btnAddConvidado.setEnabled(true);
        btnRemove.setEnabled(false);
        btnSaveChanges.setEnabled(true);
        btnRemoveConvidado.setEnabled(false);
        btnUpdate.setEnabled(false);
    }
    
    private void deleteAtividade() throws SQLException, IOException, ClassNotFoundException, EmailException{
        AtividadeController ca = new ControlAtividade();
        ca.deletaAtividade(selectedAtividade);
    }
    
    private List<String> getStringListFromListModel(){
        Object[] a = listModel.toArray();
        List<String> lista = new ArrayList<>();
        for(Object o : a){
            lista.add(o.toString());
        }return lista;
    }
    
    private void saveChanges(Atividade a) throws SQLException, IOException, ClassNotFoundException, EmailException, InvalidTimeRangeException, TimeInterferenceException{
        List<String> lista = new ArrayList<>();
        getStringListFromListModel().stream().forEach((email) ->
        {
            lista.add(email);
        });
        
        selectedAtividade.setConvidados(lista);
        selectedAtividade.setData(LocalDate.parse(textData.getText(),DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        selectedAtividade.setHoraInicio(LocalTime.parse(textHoraInicio.getText()));
        selectedAtividade.setHoraFim(LocalTime.parse(textHoraFinal.getText()));
        selectedAtividade.setLocal(textLocal.getText());
        System.out.println("Novo local: "+selectedAtividade.getLocal());
        selectedAtividade.setTipo(TipoAtividade.valueOf(comboTipoAtividade.getSelectedItem().toString()));
        AtividadeController ca = new ControlAtividade();
        ca.atualizaAtividade(a);
    }
    
    private String selectedEmail(){
        return (String)jListConvidados.getSelectedValue();
    }
    
    private void someEmailSelected(){
        btnRemoveConvidado.setVisible(true);
    }
    
    private boolean isEmailValid(String email){
        for(String mail : getStringListFromListModel())
            if(email.equals(mail))
                return false;
        return true;
    }
    
    private void removeContato()
    {
        String s = selectedEmail();
        if(s != null){
            listModel.removeElement(s);
            selectedAtividade.getConvidados().remove(s);
            jListConvidados.setModel(listModel);
        }
    }    
    
    
    private void removeContactAcessButtons(){
        btnRemoveConvidado.setEnabled(true);
        btnAddConvidado.setEnabled(false);
    }

    
    //All generated Swing code
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblDescricao = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        textLocal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        textData = new datechooser.beans.DateChooserCombo();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textHoraInicio = new javax.swing.JFormattedTextField();
        textHoraFinal = new javax.swing.JFormattedTextField();
        comboTipoAtividade = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListConvidados = new javax.swing.JList();
        btnRemoveConvidado = new javax.swing.JButton();
        textEmail = new javax.swing.JTextField();
        btnAddConvidado = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnSaveChanges = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minha Atividade");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel1.setToolTipText("Gerenciamento da Atividade");

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        jLabel1.setText("Atividade: ");

        lblDescricao.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblDescricao.setForeground(new java.awt.Color(185, 108, 108));
        lblDescricao.setText("Sua Atividade");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescricao)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblDescricao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel2.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jLabel2.setText("Local:");

        textLocal.setEditable(false);
        textLocal.setFont(new java.awt.Font("Yu Gothic", 0, 10)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Data:");

        textData.setNothingAllowed(false);
        textData.setFormat(2);
        textData.setEnabled(false);
        textData.setNavigateFont(new java.awt.Font("Serif", java.awt.Font.PLAIN, 10));

        jLabel4.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Hora de Inicio:");

        jLabel5.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jLabel5.setText("Hora do Fim:");

        textHoraInicio.setEditable(false);
        try
        {
            textHoraInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }

        textHoraFinal.setEditable(false);
        try
        {
            textHoraFinal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }

        comboTipoAtividade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PESSOAL", "PROFISSIONAL", "ACADEMICO" }));
        comboTipoAtividade.setEnabled(false);
        comboTipoAtividade.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                comboTipoAtividadeActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Yu Gothic Light", 0, 12)); // NOI18N
        jLabel8.setText("Tipo:");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Convidados"));

        jListConvidados.setEnabled(false);
        jListConvidados.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jListConvidadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListConvidados);

        btnRemoveConvidado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agendapoo/imgs/delete.png"))); // NOI18N
        btnRemoveConvidado.setText("deletar");
        btnRemoveConvidado.setEnabled(false);
        btnRemoveConvidado.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnRemoveConvidadoActionPerformed(evt);
            }
        });

        textEmail.setEnabled(false);

        btnAddConvidado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agendapoo/imgs/add_16.png"))); // NOI18N
        btnAddConvidado.setText("Adicionar");
        btnAddConvidado.setEnabled(false);
        btnAddConvidado.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddConvidadoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Yu Gothic", 0, 12)); // NOI18N
        jLabel6.setText("Email:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(textEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddConvidado))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemoveConvidado)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddConvidado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveConvidado)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(textLocal)
                    .addComponent(textData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(textHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel8)
                    .addComponent(comboTipoAtividade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textData, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textHoraFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboTipoAtividade, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnSaveChanges.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agendapoo/imgs/002_10.png"))); // NOI18N
        btnSaveChanges.setText("Salvar");
        btnSaveChanges.setEnabled(false);
        btnSaveChanges.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSaveChangesActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agendapoo/imgs/File-edit-icon.png"))); // NOI18N
        btnUpdate.setText("Atualizar");
        btnUpdate.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agendapoo/imgs/Delete-icon.png"))); // NOI18N
        btnRemove.setText("Remover");
        btnRemove.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnRemoveActionPerformed(evt);
            }
        });

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agendapoo/imgs/Users-Exit-icon (1).png"))); // NOI18N
        btnCancel.setText("Sair/Cancelar");
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnSaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboTipoAtividadeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_comboTipoAtividadeActionPerformed
    {//GEN-HEADEREND:event_comboTipoAtividadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoAtividadeActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUpdateActionPerformed
    {//GEN-HEADEREND:event_btnUpdateActionPerformed
        updateAcessFields();
        updateAcessButtons();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRemoveActionPerformed
    {//GEN-HEADEREND:event_btnRemoveActionPerformed
        try{
            int i = JOptionPane.showConfirmDialog(this, "Tem certeza que desejas remover essa atividade?", "Aviso", JOptionPane.YES_NO_OPTION);
            switch(i){
                case JOptionPane.YES_OPTION:
                    JOptionPane.showMessageDialog(this, "Atividade removida com sucesso! :)");
                    JOptionPane.showMessageDialog(null, "Estamos enviando um e-mail avisando aos convidados que a atividade foi desmarcada!");
                    deleteAtividade();
                    JOptionPane.showMessageDialog(this, "Mensagem enviada!");     
                    usuarioMain.loadAtividades();
                    this.dispose();
                    break;
            }
        }
        catch(SQLException | IOException | ClassNotFoundException | EmailException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnSaveChangesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveChangesActionPerformed
    {//GEN-HEADEREND:event_btnSaveChangesActionPerformed
        try{
            int opcao = JOptionPane.showConfirmDialog(this,"Tem certeza que desejas atualizar as informações dessa atividade?","Aviso",JOptionPane.YES_NO_OPTION);
            switch(opcao){
                case JOptionPane.YES_OPTION:
                    saveChanges(selectedAtividade);
                    JOptionPane.showMessageDialog(this, "Atualização realizada com sucesso!");
                    usuarioMain.loadAtividades();
                    this.dispose();
                    break;
            }
        }
        catch(SQLException | IOException | ClassNotFoundException | InvalidTimeRangeException | TimeInterferenceException | EmailException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
        catch(DateTimeParseException dtpe){
            JOptionPane.showMessageDialog(this, "Por favor, Insira uma hora válida!","Hora Inválida",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveChangesActionPerformed

    private void jListConvidadosMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jListConvidadosMouseClicked
    {//GEN-HEADEREND:event_jListConvidadosMouseClicked
        removeContactAcessButtons();
    }//GEN-LAST:event_jListConvidadosMouseClicked

    private void btnAddConvidadoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddConvidadoActionPerformed
    {//GEN-HEADEREND:event_btnAddConvidadoActionPerformed
        String email = textEmail.getText();
        if(isEmailValid(email)){
            listModel.addElement(email);
            jListConvidados.setModel(listModel);
            textEmail.setText("");
        }
        else
            JOptionPane.showMessageDialog(this, "Esse email já foi adicionado à lista de convidados!");
    }//GEN-LAST:event_btnAddConvidadoActionPerformed

    private void btnRemoveConvidadoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRemoveConvidadoActionPerformed
    {//GEN-HEADEREND:event_btnRemoveConvidadoActionPerformed
        removeContato();
        updateAcessButtons();
    }//GEN-LAST:event_btnRemoveConvidadoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddConvidado;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemoveConvidado;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox comboTipoAtividade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList jListConvidados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescricao;
    private datechooser.beans.DateChooserCombo textData;
    private javax.swing.JTextField textEmail;
    private javax.swing.JFormattedTextField textHoraFinal;
    private javax.swing.JFormattedTextField textHoraInicio;
    private javax.swing.JTextField textLocal;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendapoo.View;

import agendapoo.Control.ControlUsuario;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import agendapoo.Model.Usuario;
import javax.swing.ImageIcon;

/**
 *
 * @author kieckegard
 */
public class FrmMyAccount extends javax.swing.JFrame
{

    private final Usuario loggedUser;
    private final FrmUsuario frmUsuario;
    
    public FrmMyAccount(Usuario loggedUser, FrmUsuario frmUsuario)
    {
        initComponents();
        setFrameIcon();
        this.frmUsuario = frmUsuario;
        this.loggedUser = loggedUser;
        loadUserData();
    }
    
    private void setFrameIcon(){
        ImageIcon icon = new ImageIcon("src//agendapoo//imgs//account-icon2.png");
        this.setIconImage(icon.getImage());
    }
    
    private void loadUserData(){
        textEmail.setText(loggedUser.getEmail());
        textSenha.setText(loggedUser.getSenha());
        textTelefone.setText(loggedUser.getTelefone());
        textDataNascimento.setText(loggedUser.getFormattedDate());
        lblUserName.setText(loggedUser.getNome());
    }
    
    private void updateAcessButtons(){
        btnRemove.setEnabled(false);
        btnSaveChanges.setEnabled(true);
        btnUpdate.setEnabled(false);
    }
    
    private void updateAcessFields(){
        textSenha.setEditable(true);
        textTelefone.setEditable(true);
        textDataNascimento.setEditable(true);
    }
    
    private void saveChanges(Usuario loggedUser) throws DateTimeParseException, SQLException, IOException, ClassNotFoundException{
        LocalDate dataNascimento = LocalDate.parse(textDataNascimento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String senha = textSenha.getText();
        String telefone = textTelefone.getText();
        loggedUser.setSenha(senha);
        loggedUser.setTelefone(telefone);
        loggedUser.setDataNascimento(dataNascimento);
        ControlUsuario control = new ControlUsuario();
        control.atualizaUsuario(loggedUser);
    }
    
    private void deleteUsuario() throws SQLException, IOException, ClassNotFoundException{
        ControlUsuario control = new ControlUsuario();
        control.removeUsuario(loggedUser);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanelMain = new javax.swing.JPanel();
        jPanelTitle = new javax.swing.JPanel();
        lblHello = new javax.swing.JLabel();
        lblUserName = new javax.swing.JLabel();
        btnUpdate = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnSaveChanges = new javax.swing.JButton();
        jPanelUserData = new javax.swing.JPanel();
        lblEmail = new javax.swing.JLabel();
        textEmail = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        lblDataNascimento = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        lblUserIcon = new javax.swing.JLabel();
        textDataNascimento = new javax.swing.JFormattedTextField();
        textSenha = new javax.swing.JTextField();
        textTelefone = new javax.swing.JFormattedTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sua Conta!");
        setResizable(false);

        jPanelTitle.setBorder(new javax.swing.border.MatteBorder(null));

        lblHello.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblHello.setText("Olá, ");

        lblUserName.setFont(new java.awt.Font("Yu Gothic Light", 0, 18)); // NOI18N
        lblUserName.setForeground(new java.awt.Color(131, 175, 120));
        lblUserName.setText("Pedro Arthur F. de Vasconcelos");

        javax.swing.GroupLayout jPanelTitleLayout = new javax.swing.GroupLayout(jPanelTitle);
        jPanelTitle.setLayout(jPanelTitleLayout);
        jPanelTitleLayout.setHorizontalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblHello)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUserName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelTitleLayout.setVerticalGroup(
            jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTitleLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHello)
                    .addComponent(lblUserName))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jPanelUserData.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Seus Dados"), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(153, 0, 102))); // NOI18N

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmail.setText("Email:");

        textEmail.setEditable(false);
        textEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblTelefone.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTelefone.setText("Telefone:");

        lblDataNascimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDataNascimento.setText("Data de Nascimento:");

        lblSenha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSenha.setText("Senha:");

        lblUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/agendapoo/imgs/login-icon.png"))); // NOI18N

        textDataNascimento.setEditable(false);
        try
        {
            textDataNascimento.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }
        textDataNascimento.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textDataNascimento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        textSenha.setEditable(false);
        textSenha.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        textTelefone.setEditable(false);
        try
        {
            textTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##)#####-####")));
        } catch (java.text.ParseException ex)
        {
            ex.printStackTrace();
        }
        textTelefone.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textTelefone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanelUserDataLayout = new javax.swing.GroupLayout(jPanelUserData);
        jPanelUserData.setLayout(jPanelUserDataLayout);
        jPanelUserDataLayout.setHorizontalGroup(
            jPanelUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserDataLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(textDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .addComponent(textEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefone, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDataNascimento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSenha, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textSenha)
                    .addComponent(textTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUserIcon)
                .addGap(67, 67, 67))
        );
        jPanelUserDataLayout.setVerticalGroup(
            jPanelUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelUserDataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelUserDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelUserDataLayout.createSequentialGroup()
                        .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTelefone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDataNascimento))
                    .addGroup(jPanelUserDataLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblUserIcon)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addComponent(btnSaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanelUserData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelUserData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUpdateActionPerformed
    {//GEN-HEADEREND:event_btnUpdateActionPerformed
        updateAcessFields();
        updateAcessButtons();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRemoveActionPerformed
    {//GEN-HEADEREND:event_btnRemoveActionPerformed
        try
        {
            int i = JOptionPane.showConfirmDialog(this, "Tem certeza que desejas remover esse usuário?", "Aviso", JOptionPane.YES_NO_OPTION);
            switch(i)
            {
                case JOptionPane.YES_OPTION:
                deleteUsuario();
                JOptionPane.showMessageDialog(this, "Usuário removido com sucesso! :)");
                JOptionPane.showMessageDialog(this, "Você irá retornar para a tela de login!");
                frmUsuario.dispose();
                this.dispose();
                break;
            }

        }
        catch(SQLException | IOException | ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
    {//GEN-HEADEREND:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveChangesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveChangesActionPerformed
    {//GEN-HEADEREND:event_btnSaveChangesActionPerformed
        try
        {
            int opcao = JOptionPane.showConfirmDialog(this,"Tem certeza que desejas atualizar as informações dessa atividade?","Aviso",JOptionPane.YES_NO_OPTION);
            switch(opcao)
            {
                case JOptionPane.YES_OPTION:
                saveChanges(loggedUser);
                JOptionPane.showMessageDialog(this, "Atualização realizada com sucesso!");
                this.dispose();
                break;
            }
        }
        catch(SQLException | IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(DateTimeParseException dtpe){
            JOptionPane.showMessageDialog(this, "Por favor, insira uma data de nascimento válida!");
        }
    }//GEN-LAST:event_btnSaveChangesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSaveChanges;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelTitle;
    private javax.swing.JPanel jPanelUserData;
    private javax.swing.JLabel lblDataNascimento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblHello;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JFormattedTextField textDataNascimento;
    private javax.swing.JTextField textEmail;
    private javax.swing.JTextField textSenha;
    private javax.swing.JFormattedTextField textTelefone;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisagendapoo.View;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.java.balloontip.BalloonTip;
import sisagendapoo.Control.ControlAtividade;
import sisagendapoo.Control.Login;
import sisagendapoo.Model.Atividade;

/**
 *
 * @author kieckegard
 */
public class FrmUsuario extends javax.swing.JFrame
{
    private final Login login;
    private DefaultTableModel dataModel;
    private BalloonTip balloon;
    
    public FrmUsuario(Login login)
    {
        initComponents();
        dataModel = (DefaultTableModel)jTableAtividades.getModel();
        this.login = login;
        loadlabelName(); 
        loadAtividades();
    }
    
    private void loadlabelName(){
        String userName = login.getLoggedUser().getNome();
        jLabel1.setText(userName);
    }
    
    private void addRow(Atividade a){
        dataModel.addRow(new Object[] {a.getId(),a.getFormattedDate(),a.getHoraInicio().toString(),a.getLocal(),a.getDescricao(),a.getConvidadosString()});   
        jTableAtividades.setModel(dataModel);
    }

    public void loadAtividades(){
        ControlAtividade ca = new ControlAtividade();
        dataModel.setRowCount(0);
        try
        {
            for(Atividade a : ca.orderByDate(ca.atividadesByUser(login.getLoggedUser()))){
                System.out.println(a.getDescricao());
               addRow(a); 
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Não foi possível carregar a lista de atividades!");
        }
    }
    
    private Atividade getAtividadeBySelectedRow() throws SQLException{
        int indexColumn, indexRow;
        indexRow = jTableAtividades.getSelectedRow();
        int id = (int)dataModel.getValueAt(indexRow, 0);
        ControlAtividade ca = new ControlAtividade();
        return ca.getAtividadeById(id, login.getLoggedUser());
    }
    
    
    //All generated Swing code
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanelMain = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAtividades = new javax.swing.JTable();
        btnAddActivity = new javax.swing.JButton();
        lblInformation = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        btnMyAccount = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel2.setFont(new java.awt.Font("Yu Gothic Light", 0, 24)); // NOI18N
        jLabel2.setText("Bem Vindo,");

        jLabel1.setFont(new java.awt.Font("Yu Gothic Light", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(131, 175, 120));
        jLabel1.setText("Pedro Arthur F. de Vasconcelos");

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(0, 0, 0))
        );

        jPanel2.setBorder(new javax.swing.border.MatteBorder(null));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Atividades", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Yu Gothic Light", 1, 11))); // NOI18N

        jTableAtividades.setBackground(new java.awt.Color(240, 240, 240));
        jTableAtividades.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jTableAtividades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "id", "Data", "Hora", "Local", "Descrição", "Convidados"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jTableAtividades.setSelectionBackground(new java.awt.Color(202, 200, 229));
        jTableAtividades.getTableHeader().setReorderingAllowed(false);
        jTableAtividades.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jTableAtividadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableAtividades);
        if (jTableAtividades.getColumnModel().getColumnCount() > 0)
        {
            jTableAtividades.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnAddActivity.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisagendapoo/imgs/file_add.png"))); // NOI18N
        btnAddActivity.setText("Adicionar Atividade");
        btnAddActivity.setFocusable(false);
        btnAddActivity.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnAddActivityActionPerformed(evt);
            }
        });

        lblInformation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisagendapoo/imgs/information.png"))); // NOI18N
        lblInformation.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblInformation.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                lblInformationMouseMoved(evt);
            }
        });
        lblInformation.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                lblInformationMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(lblInformation)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAddActivity, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddActivity, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblInformation, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("Menu");

        btnMyAccount.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        btnMyAccount.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisagendapoo/imgs/account-icon2.png"))); // NOI18N
        btnMyAccount.setText("Sua Conta");
        jMenu1.add(btnMyAccount);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sisagendapoo/imgs/Users-Exit-icon.png"))); // NOI18N
        jMenuItem1.setText("Sair");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblInformationMouseMoved(java.awt.event.MouseEvent evt)//GEN-FIRST:event_lblInformationMouseMoved
    {//GEN-HEADEREND:event_lblInformationMouseMoved
        balloon = new BalloonTip(lblInformation, "Information!");
    }//GEN-LAST:event_lblInformationMouseMoved

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnAddActivityActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddActivityActionPerformed
    {//GEN-HEADEREND:event_btnAddActivityActionPerformed
        FrmCadastroAtividade fca = new FrmCadastroAtividade(login.getLoggedUser(),this);
        fca.setVisible(true);
    }//GEN-LAST:event_btnAddActivityActionPerformed

    private void lblInformationMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_lblInformationMouseExited
    {//GEN-HEADEREND:event_lblInformationMouseExited
        
    }//GEN-LAST:event_lblInformationMouseExited

    private void jTableAtividadesMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTableAtividadesMouseClicked
    {//GEN-HEADEREND:event_jTableAtividadesMouseClicked
        try
        {
            Atividade a = getAtividadeBySelectedRow();
            if(a==null){
                JOptionPane.showMessageDialog(this, "Nenhuma atividade encontrada!");
            }
            else{
                FrmMinhaAtividade frmAtividade = new FrmMinhaAtividade(a,this);
                frmAtividade.setVisible(true);
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jTableAtividadesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddActivity;
    private javax.swing.JMenuItem btnMyAccount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAtividades;
    private javax.swing.JLabel lblInformation;
    // End of variables declaration//GEN-END:variables
}

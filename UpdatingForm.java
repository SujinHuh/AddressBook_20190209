/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rlaqjqdyd2
 */
public class UpdatingForm extends javax.swing.JFrame {
    AddressBook addressBook = new AddressBook();
    DefaultTableModel model = new DefaultTableModel();
    
    public UpdatingForm() {
        int number;
        int index;
        int i=0;
        Personal personal;
        
        initComponents();
        try {
            Load();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatingForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(i<this.addressBook.GetLength()){
            number=i+1;
            personal=this.addressBook.GetAt(i);
        
        if(IDC_TABLE_PERSONALS.getRowCount() >= this.addressBook.GetLength()){
            IDC_TABLE_PERSONALS.setValueAt(number, i, 0);
            IDC_TABLE_PERSONALS.setValueAt(personal.getName(), i, 1);
            IDC_TABLE_PERSONALS.setValueAt(personal.getAddress(), i, 2);
            IDC_TABLE_PERSONALS.setValueAt(personal.getTelephoneNumber(), i, 3);
            IDC_TABLE_PERSONALS.setValueAt(personal.getEmailAddress(), i, 4);
            i++;
        }
        else{
            String inputStr[] = new String[5];
            inputStr[0] = Integer.toString(number);
            inputStr[1] = personal.getName();
            inputStr[2] = personal.getAddress();
            inputStr[3] = personal.getTelephoneNumber();
            inputStr[4] = personal.getEmailAddress();
            DefaultTableModel model=(DefaultTableModel) IDC_TABLE_PERSONALS.getModel();
            model.addRow(inputStr);
        }
        }
        
    }
    
    public void Load() throws SQLException{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("drive on");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","rla789456");
            System.out.println("drive connection successfully");
            String sql="SELECT personal.name,personal.address,personal.telephoneNumber,personal.emailAddress FROM personal;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
    
        while(rs.next()){
            String name = rs.getString("name");
            String address = rs.getString("address");
            String telephoneNumber = rs.getString("telephoneNumber");
            String emailAddress = rs.getString("emailAddress");
            this.addressBook.Record(name, address, telephoneNumber, emailAddress);
        }
           
        rs.close();
        ps.close();
        con.close();
        }catch(ClassNotFoundException e){
            System.out.println("drive connection failed");
            }catch(SQLException e){
            System.out.println("drive off");
            }finally{
        }
    }
    
    public void Insert(int index){
        Connection con;
        PreparedStatement ps;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("drive on");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","rla789456");
            System.out.println("drive connection successfully");
            Personal personal=this.addressBook.GetAt(index);
            String sql="INSERT INTO personal(name,address,telephoneNumber,emailAddress,code) VALUES(?,?,?,?,?);";
            
            ps=con.prepareStatement(sql);
            ps.setString(1, personal.getName());
            ps.setString(2, personal.getAddress());
            ps.setString(3, personal.getTelephoneNumber());
            ps.setString(4, personal.getEmailAddress());
            ps.setString(5, MakeCode());
            ps.execute();
            ps.close();
            con.close();
            }catch(ClassNotFoundException e){
            System.out.println("drive connection failed");
            }catch(SQLException e){
            System.out.println("drive off");
            }finally{
        }
    }
    
  public void Delete(int index){
      Connection con;
      PreparedStatement ps;
      ResultSet rs;
      String code = null;
      int i=0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("drive on");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","rla789456");
            System.out.println("drive connection successfully");
            
            String sql="SELECT personal.code FROM personal;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            while(rs.next() && i<=index){
              code = rs.getString("code");
              i++;
            }
            
            sql="DELETE FROM personal WHERE personal.code=?";
            ps=con.prepareStatement(sql);
            ps.setString(1, code);
            ps.executeUpdate();
           
            rs.close();
            ps.close();
            con.close();
            
        }catch(ClassNotFoundException e){
            System.out.println("drive connection failed");
            }catch(SQLException e){
            System.out.println("drive off");
            }finally{
        }
  }
    
  public String MakeCode() throws SQLException{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        String stringcode="P0001";
        int i=0;
        int codeInt;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("drive on");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","rla789456");
            System.out.println("drive connection successfully");

            String sql="SELECT personal.code FROM personal ORDER BY personal.code DESC;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
        
        if(rs.next()){
           stringcode = rs.getString("code");
           stringcode = stringcode.substring(1, 5);  
           int intcode = Integer.parseInt(stringcode);
           intcode++;
           stringcode = String.format("P%04d", intcode);
           
        }
        rs.close();
        ps.close();
        con.close();
        }catch(ClassNotFoundException e){
            System.out.println("drive connection failed");
            }
        return stringcode;
        }
  
  public void Modify(int index) throws SQLException{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        Personal personal;
        String code = null;
        int i=0;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("drive on");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","rla789456");
            System.out.println("drive connection successfully");

            personal=this.addressBook.GetAt(index);
            String sql="SELECT personal.code FROM personal;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
        while(rs.next() && i<=index){
              code = rs.getString("code");
              i++;
            }
        
            sql="UPDATE personal SET address=?,telephoneNumber=?,emailAddress=? WHERE personal.code=?;";
            ps=con.prepareStatement(sql);
            
            ps.setString(1, IDC_TEXTFIELD_ADDRESS.getText());
            ps.setString(2, IDC_TEXTFIELD_TELEPHONENUMBER.getText());
            ps.setString(3, IDC_TEXTFIELD_EMAILADDRESS.getText());
            ps.setString(4, code);
            ps.executeUpdate();
            
            rs.close();
            ps.close();
            con.close();
        }catch(ClassNotFoundException e){
            System.out.println("drive connection failed");
            }catch(SQLException e){
            System.out.println("drive off");
            }finally{
        }
  }
  
    public void Save() throws SQLException{
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        Personal personal;
        int i=0;
        String code = null;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("drive on");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook","root","rla789456");
            System.out.println("drive connection successfully");
            
            String sql="SELECT personal.code FROM personal;";
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();

            
            sql ="DELETE FROM personal;";
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            
            while(rs.next() && i<this.addressBook.GetLength()){
                personal=this.addressBook.GetAt(i);
                code = rs.getString("code");
                System.out.println("return2");
                sql="INSERT INTO personal(code,name,address,telephoneNumber,emailAddress) VALUES(?,?,?,?,?);";
                ps=con.prepareStatement(sql);
                ps.setString(1, code);
                ps.setString(2, personal.getName());
                ps.setString(3, personal.getAddress());
                ps.setString(4, personal.getTelephoneNumber());
                ps.setString(5, personal.getEmailAddress());
          
                ps.execute();
                i++;
            }
            
            rs.close();
            ps.close();
            con.close();
        }catch(ClassNotFoundException e){
            System.out.println("drive connection failed");
            }catch(SQLException e){
            System.out.println("drive off");
            }finally{
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        IDC_BUTTON_RECORD = new javax.swing.JButton();
        IDC_BUTTON_FIND = new javax.swing.JButton();
        IDC_BUTTON_CORRECT = new javax.swing.JButton();
        IDC_BUTTON_ERASE = new javax.swing.JButton();
        IDC_BUTTON_ARRANGE = new javax.swing.JButton();
        JPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IDC_TEXTFIELD_NAME = new javax.swing.JTextField();
        IDC_TEXTFIELD_ADDRESS = new javax.swing.JTextField();
        IDC_TEXTFIELD_TELEPHONENUMBER = new javax.swing.JTextField();
        IDC_TEXTFIELD_EMAILADDRESS = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        IDC_TABLE_PERSONALS = new javax.swing.JTable();

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("주소록");
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setName("jPanel1"); // NOI18N

        IDC_BUTTON_RECORD.setText("기재하기");
        IDC_BUTTON_RECORD.setName("IDC_BUTTON_RECORD"); // NOI18N
        IDC_BUTTON_RECORD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDC_BUTTON_RECORDActionPerformed(evt);
            }
        });

        IDC_BUTTON_FIND.setText("찾기");
        IDC_BUTTON_FIND.setName("IDC_BUTTON_FIND"); // NOI18N
        IDC_BUTTON_FIND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDC_BUTTON_FINDActionPerformed(evt);
            }
        });
        IDC_BUTTON_FIND.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                IDC_BUTTON_FINDPropertyChange(evt);
            }
        });

        IDC_BUTTON_CORRECT.setText("고치기");
        IDC_BUTTON_CORRECT.setName("IDC_BUTTON_CORRECT"); // NOI18N
        IDC_BUTTON_CORRECT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDC_BUTTON_CORRECTActionPerformed(evt);
            }
        });

        IDC_BUTTON_ERASE.setText("지우기");
        IDC_BUTTON_ERASE.setName("IDC_BUTTON_ERASE"); // NOI18N
        IDC_BUTTON_ERASE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDC_BUTTON_ERASEActionPerformed(evt);
            }
        });

        IDC_BUTTON_ARRANGE.setText("정리하기");
        IDC_BUTTON_ARRANGE.setName("IDC_BUTTON_ARRANGE"); // NOI18N
        IDC_BUTTON_ARRANGE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDC_BUTTON_ARRANGEActionPerformed(evt);
            }
        });

        JPanel2.setToolTipText("개인");
        JPanel2.setName("JPanel2"); // NOI18N

        jLabel1.setText("성명");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("주소");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("전화번호");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("이메일주소");
        jLabel4.setName("jLabel4"); // NOI18N

        IDC_TEXTFIELD_NAME.setName("IDC_TEXTFIELD_NAME"); // NOI18N

        IDC_TEXTFIELD_ADDRESS.setName("IDC_TEXTFIELD_ADDRESS"); // NOI18N

        IDC_TEXTFIELD_TELEPHONENUMBER.setName("IDC_TEXTFIELD_TELEPHONENUMBER"); // NOI18N

        IDC_TEXTFIELD_EMAILADDRESS.setName("IDC_TEXTFIELD_EMAILADDRESS"); // NOI18N

        javax.swing.GroupLayout JPanel2Layout = new javax.swing.GroupLayout(JPanel2);
        JPanel2.setLayout(JPanel2Layout);
        JPanel2Layout.setHorizontalGroup(
            JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanel2Layout.createSequentialGroup()
                .addGroup(JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(IDC_TEXTFIELD_EMAILADDRESS, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(IDC_TEXTFIELD_TELEPHONENUMBER, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                    .addComponent(IDC_TEXTFIELD_ADDRESS)
                    .addComponent(IDC_TEXTFIELD_NAME))
                .addContainerGap())
        );
        JPanel2Layout.setVerticalGroup(
            JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(IDC_TEXTFIELD_NAME, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(IDC_TEXTFIELD_ADDRESS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(IDC_TEXTFIELD_TELEPHONENUMBER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(JPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(IDC_TEXTFIELD_EMAILADDRESS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        DefaultTableModel model = new DefaultTableModel();
        IDC_TABLE_PERSONALS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "번호", "성명", "주소", "전화번호", "이메일주소"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        IDC_TABLE_PERSONALS.setName("IDC_TABLE_PERSONALS"); // NOI18N
        IDC_TABLE_PERSONALS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IDC_TABLE_PERSONALSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(IDC_TABLE_PERSONALS);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(JPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IDC_BUTTON_RECORD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IDC_BUTTON_FIND, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IDC_BUTTON_CORRECT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IDC_BUTTON_ERASE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(IDC_BUTTON_ARRANGE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(IDC_BUTTON_RECORD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IDC_BUTTON_FIND)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IDC_BUTTON_CORRECT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IDC_BUTTON_ERASE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(IDC_BUTTON_ARRANGE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(JPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        JPanel2.getAccessibleContext().setAccessibleName("개인");
        JPanel2.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("개인");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IDC_BUTTON_RECORDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDC_BUTTON_RECORDActionPerformed
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        int number;
        int index;
        
        name = IDC_TEXTFIELD_NAME.getText();
        address = IDC_TEXTFIELD_ADDRESS.getText();
        telephoneNumber = IDC_TEXTFIELD_TELEPHONENUMBER.getText();
        emailAddress = IDC_TEXTFIELD_EMAILADDRESS.getText();

        index = addressBook.Record(name, address, telephoneNumber, emailAddress);
        this.Insert(index);
        Personal personal = addressBook.GetAt(index);
               
        name = personal.getName();
        address = personal.getAddress();
        telephoneNumber = personal.getTelephoneNumber();
        emailAddress = personal.getEmailAddress();
        number = index + 1;
        if(IDC_TABLE_PERSONALS.getRowCount() >= this.addressBook.GetLength()){
        
            IDC_TABLE_PERSONALS.setValueAt(number, index, 0);
            IDC_TABLE_PERSONALS.setValueAt(name, index, 1);
            IDC_TABLE_PERSONALS.setValueAt(address, index, 2);
            IDC_TABLE_PERSONALS.setValueAt(telephoneNumber, index, 3);
            IDC_TABLE_PERSONALS.setValueAt(emailAddress, index, 4);
        }
        else{
            String inputStr[] = new String[5];
            inputStr[0] = Integer.toString(number);
            inputStr[1] = name;
            inputStr[2] = address;
            inputStr[3] = telephoneNumber;
            inputStr[4] = emailAddress;
            DefaultTableModel model=(DefaultTableModel) IDC_TABLE_PERSONALS.getModel();
            model.addRow(inputStr);
        }
    }//GEN-LAST:event_IDC_BUTTON_RECORDActionPerformed

    private void IDC_BUTTON_CORRECTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDC_BUTTON_CORRECTActionPerformed
        Personal personal;
        
        int index=IDC_TABLE_PERSONALS.getSelectedRow();
        String address=IDC_TEXTFIELD_ADDRESS.getText();
        String telephoneNumber=IDC_TEXTFIELD_TELEPHONENUMBER.getText();
        String emailAddress=IDC_TEXTFIELD_EMAILADDRESS.getText();
        
        try {
            Modify(index);
        } catch (SQLException ex) {
            Logger.getLogger(UpdatingForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        index=this.addressBook.Correct(index, address, telephoneNumber, emailAddress);
        personal = this.addressBook.GetAt(index);
        
        IDC_TABLE_PERSONALS.setValueAt(address, index, 2);
        IDC_TABLE_PERSONALS.setValueAt(telephoneNumber, index, 3);
        IDC_TABLE_PERSONALS.setValueAt(emailAddress, index, 4);
    }//GEN-LAST:event_IDC_BUTTON_CORRECTActionPerformed

    private void IDC_BUTTON_ERASEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDC_BUTTON_ERASEActionPerformed
        int index;
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        int i=0;
        int number;
        Personal personal;
        
        index=IDC_TABLE_PERSONALS.getSelectedRow();
        this.Delete(index);
        this.addressBook.Erase(index);
        
        DefaultTableModel model = (DefaultTableModel)IDC_TABLE_PERSONALS.getModel();
        int rowCount = model.getRowCount();
        model.setRowCount(0);
        
        i= 0 ;
        while(i< this.addressBook.GetLength()){
            model.setRowCount(rowCount);
            
            number = i+1;
            personal = this.addressBook.GetAt(i);
            name = personal.getName();
            address = personal.getAddress();
            telephoneNumber = personal.getTelephoneNumber();
            emailAddress = personal.getEmailAddress();
            
            IDC_TABLE_PERSONALS.setValueAt(number, i, 0);
            IDC_TABLE_PERSONALS.setValueAt(personal.getName(), i, 1);
            IDC_TABLE_PERSONALS.setValueAt(personal.getAddress(), i, 2);
            IDC_TABLE_PERSONALS.setValueAt(personal.getTelephoneNumber(), i, 3);
            IDC_TABLE_PERSONALS.setValueAt(personal.getEmailAddress(), i, 4);
            i++;
        }
        
        if(model.getRowCount() == 0){
            model.setRowCount(8);
        }
        
    }//GEN-LAST:event_IDC_BUTTON_ERASEActionPerformed

    private void IDC_BUTTON_ARRANGEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDC_BUTTON_ARRANGEActionPerformed
        int i=0;
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        
        Personal personal;
        this.addressBook.Arrange();
        
        //DefaultTableModel model = (DefaultTableModel)IDC_TABLE_PERSONALS.getModel();
        //int rowCount = model.getRowCount();
        //model.setRowCount(0);
        
        i= 0 ;
        while(i< this.addressBook.GetLength()){
            //model.setRowCount(rowCount);
            
            int number = i+1;
            personal = this.addressBook.GetAt(i);
            name = personal.getName();
            address = personal.getAddress();
            telephoneNumber = personal.getTelephoneNumber();
            emailAddress = personal.getEmailAddress();
            
            IDC_TABLE_PERSONALS.setValueAt(number, i, 0);
            IDC_TABLE_PERSONALS.setValueAt(personal.getName(), i, 1);
            IDC_TABLE_PERSONALS.setValueAt(personal.getAddress(), i, 2);
            IDC_TABLE_PERSONALS.setValueAt(personal.getTelephoneNumber(), i, 3);
            IDC_TABLE_PERSONALS.setValueAt(personal.getEmailAddress(), i, 4);
            i++;
        }
         
    }//GEN-LAST:event_IDC_BUTTON_ARRANGEActionPerformed

    private void IDC_BUTTON_FINDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDC_BUTTON_FINDActionPerformed
        FindingForm findingForm=new FindingForm(this);
        findingForm.setVisible(true);
    }//GEN-LAST:event_IDC_BUTTON_FINDActionPerformed

    private void IDC_BUTTON_FINDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_IDC_BUTTON_FINDPropertyChange
    }//GEN-LAST:event_IDC_BUTTON_FINDPropertyChange

    private void IDC_TABLE_PERSONALSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IDC_TABLE_PERSONALSMouseClicked
        int index;
        String name;
        String address;
        String telephoneNumber;
        String emailAddress;
        int i = 0;

        if (evt.getClickCount() == 2){
            index = IDC_TABLE_PERSONALS.getSelectedRow();
            name = (String)IDC_TABLE_PERSONALS.getValueAt(index, 1);
            address = (String)IDC_TABLE_PERSONALS.getValueAt(index, 2);
            telephoneNumber = (String)IDC_TABLE_PERSONALS.getValueAt(index, 3);
            emailAddress = (String)IDC_TABLE_PERSONALS.getValueAt(index, 4);

            IDC_TEXTFIELD_NAME.setText(name);
            IDC_TEXTFIELD_ADDRESS.setText(address);
            IDC_TEXTFIELD_TELEPHONENUMBER.setText(telephoneNumber);
            IDC_TEXTFIELD_EMAILADDRESS.setText(emailAddress);
        }
    }//GEN-LAST:event_IDC_TABLE_PERSONALSMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            Save();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatingForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdatingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdatingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdatingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdatingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UpdatingForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton IDC_BUTTON_ARRANGE;
    private javax.swing.JButton IDC_BUTTON_CORRECT;
    private javax.swing.JButton IDC_BUTTON_ERASE;
    private javax.swing.JButton IDC_BUTTON_FIND;
    private javax.swing.JButton IDC_BUTTON_RECORD;
    public javax.swing.JTable IDC_TABLE_PERSONALS;
    public javax.swing.JTextField IDC_TEXTFIELD_ADDRESS;
    public javax.swing.JTextField IDC_TEXTFIELD_EMAILADDRESS;
    public javax.swing.JTextField IDC_TEXTFIELD_NAME;
    public javax.swing.JTextField IDC_TEXTFIELD_TELEPHONENUMBER;
    private javax.swing.JPanel JPanel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables


}

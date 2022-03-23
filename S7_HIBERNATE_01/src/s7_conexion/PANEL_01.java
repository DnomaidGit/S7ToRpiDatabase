package s7_conexion;

import controller.Operation;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import static s7_conexion.DataIsoTCP.mensPanel;

public class PANEL_01 extends javax.swing.JFrame 
{
    //instancia de clase
    private DataIsoTCP dataisotcp;
    public static String msjPanel = "Starting...";
    public static int currentDataReadPuesto = 0;
    public static int lastDataWritePuesto = 0;
    public static int lastDataInsertPuesto = 0;
    public static int currentDataReadChasis = 0;
    public static int lastDataWriteChasis = 0;
    public static int lastDataInsertChasis = 0;
    public static int currentDataReadModelo = 0;
    public static int lastDataWriteModelo = 0;
    public static int lastDataInsertModelo = 0;
    public static int currentDataReadEstado = 0;
    public static int lastDataWriteEstado = 0;
    public static int lastDataInsertEstado = 0;
 
    public static int lastQueryPuestoPuesto = 0;
    public static String lastQueryPuestoChasis = "0";
    public static int lastQueryPuestoModelo = 0;
    public static int lastQueryPuestoEstado = 0;
     
    public static int numArray = 0;
    public static  String[] buffer = new String[17];

    public static void setMsjPanel(String msj) {
        msjPanel = msj;
        buffer[numArray]=msjPanel;
        jTextArea1.setText(null);

        if(numArray == buffer.length-1){
            for(int i = 0; i < buffer.length-1; i++){
                buffer[i] = buffer[i+1];
            }
            buffer[buffer.length-1]="";
        }        

        for(int i = 0; i <= numArray; i++){
            jTextArea1.append(buffer[i]);
            jTextArea1.append(System.getProperty("line.separator"));            
        }

        if(numArray < buffer.length-1){
        numArray += 1;    
        }
    }
    
    public static void bitUpdateRead(int db){
        DataIsoTCP.ReadBitUpdataDB(db, 0, 2, 0);
    }
    
    public static void bitBackupRead(int db){
    DataIsoTCP.ReadBitBackupDB(db, 0, 2, 1);
    }

    public static void currentDataRead(int db){
        DataIsoTCP.ReadData(4, db, 4, 2);  
        currentDataReadPuesto = DataIsoTCP.readData;
        jTextField_ReadPuesto.setText(String.valueOf(currentDataReadPuesto));        
        DataIsoTCP.ReadData(4, db, 6, 2);  
        currentDataReadChasis = DataIsoTCP.readData;
        jTextField_ReadChasis.setText(String.valueOf(currentDataReadChasis));        
        DataIsoTCP.ReadData(4, db, 8, 2);  
        currentDataReadModelo = DataIsoTCP.readData;
        jTextField_ReadModelo.setText(String.valueOf(currentDataReadModelo));        
        DataIsoTCP.ReadData(4, db, 10, 2);  
        currentDataReadEstado = DataIsoTCP.readData;
        jTextField_ReadEstado.setText(String.valueOf(currentDataReadEstado));        
    }
        
    public static void LastDataWriteRead(int db){
        DataIsoTCP.ReadData(4, db, 22, 2);
        lastDataWritePuesto = DataIsoTCP.readData;
        jTextField_WritePuesto.setText(String.valueOf(lastDataWritePuesto));     
        DataIsoTCP.ReadData(4, db, 24, 2);
        lastDataWriteChasis = DataIsoTCP.readData;
        jTextField_WriteChasis.setText(String.valueOf(lastDataWriteChasis));     
        DataIsoTCP.ReadData(4, db, 26, 2);
        lastDataWriteModelo = DataIsoTCP.readData;
        jTextField_WriteModelo.setText(String.valueOf(lastDataWriteModelo));     
        DataIsoTCP.ReadData(4, db, 28, 2);
        lastDataWriteEstado = DataIsoTCP.readData;
        jTextField_WriteEstado.setText(String.valueOf(lastDataWriteEstado));     
    }

    public static void bitUpdateWrite(int db){
        DataIsoTCP.WriteBitDB(db, 18, 2, 0, true);
    }
    
    public static void bitBackupWrite(int db){
    DataIsoTCP.WriteBitDB(db, 18, 2, 1, true);
    }
    
    public static void writeData(int db){
        DataIsoTCP.WriteData16(4, db, 22, currentDataReadPuesto);        
        DataIsoTCP.WriteData16(4, db, 24, currentDataReadChasis);        
        DataIsoTCP.WriteData16(4, db, 26, currentDataReadModelo);        
        DataIsoTCP.WriteData16(4, db, 28, currentDataReadEstado);        
    }

        public static void writeBackup(int db){
        DataIsoTCP.WriteData16(4, db, 6, Integer.valueOf (lastQueryPuestoChasis));        
        DataIsoTCP.WriteData16(4, db, 8, lastQueryPuestoModelo);        
        DataIsoTCP.WriteData16(4, db, 10, lastQueryPuestoEstado);        
    }
    
    public static void insert(){
        Operation operation;
        operation = new Operation();
        operation.insert(lastDataWritePuesto, String.valueOf(lastDataWriteChasis), lastDataWriteModelo, lastDataWriteEstado);
        lastDataInsertPuesto = lastDataWritePuesto;
        lastDataInsertChasis = lastDataWriteChasis;
        lastDataInsertModelo = lastDataWriteModelo;
        lastDataInsertEstado = lastDataWriteEstado;
        jTextField_InsertPuesto.setText(String.valueOf(lastDataInsertPuesto));
        jTextField_InsertChasis.setText(String.valueOf(lastDataInsertChasis));
        jTextField_InsertModelo.setText(String.valueOf(lastDataInsertModelo));
        jTextField_InsertEstado.setText(String.valueOf(lastDataInsertEstado));
    }
    
    public static void forceInsert(){
        lastDataWritePuesto = Integer.valueOf (jTextField_WritePuesto.getText());
        lastDataInsertPuesto = Integer.valueOf (jTextField_InsertPuesto.getText());
        lastDataWriteChasis = Integer.valueOf (jTextField_WriteChasis.getText());
        lastDataInsertChasis = Integer.valueOf (jTextField_InsertChasis.getText());        
        if (lastDataWritePuesto != lastDataInsertPuesto || lastDataWriteChasis != lastDataInsertChasis)
            {    
            insert();
            }
    }
    
    public static void backupPuesto(int puesto){
        Operation operation;
        operation = new Operation();
        operation.backupPuesto(puesto);
        jTextField_QueryPuesto.setText(String.valueOf(lastQueryPuestoPuesto));
        jTextField_QueryChasis.setText(lastQueryPuestoChasis);
        jTextField_QueryModelo.setText(String.valueOf(lastQueryPuestoModelo));
        jTextField_QueryEstado.setText(String.valueOf(lastQueryPuestoEstado));
    }
            
    public PANEL_01() 
    {
        initComponents();
        jTextField_InfoPLCConex.setText("Disconnected PLC");        
        jTextField_ReadPuesto.setText(String.valueOf(currentDataReadPuesto));
        jTextField_WritePuesto.setText(String.valueOf(lastDataWritePuesto));
        jTextField_InsertPuesto.setText(String.valueOf(lastDataInsertPuesto));
        jTextField_ReadChasis.setText(String.valueOf(currentDataReadChasis));
        jTextField_WriteChasis.setText(String.valueOf(lastDataWriteChasis));
        jTextField_InsertChasis.setText(String.valueOf(lastDataInsertChasis));
        jTextField_ReadModelo.setText(String.valueOf(currentDataReadModelo));
        jTextField_WriteModelo.setText(String.valueOf(lastDataWriteModelo));
        jTextField_InsertModelo.setText(String.valueOf(lastDataInsertModelo));
        jTextField_ReadEstado.setText(String.valueOf(currentDataReadEstado));
        jTextField_WriteEstado.setText(String.valueOf(lastDataWriteEstado));
        jTextField_InsertEstado.setText(String.valueOf(lastDataInsertEstado));

        jTextArea1.append(msjPanel);
        jTextArea1.append(System.getProperty("line.separator"));
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButtonLastDataWrite = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField_InfoPLCConex = new javax.swing.JTextField();
        jTextField_ReadChasis = new javax.swing.JTextField();
        jTextField_WriteChasis = new javax.swing.JTextField();
        jButtonConnectPlc = new javax.swing.JButton();
        jTextField_InsertChasis = new javax.swing.JTextField();
        jButtonDisconnectPlc = new javax.swing.JButton();
        jButtonForceInsert = new javax.swing.JButton();
        jButtonCurrentDataRead = new javax.swing.JButton();
        jTextField_ReadPuesto = new javax.swing.JTextField();
        jTextField_WritePuesto = new javax.swing.JTextField();
        jTextField_InsertPuesto = new javax.swing.JTextField();
        jTextField_WriteModelo = new javax.swing.JTextField();
        jTextField_InsertModelo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_WriteEstado = new javax.swing.JTextField();
        jTextField_ReadEstado = new javax.swing.JTextField();
        jTextField_InsertEstado = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_QueryPuesto = new javax.swing.JTextField();
        jTextField_QueryChasis = new javax.swing.JTextField();
        jTextField_QueryModelo = new javax.swing.JTextField();
        jTextField_QueryEstado = new javax.swing.JTextField();
        jButtonPuestoBackup = new javax.swing.JButton();
        jTextField_ReadModelo = new javax.swing.JTextField();
        jTextField_QueryPuestoBackup = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GB dnomaiD");
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/diamante-40x40.png")).getImage());
        setMaximumSize(new java.awt.Dimension(845, 386));

        jPanel1.setPreferredSize(new java.awt.Dimension(1230, 709));
        jPanel1.setRequestFocusEnabled(false);

        jLabel2.setText("Info:");

        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jButtonLastDataWrite.setText("Last Data Write");
        jButtonLastDataWrite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonLastDataWriteMouseClicked(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Last Data Insert");

        jTextField_InfoPLCConex.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_InfoPLCConex.setText("INFO_PLC_CONEX");

        jTextField_ReadChasis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ReadChasis.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_WriteChasis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_WriteChasis.setPreferredSize(new java.awt.Dimension(75, 20));

        jButtonConnectPlc.setText("Connect to PLC");
        jButtonConnectPlc.setActionCommand("Connect");
        jButtonConnectPlc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonConnectPlcMouseClicked(evt);
            }
        });

        jTextField_InsertChasis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_InsertChasis.setPreferredSize(new java.awt.Dimension(75, 20));

        jButtonDisconnectPlc.setText("Disconnect PLC");
        jButtonDisconnectPlc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDisconnectPlcMouseClicked(evt);
            }
        });

        jButtonForceInsert.setText("Force Insert");
        jButtonForceInsert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonForceInsertMouseClicked(evt);
            }
        });

        jButtonCurrentDataRead.setText("Current Data Read");
        jButtonCurrentDataRead.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonCurrentDataReadMouseClicked(evt);
            }
        });

        jTextField_ReadPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ReadPuesto.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_WritePuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_WritePuesto.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_InsertPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_InsertPuesto.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_WriteModelo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_WriteModelo.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_InsertModelo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setText("Chasis");

        jLabel5.setText("Modelo");

        jLabel6.setText("Estado");

        jTextField_WriteEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_WriteEstado.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_ReadEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ReadEstado.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_InsertEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_InsertEstado.setPreferredSize(new java.awt.Dimension(75, 20));

        jLabel7.setText("Puesto Trabajo");

        jTextField_QueryPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_QueryPuesto.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_QueryChasis.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_QueryChasis.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_QueryModelo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jTextField_QueryEstado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_QueryEstado.setPreferredSize(new java.awt.Dimension(75, 20));

        jButtonPuestoBackup.setText("Backup");
        jButtonPuestoBackup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonPuestoBackupMouseClicked(evt);
            }
        });

        jTextField_ReadModelo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_ReadModelo.setPreferredSize(new java.awt.Dimension(75, 20));

        jTextField_QueryPuestoBackup.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_QueryPuestoBackup.setPreferredSize(new java.awt.Dimension(75, 20));

        jLabel8.setText("Puesto Trabajo");
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jTextField_QueryPuestoBackup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButtonLastDataWrite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonCurrentDataRead, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButtonPuestoBackup, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextField_ReadPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField_WritePuesto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_InsertPuesto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jTextField_QueryPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_QueryChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField_ReadChasis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField_WriteChasis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField_InsertChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel4)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_WriteModelo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_InsertModelo)
                                    .addComponent(jTextField_QueryModelo)
                                    .addComponent(jTextField_ReadModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jTextField_WriteEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField_ReadEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jTextField_InsertEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jButtonForceInsert))
                                    .addComponent(jTextField_QueryEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel5)
                                .addGap(55, 55, 55)
                                .addComponent(jLabel6))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonConnectPlc, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDisconnectPlc, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_InfoPLCConex, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jTextField_InfoPLCConex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConnectPlc)
                    .addComponent(jButtonDisconnectPlc))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_WriteModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_WriteEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_WriteChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_WritePuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonLastDataWrite))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_InsertModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_InsertEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_InsertChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_InsertPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel8)
                            .addComponent(jButtonForceInsert)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_ReadChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_ReadModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_ReadEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_ReadPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonCurrentDataRead))
                        .addGap(77, 77, 77)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_QueryPuestoBackup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonPuestoBackup)
                    .addComponent(jTextField_QueryPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_QueryChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_QueryModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_QueryEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLastDataWriteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonLastDataWriteMouseClicked
        writeData(11);
        LastDataWriteRead(11);
    }//GEN-LAST:event_jButtonLastDataWriteMouseClicked

    private void jButtonCurrentDataReadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCurrentDataReadMouseClicked
        currentDataRead(11);
        LastDataWriteRead(11);
    }//GEN-LAST:event_jButtonCurrentDataReadMouseClicked

    private void jButtonDisconnectPlcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDisconnectPlcMouseClicked
        try {
            DataIsoTCP.StopConnection();
        } catch (Throwable e) {
            mensPanel.MnsThrowable("PLC_TCP",e);
        }
        if (DataIsoTCP.Connection == false)
        {
            this.jTextField_InfoPLCConex.setText("Disconnected PLC");
        }
    }//GEN-LAST:event_jButtonDisconnectPlcMouseClicked

    private void jButtonConnectPlcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonConnectPlcMouseClicked
        if(DataIsoTCP.Connection == false){
            DataIsoTCP.Start("192.168.0.1");
        }

        if (DataIsoTCP.Connection == true)
        {
            this.jTextField_InfoPLCConex.setText("Connected to PLC");
        }
    }//GEN-LAST:event_jButtonConnectPlcMouseClicked

    private void jButtonForceInsertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonForceInsertMouseClicked
        forceInsert();
    }//GEN-LAST:event_jButtonForceInsertMouseClicked

    private void jButtonPuestoBackupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonPuestoBackupMouseClicked
        backupPuesto(Integer.valueOf (jTextField_QueryPuestoBackup.getText()));
    }//GEN-LAST:event_jButtonPuestoBackupMouseClicked
    
    public static void main(String args[]) {

     java.awt.EventQueue.invokeLater(new Runnable() {@Override
            public void run() {
                new PANEL_01().setVisible(true);
            }
        });

     TimerTask timerTask;
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            { 
                if (DataIsoTCP.Connection == true )
                    {
                    for(int i = 11; i < 15; i++)
                        {                            
                        bitUpdateRead(i);
                        if (DataIsoTCP.readDataBitUpdata == true)
                            {                                
                            currentDataRead(i);
                            if (currentDataReadChasis != lastDataWriteChasis || currentDataReadPuesto != lastDataWritePuesto ||
                                currentDataReadModelo != lastDataWriteModelo || currentDataReadEstado != lastDataWriteEstado )
                                {
                                writeData(i);
                                LastDataWriteRead(i);
                                }    
                            if (lastDataWriteChasis != lastDataInsertChasis || lastDataWritePuesto != lastDataInsertPuesto ||
                                lastDataWriteModelo != lastDataInsertModelo || lastDataWriteEstado != lastDataInsertEstado )
                                {    
                                insert();
                                currentDataRead(i);
                                bitUpdateWrite(i);  
                                }
                            }
                        }
                        for(int i = 11; i < 15; i++)
                        {    
                        bitBackupRead(i);
                        if (DataIsoTCP.readDataBitBackup == true)
                            {
                            backupPuesto(i);
                            writeBackup(i);
                            bitBackupWrite(i);
                            }
                        }
                    }
            }
        };
     Timer timer = new Timer();
     timer.scheduleAtFixedRate(timerTask, 0, 10000); 

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnectPlc;
    private javax.swing.JButton jButtonCurrentDataRead;
    private javax.swing.JButton jButtonDisconnectPlc;
    private javax.swing.JButton jButtonForceInsert;
    private javax.swing.JButton jButtonLastDataWrite;
    private javax.swing.JButton jButtonPuestoBackup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private static javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextArea1;
    private static javax.swing.JTextField jTextField_InfoPLCConex;
    private static javax.swing.JTextField jTextField_InsertChasis;
    private static javax.swing.JTextField jTextField_InsertEstado;
    private static javax.swing.JTextField jTextField_InsertModelo;
    private static javax.swing.JTextField jTextField_InsertPuesto;
    private static javax.swing.JTextField jTextField_QueryChasis;
    private static javax.swing.JTextField jTextField_QueryEstado;
    private static javax.swing.JTextField jTextField_QueryModelo;
    private static javax.swing.JTextField jTextField_QueryPuesto;
    private static javax.swing.JTextField jTextField_QueryPuestoBackup;
    private static javax.swing.JTextField jTextField_ReadChasis;
    private static javax.swing.JTextField jTextField_ReadEstado;
    private static javax.swing.JTextField jTextField_ReadModelo;
    private static javax.swing.JTextField jTextField_ReadPuesto;
    private static javax.swing.JTextField jTextField_WriteChasis;
    private static javax.swing.JTextField jTextField_WriteEstado;
    private static javax.swing.JTextField jTextField_WriteModelo;
    private static javax.swing.JTextField jTextField_WritePuesto;
    // End of variables declaration//GEN-END:variables

}

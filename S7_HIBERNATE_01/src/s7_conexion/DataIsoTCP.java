package s7_conexion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import static s7_conexion.DataIsoTCP.readDataBitUpdata;

public class DataIsoTCP {

    public static boolean Connection = false;
    private static int i, j;
    public static byte b;
    private static char buf[];
    public static byte buf1[];
    public static PLCinterface di;
    public static TCPConnection dc;
    public static int readData;
    public static boolean readDataBitUpdata = false;
    public static boolean readDataBitBackup = false;
    public static Socket sock;
    private static int slot;
    public static byte[] by;
    public static String IP;
    public static MensPanel mensPanel = new MensPanel();
   
    DataIsoTCP(String host) {
        IP = host;
        //Nodave.Debug=Nodave.DEBUG_ALL;
        buf = new char[Nodave.OrderCodeSize];
        buf1 = new byte[Nodave.PartnerListSize];
        try {
            sock = new Socket(host, 102);
            mensPanel.MnsText("PLC_TCP: Socket OK " + IP);
        } catch (IOException e) {
            sock = null;
            mensPanel.MnsIOException("PLC",e);
            mensPanel.MnsText("PLC_TCP Fault: Socket NOK " + IP);             
        }
    }

    public static void StartConnection() {
        Connection = false;
        OutputStream oStream = null;
        InputStream iStream = null;
        slot = 2;
        mensPanel.MnsText("PLC_TCP: Start Connection... ");
        if (sock != null) {
            try {
                oStream = sock.getOutputStream();
            } catch (IOException e) {
                mensPanel.MnsIOException("PLC_TCP",e);
            }
            try {
                iStream = sock.getInputStream();
            } catch (IOException e) {
                mensPanel.MnsIOException("PLC_TCP",e);
            }
            di = new PLCinterface(
                    oStream,
                    iStream,
                    "IF1",
                    0,
                    Nodave.PROTOCOL_ISOTCP);

            dc = new TCPConnection(di, 0, slot);
            int res = dc.connectPLC();
            if (0 == res) {
                Connection = true;
                mensPanel.MnsText("PLC_TCP: Connection OK " + IP); 
            } else {
                mensPanel.MnsText("PLC_TCP Fault: Connection NOK " + IP);
            }
        }else {
            mensPanel.MnsText("PLC_TCP Fault: Connection NOK " + IP);
        }
    }

    public static void StopConnection() throws Throwable {
        if (Connection == true) {
            Connection = false;
//            dc.disconnectPLC();
//            dc.finalize();
//            di.disconnectAdapter();
            mensPanel.MnsText("PLC_TCP: Stop Connection " + IP);
        }
    }

    public static void ReadData(int area, int db, int address, int bytes) {
        if (Connection == true) {
            int aux = 0;    
            switch (area) {
                case 1:
                    readData = dc.readBytes(Nodave.INPUTS, 0, address, bytes, null);
                    break;
                case 2:
                    readData = dc.readBytes(Nodave.FLAGS, db, address, bytes, null);
                    break;
                case 3:
                    readData = dc.readBytes(Nodave.OUTPUTS, 0, address, bytes, null);        
                    break;
                case 4:
                    aux = dc.readBytes(Nodave.DB, db, address, bytes, null);
                    break;
                default:
                    mensPanel.MnsText("PLC_TCP Fault: Reading end NOK"); 
                    break;
            }
            if (aux == 0) {
                readData = (int)dc.getINT();
                mensPanel.MnsText("PLC_TCP: Reading end OK");  
            } else{
                mensPanel.MnsText("PLC_TCP Fault: Reading end NOK");        
            }
        } else{
            mensPanel.MnsText("PLC_TCP Fault: Reading end NOK (Disconnected PLC)");        
        }    
    }

        public static void ReadBitUpdataDB(int db, int address, int bytes,int bit) {
        if (Connection == true) {
            i = 0;
            readDataBitUpdata = false;
            int res = dc.readBytes(Nodave.DB, db, address, bytes, null);
            
        if (res == 0) {
            j = dc.getBYTE();
            b = (byte) j;
           if ((b & (1 << bit)) != 0) {
                readDataBitUpdata = true;
            } else {
                readDataBitUpdata = false;
            }
                mensPanel.MnsText("PLC_TCP: Bit Reading end OK");  
            } else{
                mensPanel.MnsText("PLC_TCP Fault: Bit Reading end NOK");        
            }
        } else{
            mensPanel.MnsText("PLC_TCP Fault: Bit Reading end NOK (Disconnected PLC)");        
        }                                           
    }
    
        public static void ReadBitBackupDB(int db, int address, int bytes,int bit) {
        if (Connection == true) {
            i = 0;
            readDataBitBackup = false;
            int res = dc.readBytes(Nodave.DB, db, address, bytes, null);
            
        if (res == 0) {
            j = dc.getBYTE();
            b = (byte) j;
           if ((b & (1 << bit)) != 0) {
                readDataBitBackup = true;
            } else {
                readDataBitBackup = false;
            }
                mensPanel.MnsText("PLC_TCP: Bit Reading end OK");  
            } else{
                mensPanel.MnsText("PLC_TCP Fault: Bit Reading end NOK");        
            }
        } else{
            mensPanel.MnsText("PLC_TCP Fault: Bit Reading end NOK (Disconnected PLC)");        
        }                                           
    }
        
    public static void WriteData16(int area, int db, int address, int m) {
        if (Connection == true) {        
            by = Nodave.bswap_16(m);
            switch (area) {
                case 1:
                    dc.writeBytes(Nodave.INPUTS, 0, address, 2, by);
                    break;
                case 2:
                    dc.writeBytes(Nodave.FLAGS, db, address, 2, by);
                    break;
                case 3:
                    dc.writeBytes(Nodave.OUTPUTS, 0, address, 2, by);
                    break;
                case 4:
                    dc.writeBytes(Nodave.DB, db, address, 2, by);
                    break;    
                default:
                    break;
            }
            mensPanel.MnsText("PLC_TCP: Write end OK");   
        } else{
            mensPanel.MnsText("PLC_TCP Fault: Write NOK (Disconnected PLC)");        
        }    
    }
    
    public static void WriteBitDB(int db, int address, int bytes, int bit, boolean value) {
        if (Connection == true) {
            i = 0;
            int res = dc.readBytes(Nodave.DB, db, address, bytes, null);
            
        if (res == 0) {
            j = dc.getS8(i);
            b = (byte) j;
            if (value == true) {
                b = (byte) (b | (1 << bit));  //Set het n'de Bit
            } else {
                b = (byte) (b & ~(1 << bit));   //Reset het n'de Bit              
            }
            by = Nodave.bswap_8(b);
            dc.writeBytes(Nodave.DB, db, address, 1, by);
                mensPanel.MnsText("PLC_TCP: Write end OK");  
            } else{
                mensPanel.MnsText("PLC_TCP Fault: Write end NOK");        
            }
        } else{
            mensPanel.MnsText("PLC_TCP Fault: Write end NOK (Disconnected PLC)");        
        }                                           
    }
    
    public static void Start(String adres) {
//        Nodave.Debug=Nodave.DEBUG_ALL^(Nodave.DEBUG_IFACE|Nodave.DEBUG_SPECIALCHARS);
        DataIsoTCP tp = new DataIsoTCP(adres);
        DataIsoTCP.StartConnection();
    }
}

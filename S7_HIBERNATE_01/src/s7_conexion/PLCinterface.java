package s7_conexion;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static s7_conexion.DataIsoTCP.mensPanel;

public class PLCinterface {

    int localMPI; // the adapter's MPI address		
    String name;
    int timeout; // Timeout in milliseconds used in transport.
    int protocol; // The kind of transport used on this interface.
    OutputStream out;
    InputStream in;
    int wp, rp;

    public PLCinterface(
            OutputStream out,
            InputStream in,
            String name,
            int localMPI,
            int protocol) {
        init(out, in, name, localMPI, protocol);
    }

    public PLCinterface() {
    }

    public void init(
            OutputStream oStream,
            InputStream iStream,
            String name,
            int localMPI,
            int protocol) {
        this.out = oStream;
        this.in = iStream;
        this.name = name;
        this.localMPI = localMPI;
        this.protocol = protocol;
        timeout = 5000;
        switch (protocol) {
            case Nodave.PROTOCOL_ISOTCP:
                timeout = 50000;
                break;
            case Nodave.PROTOCOL_ISOTCP243:
                timeout = 50000;
                break;
        }
    }

    public void write(byte[] b, int start, int len) {
        if ((Nodave.Debug & Nodave.DEBUG_IFACE) != 0) {
            Nodave.dump("Interface.write", b, start, len);
        }
        try {
            out.write(b, start, len);
        } catch (IOException e) {
            mensPanel.MnsIOExceptionError("PLC_INT",e);
        }
    }

    public int read(byte[] b, int start, int len) {
        int res;
        if ((Nodave.Debug & Nodave.DEBUG_IFACE) != 0) {
            System.out.println("Interface.read");
        }
        try {
            int retry = 0;
            while ((in.available() <= 0) && (retry < 10)) {
                try {
                    if (retry > 0) {
                        Thread.sleep(timeout / 200);
                    }
                    retry++;
                    if ((Nodave.Debug & Nodave.DEBUG_IFACE) != 0) {
                        System.out.println("Interface.read delayed");
                    }
                } catch (InterruptedException e) {
                    mensPanel.MnsInterruptedException("PLC_INT",e);
                }
            }
            res = 0;
            while ((in.available() > 0) && (len > 0)) {
                if ((Nodave.Debug & Nodave.DEBUG_IFACE) != 0)
                System.out.println("can read");
                res = in.read(b, start, len);
                start += res;
                len -= res;
//		System.out.println(res+" bytes read");
            }
            if ((Nodave.Debug & Nodave.DEBUG_IFACE) != 0) {
                System.out.println("got " + res + " bytes");
            }
            return res;
        } catch (IOException e) {
            mensPanel.MnsIOException("PLC_INT",e);
            return 0;
        }
    }

    public int initAdapter() {
        return 0;
    }

    public int disconnectAdapter() {
        return 0;
    }

    public void finalize() throws Throwable {
        mensPanel.MnsText("PLC_INT: This is finalize");
        disconnectAdapter();
    }
}

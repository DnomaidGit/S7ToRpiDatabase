package s7_conexion;

import java.io.IOException;

public class MensPanel {
    public PANEL_01 panel_01=null;
    
    public void MnsIOException(String name, IOException e) {
    PANEL_01.setMsjPanel(name + " Fault: " + e.getMessage().toString());
    System.out.println(e);
    }
    
    public void MnsIOExceptionError(String name, IOException e) {
    PANEL_01.setMsjPanel(name + " Error: " + e.getMessage().toString());
    System.err.println(e);
    }

    public void MnsException(String name, Exception e) {
    PANEL_01.setMsjPanel(name + " Fault: " + e.getMessage().toString());        
    System.out.println(e);
    }
    
    public void MnsInterruptedException(String name, InterruptedException e) {
    PANEL_01.setMsjPanel(name + " Fault: " + e.getMessage().toString());
    System.out.println(e);
    }
    
    public void MnsThrowable(String name, Throwable e) {
    PANEL_01.setMsjPanel(name + " Fault: " + e.getMessage().toString());
    System.out.println(e);
    }
        
    public void MnsText(String text) {
    PANEL_01.setMsjPanel(text);        
    System.out.println(text);
    }        
}

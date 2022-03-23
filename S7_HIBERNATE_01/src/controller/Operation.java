package controller;

import model.Produccion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static s7_conexion.DataIsoTCP.mensPanel;
import s7_conexion.PANEL_01;

/**
 *
 * @author JLO
 */
public class Operation {
    
    public PANEL_01 panel_01=null;
    public static String lastChasis = "0";
    public static int puestoLastId = 0;
    public static int chasisLastId = 0;    
    
    public void insert(int puesto, String chasis, int modelo, int estado){
    
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session = null;
        try {  
            session = sesion.openSession();
            org.hibernate.Transaction tx = session.beginTransaction();
            Produccion produccion;
            produccion = new Produccion();
            produccion.setChasis(chasis);
            produccion.setEstado(estado);
            produccion.setModelo(modelo);
            produccion.setPuestoTrabajo(puesto);            
            session.save(produccion);
            tx.commit();
                mensPanel.MnsText("SQL: Insert OK");
            } catch (Exception e) {
                mensPanel.MnsException("SQL", e);
                mensPanel.MnsText("SQL Fault: Insert NOK");
            } finally {
                session.close();     
                mensPanel.MnsText("SQL: Session sql close");
        }
    }

    public void queryPuestoLastChasis(int puesto){
    
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session = null;
        try {  
            session = sesion.openSession();
            org.hibernate.Transaction tx = session.beginTransaction();
            Produccion produccion;
            produccion = new Produccion();
            String queryString = "SELECT chasis FROM Produccion WHERE fecha_hora = (Select MAX(fechaHora) From Produccion Where puesto_trabajo = :chasisToFind)";
            Query query = session.createQuery(queryString);
            query.setInteger ("chasisToFind", puesto);
            lastChasis = (String) query.uniqueResult();                        
                mensPanel.MnsText("SQL: Last Chasis Query OK");
            } catch (Exception e) {
                mensPanel.MnsException("SQL", e);
                mensPanel.MnsText("SQL Fault: Last Chasis Query NOK");
            } finally {                
                session.close();     
                mensPanel.MnsText("SQL: Session sql close");
        }
    }

    public void queryPuestoLastId(int puesto){
    
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session = null;
        try {  
            session = sesion.openSession();
            org.hibernate.Transaction tx = session.beginTransaction();
            Produccion produccion;
            produccion = new Produccion();
            String queryString = "SELECT id FROM Produccion WHERE fecha_hora = (Select MAX(fechaHora) From Produccion Where puesto_trabajo = :idToFind)";
            Query query = session.createQuery(queryString);
            query.setInteger ("idToFind", puesto);
            puestoLastId = (Integer) query.uniqueResult();                        
                mensPanel.MnsText("SQL: Puesto Last Id Query OK");
            } catch (Exception e) {
                mensPanel.MnsException("SQL", e);
                mensPanel.MnsText("SQL Fault: Puesto Last Id Query NOK");
            } finally {                
                session.close();     
                mensPanel.MnsText("SQL: Session sql close");
        }
    }

    
        public void queryChasisLastId(String chasis){
    
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session = null;
        try {  
            session = sesion.openSession();
            org.hibernate.Transaction tx = session.beginTransaction();
            Produccion produccion;
            produccion = new Produccion();
            String queryString = "SELECT id FROM Produccion WHERE fecha_hora = (Select MAX(fechaHora) From Produccion Where chasis = :idToFind)";
            Query query = session.createQuery(queryString);
            query.setString("idToFind", chasis);
            chasisLastId = (Integer) query.uniqueResult();
                mensPanel.MnsText("SQL: Chasis Last Id Query OK");
            } catch (Exception e) {
                mensPanel.MnsException("SQL", e);
                mensPanel.MnsText("SQL Fault: Chasis Last Id Query NOK");
            } finally {                
                session.close();     
                mensPanel.MnsText("SQL: Session sql close");
        }
    }

    public void queryId(int id){
    
            SessionFactory sesion = NewHibernateUtil.getSessionFactory();
            Session session = null;
        try {  
            session = sesion.openSession();
            org.hibernate.Transaction tx = session.beginTransaction();
            Produccion produccion;
            produccion = new Produccion();
            String queryString = "from Produccion where id = :idToFind";
            Query query = session.createQuery(queryString);
            query.setInteger ("idToFind", id);
            produccion = (Produccion) query.uniqueResult();
            PANEL_01.lastQueryPuestoPuesto = produccion.getPuestoTrabajo();
            PANEL_01.lastQueryPuestoChasis = produccion.getChasis();
            PANEL_01.lastQueryPuestoModelo = produccion.getModelo();
            PANEL_01.lastQueryPuestoEstado = produccion.getEstado();
            
                mensPanel.MnsText("SQL: Id Query OK");
            } catch (Exception e) {
                mensPanel.MnsException("SQL", e);
                mensPanel.MnsText("SQL Fault: Id Query NOK");
            } finally {                
                session.close();     
                mensPanel.MnsText("SQL: Session sql close");
        }
    }

    public void backupPuesto(int puesto){
    
        queryPuestoLastChasis(puesto);
        queryPuestoLastId(puesto);
        queryChasisLastId(lastChasis);
        if (puestoLastId >= chasisLastId){
            queryId(puestoLastId);        
        }else{
            PANEL_01.lastQueryPuestoPuesto = 0;
            PANEL_01.lastQueryPuestoChasis = "0";
            PANEL_01.lastQueryPuestoModelo = 0;
            PANEL_01.lastQueryPuestoEstado = 0;        
            }
   }

    
}

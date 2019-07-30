/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import static dielsale.sias.modelo.UtilidadFallecimiento.sessionObj;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Realiza los joins necesarios para integrar
 * los archvios que ya haya escogido el 
 * usuario.
 * 
 * @author a-valderrama
 */
public class UtilidadIntegrado {
    
    static Integrado comObj;
    static Session sessionObj;
    
    /**
     * Guarda el layout que se recibe como 
     * parámetro la base de datos. 
     * 
     * @param i Objeto de tipo integrado a  
     *          guardar en la base de datos.
     */
    public void save(Integrado i) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            sessionObj.save(i);
            sessionObj.getTransaction().commit();
        } catch (Exception sqlException) {
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
    }
    
    
    public ArrayList<Integrado> getIntegrados (int idEnvio){
        ArrayList<Integrado> obj = null;
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        try{
            sessionObj.beginTransaction();
            String hql = "FROM Integrado WHERE id_envio = :idEnvio";
            Query query = sessionObj.createQuery(hql);
            query.setInteger("idEnvio", idEnvio);
            obj = (ArrayList<Integrado>)query.list();
            sessionObj.getTransaction().commit();
            if(obj.isEmpty()){
                return null;
            }else{
                return obj;
            }
        }catch(Exception sqlException){
            if (null != sessionObj.getTransaction()) {
                System.out.println("\n.......ERROOOOOOOOOOOR.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        }finally{
            if (sessionObj != null) {
                sessionObj.close();
            }            
        } 
        return null;
    }
}

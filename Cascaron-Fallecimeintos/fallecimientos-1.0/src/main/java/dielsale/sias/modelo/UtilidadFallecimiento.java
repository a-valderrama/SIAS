/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Esta clase nos permite mapear la vista a la base de datos, y
 * viceversa, utilizando las queries de hibernate.
 * Es decir, nos permite conocer toda la información de Layout 
 * de la BD para utilizara en la vista.
 * 
 * @author a-valderrama
 */
public class UtilidadLayout {
    
    static Layout comObj;
    static Session sessionObj;
    
    /**
     * Esta clase nos permite conocer la información en la 
     * tabla "layout" correspondiente a cierto id de envío.
     * 
     * @return List<Layout> Lista de toda la información 
     *         del Layout filtrada por el id del envío
     */
    public List<Layout> getLayout (){
        
        List<Layout> obj = null;
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        int idEnvio = obtenerIdEnvio();
        try{
            sessionObj.beginTransaction();
            String hql = "FROM Layout WHERE id_envio = :idEnvio";
            Query query = sessionObj.createQuery(hql);
            query.setInteger("idEnvio", idEnvio);
            obj = (List<Layout>)query.list();
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
    
    /**
     * Método que regresa un elemento de la tabla layout
     * de la BD con el id correspondiente.
     * 
     * @param id id del elemento buscado
     * @return el elemento buscado
     */
    public Layout getElemento (int id){
        
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        try{
            sessionObj.beginTransaction();
            String hql = "FROM Layout WHERE id = :id";
            Query query = sessionObj.createQuery(hql);
            query.setInteger("id", id);
            Layout l = (Layout) query.uniqueResult();
            sessionObj.getTransaction().commit();
            return l;
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
    
    /**
     * Establecemos que el soporte ha sido subido al
     * id correspondiente.
     * 
     * @param id id del layout al que se le sube el 
     *           soporte
     */
    public void setSoporte(int id){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "UPDATE Layout l SET l.soporte= true WHERE l.id= :id";
            Query query = sessionObj.createQuery(hql);
            query.setInteger("id", id);
            query.executeUpdate();
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
    
    /*
     * Esta es un método auxiliar para conocer el id del
     * último envío subido a la base. Que será a la tabla 
     * a la que se le asignará un "soporte.pdf".
     */
    private int obtenerIdEnvio(){
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        sessionObj.beginTransaction();
        String hql = "FROM Layout ORDER BY id DESC";
        Query query = sessionObj.createQuery(hql);
        query.setMaxResults(1);
        Layout last = (Layout) query.uniqueResult();
        sessionObj.getTransaction().commit();
        return last.getId_envio();
    }
}

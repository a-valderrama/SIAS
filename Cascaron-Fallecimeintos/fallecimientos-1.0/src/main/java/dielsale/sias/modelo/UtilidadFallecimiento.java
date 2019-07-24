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
 * Esta clase nos permite mapear la vista a la base
 * de datos, y viceversa, utilizando las queries 
 * de hibernate.
 * Es decir, nos permite conocer toda la información
 * de Fallecimiento de la BD para utilizara en la
 * vista.
 * 
 * @author a-valderrama
 */
public class UtilidadFallecimiento {
    
    static Fallecimiento comObj;
    static Session sessionObj;
    
    /**
     * Guarda el layout que se recibe como 
     * parámetro la base de datos. 
     * Con los campos previamente establecidos, 
     * a cada uno.
     * 
     * @param l Objeto de tipo fallecimiento a  
     *          guardar en la base de datos.
     */
    public void save(Fallecimiento l) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            sessionObj.save(l);
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
    
    /**
     * Esta clase nos permite conocer la información en la 
     * tabla "layout" correspondiente a cierto id de envío.
     * 
     * @return Lista de toda la información del 
     *         fallecimiento filtrada por el id del envío
     */
    public List<Fallecimiento> getLayout (){
        
        List<Fallecimiento> obj = null;
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        int idEnvio = obtenerIdEnvio();
        try{
            sessionObj.beginTransaction();
            String hql = "FROM Fallecimiento WHERE id_envio = :idEnvio";
            Query query = sessionObj.createQuery(hql);
            query.setInteger("idEnvio", idEnvio);
            obj = (List<Fallecimiento>)query.list();
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
     * Método que regresa un elemento de la tabla de
     * fallecimiento de la BD con el id 
     * correspondiente.
     * 
     * @param id id del elemento buscado
     * @return el elemento buscado
     */
    public Fallecimiento getElemento (int id){
        
        sessionObj = HibernateUtil.getSessionFactory().openSession();
        try{
            sessionObj.beginTransaction();
            String hql = "FROM Fallecimiento WHERE id = :id";
            Query query = sessionObj.createQuery(hql);
            query.setInteger("id", id);
            Fallecimiento l = (Fallecimiento) query.uniqueResult();
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
     * @param id id del fallecimiento al que se le sube  
     *           el soporte
     */
    public void setSoporte(int id){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "UPDATE Fallecimiento f SET f.soporte= true WHERE f.id= :id";
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
        String hql = "FROM Fallecimiento ORDER BY id DESC";
        Query query = sessionObj.createQuery(hql);
        query.setMaxResults(1);
        Fallecimiento last = (Fallecimiento) query.uniqueResult();
        sessionObj.getTransaction().commit();
        return last.getId_envio();
    }
}

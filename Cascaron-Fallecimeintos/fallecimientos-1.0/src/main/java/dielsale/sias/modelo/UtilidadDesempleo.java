/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Esta clase nos permite mapear la vista a la base
 * de datos, y viceversa, utilizando las queries 
 * de hibernate.
 * Es decir, nos permite conocer toda la información
 * de Desempleo de la BD para utilizara en la
 * vista.
 * 
 * @author Alejandro Valderrama para Dielsale
 */
public class UtilidadDesempleo {
    
    static Desempleo comObj;
    static Session sessionObj;
    
    /**
     * Guarda el layout que se recibe como 
     * parámetro la base de datos. 
     * Con los campos previamente establecidos, 
     * a cada uno.
     * 
     * @param d Objeto de tipo Desempleo a guardar 
     *          en la base de datos.
     */
    public void save(Desempleo d) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            sessionObj.save(d);
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
     * Método que nos regresa todos los idEnvios
     * existentes para el archvio de Desempleo
     * a clientes
     * 
     * @return Todos los idEnvios existentes en 
     *         la base de datos.
     */
    public List<Integer> getIdEnvios(){
        List<Integer> obj = null;
        sessionObj = HibernateUtil.getSessionFactory().openSession();

        try{
            sessionObj.beginTransaction();
            String hql = "SELECT id_envio FROM Desempleo";
            Query query = sessionObj.createQuery(hql);
            obj = (List<Integer>)query.list();
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
     * Método que nos regresa todos los registros
     * existentes bajo un idEnvio específico
     * 
     * @param idEnvio id envío en cuestión
     * @return Todos todos los registros
     *         existentes bajo un idEnvio 
     *         específico.
     */
    public ArrayList<Desempleo> getRegistros(int idEnvio){
        ArrayList<Desempleo> obj = null;
        sessionObj = HibernateUtil.getSessionFactory().openSession();

        try{
            sessionObj.beginTransaction();
            String hql = "FROM Desempleo WHERE id_envio = :idEnvio";
            Query query = sessionObj.createQuery(hql);
            query.setInteger("idEnvio", idEnvio);
            obj = (ArrayList<Desempleo>)query.list();
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

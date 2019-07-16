/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Esta clase nos permite mapear la vista a la base de datos, 
 * y viceversa, utilizando las queries de hibernate.
 * Es decir, nos da todas las utilidades necesarias
 * que deben tener los usuarios en la vista.
 * 
 * @author a-valderrama
 */
public class UtilidadUsuario {
    
    static Usuario userObj;
    static Session sessionObj;
    
    /**
     * Método que busca al comentarista con el nombre de 
     * usuario, pasado como parámetro, en la base de datos
     * 
     * @param usuario Nombre del usuario que buscamos
     * @return Usuario wrapper completo del usuario
     */
    public Usuario buscaPorUsuario(String usuario) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "FROM Usuario u WHERE u.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario",usuario);
            Usuario u = (Usuario)query.uniqueResult();
            sessionObj.getTransaction().commit();
            return u;
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
        return null;
    }
}

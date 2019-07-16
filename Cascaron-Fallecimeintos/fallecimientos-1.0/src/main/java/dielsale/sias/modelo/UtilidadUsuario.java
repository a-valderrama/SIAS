/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Esta clase nos permite mapear la vista a la base de datos, y
 * viceversa, utilizando las queries de hibernate.
 * Es decir, nos da todas las utilidades necesarias
 * que deben tener los usuarios de tipo administrador
 * en la vista.
 * 
 * @author a-valderrama
 */
public class UtilidadAdministrador {
    
    static Administrador userObj;
    static Session sessionObj;
    
    /**
     * Método que busca al comentarista con el nombre de 
     * usuario, pasado como parámetro, en la base de datos
     * 
     * @param usuario Nombre del usuario que buscamos
     * @return Administrador wrapper completo del usuario
     */
    public Administrador buscaPorUsuario(String usuario) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "FROM Administrador admin WHERE admin.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario",usuario);
            Administrador comen = (Administrador)query.uniqueResult();
            sessionObj.getTransaction().commit();
            return comen;
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

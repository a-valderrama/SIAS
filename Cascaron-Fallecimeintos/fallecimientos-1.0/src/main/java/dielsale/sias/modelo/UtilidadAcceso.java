/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import static dielsale.sias.modelo.UtilidadBitacora.sessionObj;
import org.hibernate.Query;

/**
 * Esta clase nos permite mapear la vista a la base de datos,
 * y viceversa, utilizando las queries de hibernate.
 * Es decir, nos permite conocer toda la información de los 
 Acceso en la BD.
 * 
 * @author a-valderrama
 */
public class UtilidadAcceso {
    
    /**
     * Guarda el número de intentos de acceso fallidos del 
     * usuario.
     * 
     * @param acceso  Un objeto de tipo acceso que 
     *                representa el nuevo intento 
     *                de acceso fallido
     */
    public void guardaIntentos (Acceso acceso){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            sessionObj.save(acceso);
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
     * Nos dice si un usuario ya está en la tabla de 
     * los accesos
     * 
     * @param usuario Usuario a buscar
     * @return true  Si el usuario ya se encuentra en
     *               la tabla
     *         false En otro caso
     */
    public boolean buscaUsuario (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "FROM Acceso a WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario",usuario);
            Acceso acceso = (Acceso)query.uniqueResult();
            sessionObj.getTransaction().commit();
            if(acceso != null)
                return true;
            return false;
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
        return false;
    }
    
    /**
     * Actualiza los intentos de acceso fallidos del 
     * usuario
     * 
     * @param usuario Usuario que realizó un intento
     *                fallido
     */
    public void actualizaIntentos (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "UPDATE Acceso a SET a.intentos = a.intentos + 1 WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario", usuario);
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
    
    /**
     * Regresa la cantidad de intentos de acceso fallidos 
     * del usuario al sistema.
     * 
     * @param usuario Usuario que realizó un intento
     *                fallido
     * @return int    El número de intentos del 
     *                usuario
     */
    public int getIntentos (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "FROM Acceso a WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario",usuario);
            Acceso acceso = (Acceso)query.uniqueResult();
            sessionObj.getTransaction().commit();
            if(acceso != null)
                return acceso.getIntentos();
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
        return -1;
    }
    
    /**
     * Restablece la cantidad de intentos a 0.
     * 
     * @param usuario Usuario que realizó un intento
     *                fallido
     */
    public void resetIntentos (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "UPDATE Acceso a SET a.intentos = 0 WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario", usuario);
            query.executeUpdate();
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
     * Actualiza los errores por accesos fallidos del 
     * usuario.
     * 
     * @param usuario Usuario que realizó un intento
     *                fallido
     */
    public void updateErrores (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "UPDATE Acceso a SET a.errores = a.errores + 1 WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario", usuario);
            query.executeUpdate();
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
     * Regresa la cantidad de errores del usuario en 
     * el sistema.
     * 
     * @param usuario Usuario que realizó un intento
     *                fallido
     * @return int    El número de errores del 
     *                usuario
     */
    public int getErrores (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "FROM Acceso a WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario",usuario);
            Acceso acceso = (Acceso)query.uniqueResult();
            sessionObj.getTransaction().commit();
            return acceso.getErrores();
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
        return -1;
    }
    
    /**
     * Restablece la cantidad de errores del usuario en 
     * el sistema.
     * 
     * @param usuario Usuario que realizó un intento
     *                fallido
     */
    public void resetErrores (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "UPDATE Acceso a SET a.errores = 0 WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario", usuario);
            query.executeUpdate();
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
     * Establece la hora en la que se hizo el bloqueo
     * de la cuenta.
     * 
     * @param usuario Usuario al que se le bloqueo
     *                la cuenta.
     * @param hora    Hora a la que se realizo el 
     *                bloqueo
     */
    public void setHoraDeshabilitado (String usuario, String hora){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "UPDATE Acceso a SET a.horaDeshabilitado = :hora WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario", usuario);
            query.setString("hora", hora);
            query.executeUpdate();
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
     * Regresa la cantidad de errores del usuario en 
     * el sistema.
     * 
     * @param usuario Usuario que realizó los intentos
     *                fallidos
     * @return String La hora en la que se le 
     *                deshabilitó la cuenta
     */
    public String getHoraDeshabilitado (String usuario){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            String hql = "FROM Acceso a WHERE a.usuario = :usuario";
            Query query = sessionObj.createQuery(hql);
            query.setString("usuario",usuario);
            Acceso acceso = (Acceso)query.uniqueResult();
            sessionObj.getTransaction().commit();
            if( acceso != null){   
                return acceso.getHoraDeshabilitado();
            }
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

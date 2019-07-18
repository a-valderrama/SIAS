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
 * Es decir, nos permite conocer toda la información de la 
 * Bitacora de la BD.
 * 
 * @author a-valderrama
 */
public class UtilidadBitacora {
    
    static Layout comObj;
    static Session sessionObj;
    
    /**
     * Registra en la bitácora el nuevo acceso de 
     * un usuario.
     * 
     * @param nuevoRegistro El nuevo registro a poner 
     *                      en la bitácora
     */
    public void actualizaAcceso(Bitacora nuevoRegistro){
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            sessionObj.save(nuevoRegistro);
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
}

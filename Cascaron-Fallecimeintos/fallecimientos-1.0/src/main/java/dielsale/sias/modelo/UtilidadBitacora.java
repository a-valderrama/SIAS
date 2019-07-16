/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author dielsale
 */
public class UtilidadBitacora {
    
    static Layout comObj;
    static Session sessionObj;
    
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

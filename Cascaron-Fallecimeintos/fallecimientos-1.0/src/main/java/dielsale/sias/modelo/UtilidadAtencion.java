/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import org.hibernate.Session;

/**
 * Esta clase nos permite mapear la vista a la base
 * de datos, y viceversa, utilizando las queries 
 * de hibernate.
 * Es decir, nos permite conocer toda la información
 * de Atencion de la BD para utilizara en la
 * vista.
 * 
 * @author a-valderrama
 */
public class UtilidadAtencion {
    
    static Atencion comObj;
    static Session sessionObj;
    
    /**
     * Guarda el layout que se recibe como 
     * parámetro la base de datos. 
     * Con los campos previamente establecidos, 
     * a cada uno.
     * 
     * @param a Objeto de tipo Atencion a guardar 
     *          en la base de datos.
     */
    public void save(Atencion a) {
        try {
            sessionObj = HibernateUtil.getSessionFactory().openSession();
            sessionObj.beginTransaction();
            sessionObj.save(a);
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

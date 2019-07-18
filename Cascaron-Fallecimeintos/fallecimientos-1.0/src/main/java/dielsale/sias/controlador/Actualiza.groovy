/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dielsale.sias.controlador

import dielsale.sias.modelo.UtilidadBitacora
import java.time.LocalDateTime
import org.hibernate.type.TimestampType

/**
 *
 * @author dielsale
 */
class ActualizaBitacora {
    
    UtilidadBitacora u = new UtilidadBitacora()
    
    /**
     * Ingresa el registro de accesos de los usuarios
     * al sistema.
     *
     * @param String El usuario que realiza el 
     *               ingreso
     */
    public void acceso (String usuario){
        dielsale.sias.modelo.Bitacora nuevoRegistro = new dielsale.sias.modelo.Bitacora()
        LocalDateTime acceso = LocalDateTime.now()
        nuevoRegistro.setUsuario(usuario)
        nuevoRegistro.setAccion("Acceso al sistema")
        nuevoRegistro.setAcceso(acceso as String)
        u.actualizaAcceso(nuevoRegistro)
    }
}


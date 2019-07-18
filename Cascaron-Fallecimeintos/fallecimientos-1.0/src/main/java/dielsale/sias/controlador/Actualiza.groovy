/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dielsale.sias.controlador

import dielsale.sias.modelo.Acceso
import dielsale.sias.modelo.Bitacora
import dielsale.sias.modelo.UtilidadAcceso
import dielsale.sias.modelo.UtilidadBitacora
import dielsale.sias.modelo.UtilidadUsuario
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Esta clase se encarga de actualizar los 
 * registros en la bitácora (BD), así como 
 * los intentos de acceso fallidos 
 *
 * @author a-valderrama
 */
class Actualiza {
    
    UtilidadBitacora uBitacora = new UtilidadBitacora()
    UtilidadAcceso uAccesos = new UtilidadAcceso()
    UtilidadUsuario uUsuario = new UtilidadUsuario()
    
    /**
     * Ingresa el registro de accesos de los usuarios
     * al sistema.
     *
     * @param String El usuario que realiza el 
     *               ingreso
     */
    public void acceso (String usuario){
        Bitacora nuevoRegistro = new Bitacora()
        LocalDateTime acceso = LocalDateTime.now()
        nuevoRegistro.setUsuario(usuario)
        nuevoRegistro.setAccion("Acceso al sistema")
        nuevoRegistro.setAcceso(acceso as String)
        uBitacora.actualizaAcceso(nuevoRegistro)
    }
    
    /**
     * Ingresa el registro de intentos de acceso de los 
     * usuarios al sistema.
     *
     * @param String El usuario que trata de realizar  
     *               el ingreso
     */
    public void intentoFallido (String usuario){
        Acceso a = new Acceso()
        a.setUsuario(usuario)
        if(uAccesos.buscaUsuario(usuario)){
            uAccesos.actualizaIntentos(usuario)
            return
        }
        a.setIntentos(1)
        uAccesos.guardaIntentos(a)
    }
    
    /**
     * Nos dice la cantidad de intentos de acceso 
     * fallidos a tenido el usuario al sistema.
     *
     * @param String El usuario que ha realizado
     *               los intentos
     * @return int   La cantidad de intentos que 
     *               están registrados en el 
     *               sistema.
     */
    public int getIntentos (String usuario){
        return uAccesos.getIntentos(usuario) 
    }
    
    /**
     * Restablece la cantidad de intentos de acceso 
     * fallidos a tenido el usuario al sistema.
     *
     * @param String El usuario que ha realizado
     *               los intentos
     * @return 2     Si lla se cometieron 2 
     *               errores, es decir se bloqueo
     *               la cuenta
     *         1     En otro caso
     */
    public int resetIntentos (String usuario){
        uAccesos.updateErrores(usuario)
        if(uAccesos.getErrores(usuario) == 2){
            uUsuario.bloqueaCuenta(usuario)
            uAccesos.resetErrores(usuario)
            uAccesos.resetIntentos(usuario) 
            return 2
        }
        uAccesos.resetIntentos(usuario)
        LocalTime acceso = LocalTime.now()
        uAccesos.setHoraDeshabilitado(usuario, acceso as String)
        return 1
    }
    
    /**
     * Nos dice el status (bloqueada o no) de
     * la cuenta del usuario.
     *
     * @param String El usuario del cual queremos
     *               verificar la cuenta.
     * @return true  Si está bloqueada
     *         false En otro caso
     */
    public boolean getStatus (String usuario){
        return uUsuario.getStatus(usuario)
    }
    
    /**
     * Nos dice la hora en la que se bloqueo
     * la cuenta del usuario.
     *
     * @param String  El usuario del cual queremos
     *                verificar el bloqueo.
     * @return String Hora del bloqueo.
     */
    public String getHoraDeshabilitado (String usuario){
        return uAccesos.getHoraDeshabilitado(usuario)
    }
}


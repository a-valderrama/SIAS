/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dielsale.sias.controlador

import javax.faces.bean.ManagedBean
import javax.faces.bean.SessionScoped

/**
 *
 * @author a-valderrama
 */
@ManagedBean
@SessionScoped
class Display {
    
    IniciaSesion i = new IniciaSesion()
    
    /**
     * Nos dice si el usuario logeado puede, o 
     * no, el módulo de gestionar coberturas
     *
     * @return false Si su perfil es nulo o de 
     *               otras áreas.
     *         true  En otro caso.
     */
    public boolean gModulo(){
        def perfil = i.getTipoUsuario()
        if(perfil.equals("null"))
            return false
        return true
    }
    
    /**
     * Nos dice si el perfil del usuario logeado 
     * es de otras áreas.
     *
     * @return true  Si su perfil es de otras áreas
     *         false En otro caso
     */
    public boolean esOtra(){
        def perfil = i.getTipoUsuario()
        if(perfil.matches("otras areas"))
            return true
        return false
    }
    
    /**
     * Nos dice si el perfil del usuario logeado 
     * es Analista 1.
     *
     * @return true  Si su perfil es analista1
     *         false En otro caso
     */
    public boolean esAnalista1(){
        def perfil = i.getTipoUsuario()
        if(perfil.matches("analista1"))
            return true
        return false
    }
    
    /**
     * Nos dice si el perfil del usuario logeado 
     * es Analista 2.
     *
     * @return true  Si su perfil es analista2
     *         false En otro caso
     */
        public boolean esAnalista2(){
        def perfil = i.getTipoUsuario()
        if(perfil.matches("analista2"))
            return true
        return false
    }
    
    /**
     * Nos dice si el perfil del usuario logeado 
     * es Analista 3.
     *
     * @return true  Si su perfil es analista3
     *         false En otro caso
     */
        public boolean esAnalista3(){
        def perfil = i.getTipoUsuario()
        if(perfil.matches("analista3"))
            return true
        return false
    }
    
    /**
     * Nos dice si el perfil del usuario logeado 
     * es subdirector.
     *
     * @return true  Si su perfil es subdirector
     *         false En otro caso
     */
    public boolean esSubdirector(){
        def perfil = i.getTipoUsuario()
        if(perfil.matches("subdirector"))
            return true
        return false
    }
    
    /**
     * Nos dice si el perfil del usuario logeado 
     * es director.
     *
     * @return true  Si su perfil es director
     *         false En otro caso
     */
    public boolean esDirector(){
        def perfil = i.getTipoUsuario()
        if(perfil.matches("director"))
            return true
        return false
    }
    
    /**
     * Nos dice si el perfil del usuario logeado 
     * es administrador.
     *
     * @return true  Si su perfil es administrador
     *         false En otro caso
     */
    public boolean esAdministrador(){
        def perfil = i.getTipoUsuario()
        if(perfil.matches("administrador"))
            return true
        return false
    }
}


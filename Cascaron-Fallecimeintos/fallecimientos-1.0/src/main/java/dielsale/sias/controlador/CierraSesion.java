/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Esta clase nos pertmitirá cerrar sesión
 * desde la vista. Básicamente se quita del 
 * contexto el tipo de usuario logeado.
 * 
 * @author Alejandro Valderrama para Dielsale
 */
@ManagedBean
@RequestScoped
public class CierraSesion {
    
    public CierraSesion() {
        FacesContext.getCurrentInstance()
                .getViewRoot()
                .setLocale(new Locale("es-Mx"));
    }
    
    public String cierra(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Se ha cerrado la sesión", ""));
        return "inicia-sesion.xhmtl?faces-redirect=true";
    }    
}
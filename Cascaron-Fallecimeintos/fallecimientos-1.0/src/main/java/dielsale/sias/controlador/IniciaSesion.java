/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import dielsale.sias.modelo.Administrador;
import dielsale.sias.modelo.UtilidadAdministrador;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.concurrent.TimeUnit;
import javax.faces.bean.SessionScoped;

/**
 * Esta clase nos pertmitirá iniciar sesión
 * desde la vista. Básicamente pone en el 
 * contexto el tipo de usuario que fue 
 * logeado. Para posteriormente poder usarlo
 * con facilidad.
 * 
 * @author a-valderrama
 */
@ManagedBean
@SessionScoped
public class IniciaSesion {
    
    //Modelo del Administrador
    UtilidadAdministrador uAdmin = new UtilidadAdministrador();
    //Correo y contraseña del usuario realizando login
    private String usuario = new String();
    private String pwd = new String();
    
    //Constructor por default
    public IniciaSesion() {
        FacesContext.getCurrentInstance()
                .getViewRoot()
                .setLocale(new Locale("es-Mx"));
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    /**
     * Método con el que gestionaremos el incio de 
     * sesión de la aplicación. Esto lo hacemos buscando
     * el usuario en la base de datos. Luego ponemos el 
     * tipo del usuario en el "context" de la aplicación.
     * 
     * @return String Acción que queremos que haga el xhtml
     *                después del login
     */
    public String inicia(){
        Administrador admin = uAdmin.buscaPorUsuario(usuario);
        if(admin != null){
            if(admin.getContrasenia().equals(pwd)){
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("tipo_usuario", "administrador");
                context.getExternalContext().getSessionMap().put("usuario", admin);
                FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Se inició sesión correctamente como administrador", ""));
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(InterruptedException e){
                    System.out.println("Error");
                }
                return "modulos.xhmtl?faces-redirect=true";
            }
             else{
                FacesContext.getCurrentInstance()
                .addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Usuario o contraseña incorrecto", ""));
                return "";
            }
        }
        return "";
    }
    
    /**
     * Método que nos regresa el tipo de usuario que hizo
     * log in. Obtenemos el tipo de usuario guardado en el 
     * sessionMap.
     * 
     * @return String Tipo de usuario que ha realizado 
     *                login
     */
    public String getTipoUsuario(){
        FacesContext context = FacesContext.getCurrentInstance();
        String tipo = (String)context.getExternalContext().getSessionMap().get("tipo_usuario");
        tipo = "usuario."+tipo;
        return tipo;
    }
}

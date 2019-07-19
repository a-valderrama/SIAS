/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import dielsale.sias.modelo.Usuario;
import dielsale.sias.modelo.UtilidadUsuario;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.util.concurrent.TimeUnit;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

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
    
    //Modelo del usuario
    UtilidadUsuario u = new UtilidadUsuario();
    //Correo y contraseña del usuario realizando login
    private String user = new String();
    private String pwd = new String();
    
    //Constructor por default
    public IniciaSesion() {
        FacesContext.getCurrentInstance()
                .getViewRoot()
                .setLocale(new Locale("es-Mx"));
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        Usuario usuario = u.buscaPorUsuario(user);
        FacesContext context = FacesContext.getCurrentInstance();
        RequestContext rContext = RequestContext.getCurrentInstance();
        Actualiza actualiza = new Actualiza();
        if(usuario != null){
            String nombreUsuario = usuario.getUsuario();
            if(actualiza.getStatus(nombreUsuario)){
                //El acceso es incorrecto pues tiene la cuenta bloqueada
                rContext.execute("PF('bloqueado').show()");
                return "";
            }
            if(usuario.getTipo().trim().equals("")){
                rContext.execute("PF('perfil').show()");
                return "";
            }
            //verificamos que la cuenta ya esté habilitada
            if(actualiza.getHoraDeshabilitado(nombreUsuario) != null){
                LocalTime horaDeshabilitado = LocalTime.parse(actualiza.getHoraDeshabilitado(nombreUsuario)).plus(5, ChronoUnit.MINUTES);
                LocalTime horaActual = LocalTime.now();
                System.out.println("-----"+horaDeshabilitado.isBefore(horaActual)+"-----");
                System.out.println("Hora BD+5:"+horaDeshabilitado);
                System.out.println("Hora Actual"+horaActual);
                if(horaDeshabilitado.isAfter(horaActual)){
                    rContext.execute("PF('deshabilitado').show()");
                    return "";
                }
            }
            if(usuario.getContrasenia().equals(pwd)){
                context.getExternalContext().getSessionMap().put("tipo_usuario", usuario.getTipo());
                context.getExternalContext().getSessionMap().put("usuario", usuario.getUsuario());
                //El acceso es correcto
                actualiza.acceso(nombreUsuario);
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(InterruptedException e){
                    System.out.println("Error");
                }
                return "modulos.xhmtl?faces-redirect=true";
            }
            //acceso incorrecto
            FacesContext.getCurrentInstance()
            .addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usuario o contraseña incorrecto", ""));
            if(actualiza.getIntentos(nombreUsuario) == 5){
                if(actualiza.resetIntentos(nombreUsuario) == 2){
                    rContext.execute("PF('bloqueado').show()");
                    return"";
                }
                rContext.execute("PF('5_intentos').show()");
                return "";
            }
            actualiza.intentoFallido(nombreUsuario);
            return "";
        }
        FacesContext.getCurrentInstance()
            .addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Usuario o contraseña incorrecto", ""));
        return "";
    }
    
    /**
     * Método que nos regresa el usuario que hizo
     * log in y la forma en la que se mostrará en
     * pantalla. Los obtenemos del sessionMap.
     * 
     * @return String El usuario a mostrar en las vistas.
     */
    public String getUsuarioCompleto(){
        FacesContext context = FacesContext.getCurrentInstance();
        String tipo = (String)context.getExternalContext().getSessionMap().get("tipo_usuario");
        String usuario = (String)context.getExternalContext().getSessionMap().get("usuario");
        return usuario+"."+tipo;
    }
    
    /**
     * Método que nos regresa el perfil del usuario 
     * que hizo log in. Lo obtenemos del sessionMap.
     * 
     * @return String El perfil del usuario que 
     *                realizo el log in.
     */
    public String getTipoUsuario(){
        FacesContext context = FacesContext.getCurrentInstance();
        String tipo = (String)context.getExternalContext().getSessionMap().get("tipo_usuario");
        return tipo;
    }
    
     /**
     * Método que nos regresa el nombre del usuario 
     * que hizo log in. Lo obtenemos del sessionMap.
     * 
     * @return String El nombre del usuario que 
     *                realizo el log in.
     */
    public String getUsuario(){
        FacesContext context = FacesContext.getCurrentInstance();
        String usuario = (String)context.getExternalContext().getSessionMap().get("usuario");
        return usuario;
    }
}

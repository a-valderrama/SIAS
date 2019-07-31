/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import dielsale.sias.modelo.Repetidos;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 * Muestra los mensajes correspondientes a la 
 * funcionalidad de integrar los reclamos.
 * 
 * @author Alejandro Valderrama para Dielsale
 */

@ManagedBean
@SessionScoped
public class MensajeIntegrado {
    
    private RequestContext rContext = RequestContext.getCurrentInstance();
    
    /**
     * Método para desplegar mensaje de error
     * general al integrar reclamos. 
     */
    public void noMatch(){
        rContext.execute("PF('no_match').show()");
    }
    
    /**
     * Método auxiliar para desplegar mensaje de 
     * error por no tener ninguna opción válida
     * al integrar reclamos. 
     */
    public void ningunoValido(){
        rContext.execute("PF('nin_valido').show()");
    }
    
    /**
     * Método auxiliar para desplegar mensaje de 
     * integración exitosa con registros repetidos
     * 
     * @param rep La lista de los elementos 
     *            repetidos después de integrar 
     *            los archivos correspondientes.
     */
    public void rep(ArrayList<Repetidos> rep){
        FacesContext fContext =  FacesContext.getCurrentInstance();
        fContext.getExternalContext().getSessionMap().put("repetidos", rep);
        rContext.execute("PF('rep').show()");
    }
    
    /**
     * Método auxiliar para desplegar mensaje de 
     * integración exitosa sin registros repetidos
     */
    public void noRep(){
        rContext.execute("PF('no_rep').show()");
    }
    
    /**
     * Método que nos regresa todos los elementos 
     * repetidos en el archivo integrado
     * 
     * @return Lista de los registros repetidos
     */
    public ArrayList<Repetidos> getRep(){
        FacesContext fContext =  FacesContext.getCurrentInstance();
        ArrayList<Repetidos> rep = (ArrayList<Repetidos>)fContext.getExternalContext().getSessionMap().get("repetidos");
        return rep;
    }
}

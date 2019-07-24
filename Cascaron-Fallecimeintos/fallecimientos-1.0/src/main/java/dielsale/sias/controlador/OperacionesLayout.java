/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import dielsale.sias.modelo.Fallecimiento;
import dielsale.sias.modelo.UtilidadFallecimiento;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Esta clase es la que nos permite hacer las operaciones
 * necesarias con el layout. Como por ejemplo filtrar
 * la tabla mostrada en la vista: 
 * gestion-coberturas-fii_soporte.xhtml
 * 
 * @author a-valderrama
 */
@ManagedBean
@RequestScoped
public class OperacionesLayout {
    
    UtilidadFallecimiento u = new UtilidadFallecimiento();
    
    /**
     * Método que nos regresa los datos del layout en
     * la base de datos.
     * 
     * @return List<Layout> datos del layout en la BD.
     */
    public List<Fallecimiento> getLayout(){
        List<Fallecimiento> layout = u.getLayout();
        return layout;
    }
    
    /**
     * Este método regresa un objeto de tipo Fallecimiento de la
 BD filtrado por su id.
     * EL id del Fallecimiento es del que se quiere adjuntar un 
 soporte, y se obtiene del session-map.
     * 
     * @return Fallecimiento elemento filtrado por su id.
     */
    public dielsale.sias.modelo.Fallecimiento getElementoSoporte(){
        dielsale.sias.modelo.Fallecimiento elemento = u.getElemento(getIdElemento());
        return elemento;
    }
    
    /**
     * En este método verificamos el status del soporte en 
     * la BD. 
     * "true" se refiere a que el soporte ya ha sido subido
     * y "false" que no.
     * 
     * @param id El id del cual queremos saber el status
     *           del soporte
     * @return El status del soporte
     */
    public boolean getSoporte(int id){
        return u.getElemento(id).getSoporte();
    }
    
    /*
     * Regresa el id del elemento del layout en cuestión
     */
    private int getIdElemento(){
        FacesContext context = FacesContext.getCurrentInstance();
        int id = (int)context.getExternalContext().getSessionMap().get("id_layout");
        return id;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta clase la usamos para tener acceso a los 
 * atributos de la bitácora desde la vista. 
 * Pero además definimos las columnas de la base
 * de datos que se va a leer. Así como el nombre 
 * de la tabla.
 * 
 * @author a-valderrama
 */
@Entity
@Table(catalog = "SIAS", schema = "public", name = "bitacora")
public class Bitacora {
    
    @Id
    @Column(name = "id_bitacora")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id_bitacora;
    
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "accion")
    private String accion;
    
    @Column(name = "acceso")
    private String acceso;

    public String getIdBitacora() {
        return id_bitacora;
    }

    public void setIdBitacora(String id_bitacora) {
        this.id_bitacora = id_bitacora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
}

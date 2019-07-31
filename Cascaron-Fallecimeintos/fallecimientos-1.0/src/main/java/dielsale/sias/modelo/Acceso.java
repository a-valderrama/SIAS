/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Esta clase la usamos para tener acceso a los 
 * atributos del acceso desde la vista. 
 * Pero además definimos las columnas de la base
 * de datos que se va a leer. Así como el nombre 
 * de la tabla.
 * 
 * @author Alejandro Valderrama para Dielsale
 */
@Entity
@Table(catalog = "SIAS", schema = "public", name = "accesos")
public class Acceso {
    
    @Id
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "intentos")
    private int intentos;
    
    @Column(name = "errores")
    private int errores; 
    
    @Column(name = "hora_deshabilitado")
    private String horaDeshabilitado;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public String getHoraDeshabilitado() {
        return horaDeshabilitado;
    }

    public void setHoraDeshabilitado(String horaDeshabilitado) {
        this.horaDeshabilitado = horaDeshabilitado;
    }
}

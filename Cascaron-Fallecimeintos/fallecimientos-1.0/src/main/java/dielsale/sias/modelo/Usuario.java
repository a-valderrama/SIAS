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
 * atributos del administrador desde la vista. 
 * Pero además definimos las columnas de la base
 * de datos que se va a leer. Así como el nombre 
 * de la tabla.
 *  
 * @author Alejandro Valderrama para Dielsale
 */
@Entity
@Table(catalog = "SIAS", schema = "public", name = "usuario")
public class Usuario{
    
    @Id
    @Column(name = "usuario")
    private String usuario;
    
    @Column(name = "contraseña")
    private String contrasenia;
    
    @Column(name = "tipo")
    private String tipo;
    
    @Column(name = "bloqueado")
    private boolean bloqueado;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}

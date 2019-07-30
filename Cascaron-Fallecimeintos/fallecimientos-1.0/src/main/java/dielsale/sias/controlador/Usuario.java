/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Esta clase la usamos para tener acceso a los 
 * atributos del usuario desde la vista
 * 
 * @author a-valderrama
 * 
 */
public class Usuario {
    
    private String usuario;
    
    private String contrasenia;
    
    private String tipo;

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
}

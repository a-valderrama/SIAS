/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

/**
 * Elementos repetidos que tendremos que
 * guardar para reportar en la vista después
 * de integrar.
 * 
 * @author Alejandro Valderrama para Dielsale
 */
public class Repetidos {
    
    private int idCredito;
    private int idTrabajador;
    //De que archivo viene el elemento repetido
    private String fuente;

    public int getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(int idCredito) {
        this.idCredito = idCredito;
    }

    public int getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(int idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
}

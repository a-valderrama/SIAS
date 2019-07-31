/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.modelo;

/**
 * Clase con la que estableceremos la ubicación 
 * de los errores, sintácticos, del layout
 * cargado.
 * 
 * @author Alejandro Valderrama para Dielsale
 */
public class ErrorLayout {
    
    private int idCredito;
    
    private int renglon;
    
    private int columna;

    public int getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(int idCredito) {
        this.idCredito = idCredito;
    }

    public int getRenglon() {
        return renglon;
    }

    public void setRenglon(int renglon) {
        this.renglon = renglon;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}

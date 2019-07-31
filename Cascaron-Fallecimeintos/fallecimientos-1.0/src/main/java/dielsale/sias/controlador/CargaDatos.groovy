/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dielsale.sias.controlador

import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import dielsale.sias.modelo.Atencion
import dielsale.sias.modelo.Desempleo
import dielsale.sias.modelo.ErrorLayout
import dielsale.sias.modelo.Fallecimiento
import dielsale.sias.modelo.UtilidadAtencion
import dielsale.sias.modelo.UtilidadDesempleo
import dielsale.sias.modelo.UtilidadFallecimiento
import java.nio.file.Path
import java.text.SimpleDateFormat
import javax.faces.context.FacesContext
import org.primefaces.model.UploadedFile

/**
 * Esta clase se encarga de hacer las 
 * validaciones a los registros del archivo
 * subido por el usuario. 
 * En caso de que todos sean correctos carga
 * la información del archivo a la table 
 * correspondiente
 *
 * @author Alejandro Valderrama para Dielsale
 */
class CargaDatos {
    
    //Regex compartidas por algunos métodos
    private def numeric = "^[0-9*]+\$"
    private def alphaNumeric = "^[a-zA-Z0-9*]+\$"
    private def numRegistros = 0
    
    /**
     * Valida el formato de los datos del archivo 
     * con respecto a la especificaión de 
     * requerimientos.
     * Además, si no hay errores, guarda todos
     * los registros en la base de datos.
     *
     *@param file El archivo que contiene la 
     *            información que queremos validar
     *            
     *@return Lista con todos los errores que 
     *        contiene el archivo
     *
     */
    public ArrayList<ErrorLayout> validaDatos(File file){
        def display = new Display()
        def FileReader filereader = new FileReader(file)
        //nos saltamos la primera línea porque es el header
        def CSVReader csvReader = new CSVReaderBuilder(filereader) 
                                        .withSkipLines(1) 
                                        .build()
        def String[] next
        def renglon = 1
        def analista
        //Para evitar errores utilizamos una cadena vacía
        def creditoIncorrecto = ""
        def ErrorLayout error
        def errores = new ArrayList<ErrorLayout>() 
        def registrosFallecimiento = new ArrayList<Fallecimiento>()
        def registrosAtencion = new ArrayList<Atencion>()
        def registrosDesempleo = new ArrayList<Desempleo>()
        def uAtencion = new UtilidadAtencion()
        def uFallecimiento = new UtilidadFallecimiento()
        def uDesempleo = new UtilidadDesempleo()
        //Establecemos con que tipo de carga estamos tratando
        def cargaDesempelo = display.esAnalista1() || display.esAdministrador()
        def cargaFallecimiento = display.esAnalista2() || display.esAdministrador()
        def cargaAtencion = display.esAnalista3() || display.esAdministrador()
        
        /*if(display.esAnalista3() || display.esAdministrador())
            eturn validaAtencion(file)*/
        
        while((next = csvReader.readNext()) != null){
                
            if(!esAlfaNumerico(next[0])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(1)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esEndoso(next[1])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(2)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esId(next[2])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(3)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumerico(next[3])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(4)
                error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumerico(next[4])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(5)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esTexto(next[5])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(6)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esTextoNull(next[6])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(7)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esTexto(next[7])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(8)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esTextoNull(next[8])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(9)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFecha(next[9])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(10)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esSexo(next[10])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(11)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esRFC(next[11])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(12)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFloat(next[12])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(13)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esEmpresa(next[13])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(14)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esTextoNull(next[14])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(15)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumTelefono(next[15])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(16)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumTelefono(next[16])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(17)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esSeguroSocial(next[17])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(18)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumSS(next[18])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(19)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumericoNull(next[19])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(20)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esProducto(next[20])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(21)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esSeguroSocial(next[21])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(22)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esIdEnvio(next[22])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(23)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumerico(next[23])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(24)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFecha(next[24])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(25)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumerico(next[25])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(26)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFloat (next[26])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(27)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFecha(next[27])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(28)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFecha(next[28])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(29)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esPlazo(next[29])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(30)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esMovimiento(next[30])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(31)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFecha(next[31])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(32)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esFecha(next[32])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(33)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esNumericoNull(next[33])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(34)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else if(!esActual(next[34])){
                error = new ErrorLayout()
                error.setRenglon(renglon)
                error.setColumna(35)
                if(esNumerico(next[3]))
                    error.setIdCredito(next[3] as Integer)
                else
                    error.setIdCredito(creditoIncorrecto as Integer)
                errores.add(error)
            }

            else{ 
                if(cargaFallecimiento){
                    def f = setFallecimiento(next)
                    registrosFallecimiento.add(f)
                }
                if(cargaAtencion){
                    def a = setAtencion(next)
                    registrosAtencion.add(a)
                }
                if(cargaDesempelo){
                    def d = setDesempleo(next)
                    registrosDesempleo.add(d)
                }
            }

            renglon++
        }
        if(errores.isEmpty()){
            if(cargaFallecimiento){
                registrosFallecimiento.each{ registro ->
                    uFallecimiento.save(registro)
                }
                numRegistros = registrosFallecimiento.size()
            }
            if(cargaAtencion){
                registrosAtencion.each{ registro ->
                    uAtencion.save(registro)
                }
                numRegistros = registrosAtencion.size()
            }
            if(cargaDesempelo){
                registrosDesempleo.each{ registro ->
                    uDesempleo.save(registro)
                }
                numRegistros = registrosDesempleo.size()
            }
        }
        
        return errores
        
        
        //Para las pruebas de validaciones de los archivos. Quitar después de las pruebas
        /*println (esAlfaNumerico(next[0]).toString() + "\t" + esEndoso(next[1]) + \
                "\t" + esId(next[2]) + "\t" + esNumerico(next[3]) + "\t" +  esNumerico(next[4]) + \
                "\t" + esTexto(next[5]) + "\t" + esTextoNull(next[6])  + "\t" + esTexto(next[7]) + \
                "\t" + esTextoNull(next[8]) + "\t" + esFecha(next[9])  + "\t" + esSexo(next[10]) + \
                "\t" + esRFC(next[11]) + "\t" + esFloat(next[12]) + "\t" +esEmpresa(next[13]) + \
                "\t" + esTextoNull(next[14]) + "\t" + esNumTelefono(next[15]) + \
                "\t" + esNumTelefono(next[16]) + "\t" + esSeguroSocial(next[17]) + \
                "\t" + esNumSS(next[18]) + "\t" + esNumericoNull(next[19]).toString() + \
                "\t" + esProducto(next[20]) + "\t" + esSeguroSocial(next[21]) + \
                "\t" + esIdEnvio(next[22]) + "\t" +esNumerico(next[23])  + "\t" + esFecha(next[24]) + \
                "\t" + esNumerico(next[25]) + "\t" +esFloat (next[26]) + "\t" + esFecha(next[27])+ \
                "\t" + esFecha(next[28]) + "\t" + esPlazo(next[29]) + "\t" + esMovimiento(next[30]) + \
                "\t" + esFecha(next[31])+ "\t" + esFecha(next[32]) + "\t" + esNumericoNull(next[33])+\
                "\t" + esActual(next[34])
                )*/
    }
    
    /**
     * Método que regresa el número de 
     * registros.
     * 
     * @return El número de registros.
     */
    public int getNumRegistros(){
        return numRegistros
    }
    
    /*
     * Carga los elementos del layout de 
     * fallecimientos.
     */
    private Fallecimiento setFallecimiento(String[] next){
        def fallecimiento = new Fallecimiento()
        
        fallecimiento.setPoliza(next[0])
        fallecimiento.setEndoso(next[1].toInteger())
        fallecimiento.setId_aseguradora(next[2].toInteger())
        fallecimiento.setId_credito(next[3].toInteger())
        fallecimiento.setId_trabajador(next[4].toInteger())
        fallecimiento.setPaterno(next[5])
        fallecimiento.setMaterno(next[6])
        fallecimiento.setNombre(next[7])
        fallecimiento.setSegundo_nombre(next[8])
        fallecimiento.setFecha_nacimiento(next[9])
        fallecimiento.setSexo(next[10] as char)
        fallecimiento.setRfc_trabajador(next[11])
        fallecimiento.setImporte(next[12].toDouble())
        fallecimiento.setEmpresa(next[13])
        fallecimiento.setDomicilio_ct(next[14])
        fallecimiento.setTel_principal(next[15])
        fallecimiento.setTel_cel(next[16])
        fallecimiento.setTipo_seg_social(next[17].toInteger())
        fallecimiento.setNum_seguro_social(next[18])
        fallecimiento.setSucursal(next[19].toInteger())
        fallecimiento.setProducto(next[20].toInteger())
        fallecimiento.setCobertura(next[21].toInteger())
        fallecimiento.setId_envio(next[22].toInteger())
        fallecimiento.setReferencia_bancaria(next[23].toDouble())
        fallecimiento.setFecha_baja(next[24])
        fallecimiento.setSuma_reclamospag(next[25].toDouble())
        fallecimiento.setSuma_aseguradoratot(next[26].toDouble())
        fallecimiento.setFecha_inicio_recl(next[27])
        fallecimiento.setFecha_primervto(next[28])
        fallecimiento.setPlazo(next[29].toInteger())
        fallecimiento.setTipo_movimiento(next[30] as char)
        fallecimiento.setFecha_inicio(next[31])
        fallecimiento.setFecha_vto_final(next[32])
        if(esNumerico(next[33]) && !next[33].matches(""))
            fallecimiento.setMax_r_pagadas(next[33].toInteger())   
        if(esNumerico(next[34]) && !next[34].matches(""))
            fallecimiento.setActuales(next[34].toInteger())
        return fallecimiento
    }
    
    /*
     * Carga los elementos del layout de 
     * Atencion a Clientes.
     */
    private Atencion setAtencion(String[] next){
        def atencion = new Atencion()
        
        atencion.setPoliza(next[0])
        atencion.setEndoso(next[1].toInteger())
        atencion.setId_aseguradora(next[2].toInteger())
        atencion.setId_credito(next[3].toInteger())
        atencion.setId_trabajador(next[4].toInteger())
        atencion.setPaterno(next[5])
        atencion.setMaterno(next[6])
        atencion.setNombre(next[7])
        atencion.setSegundo_nombre(next[8])
        atencion.setFecha_nacimiento(next[9])
        atencion.setSexo(next[10] as char)
        atencion.setRfc_trabajador(next[11])
        atencion.setImporte(next[12].toDouble())
        atencion.setEmpresa(next[13])
        atencion.setDomicilio_ct(next[14])
        atencion.setTel_principal(next[15])
        atencion.setTel_cel(next[16])
        atencion.setTipo_seg_social(next[17].toInteger())
        atencion.setNum_seguro_social(next[18])
        atencion.setSucursal(next[19].toInteger())
        atencion.setProducto(next[20].toInteger())
        atencion.setCobertura(next[21].toInteger())
        atencion.setId_envio(next[22].toInteger())
        atencion.setReferencia_bancaria(next[23].toDouble())
        atencion.setFecha_baja(next[24])
        atencion.setSuma_reclamospag(next[25].toDouble())
        atencion.setSuma_aseguradoratot(next[26].toDouble())
        atencion.setFecha_inicio_recl(next[27])
        atencion.setFecha_primervto(next[28])
        atencion.setPlazo(next[29].toInteger())
        atencion.setTipo_movimiento(next[30] as char)
        atencion.setFecha_inicio(next[31])
        atencion.setFecha_vto_final(next[32])
        if(esNumerico(next[33]) && !next[33].matches(""))
            atencion.setMax_r_pagadas(next[33].toInteger())   
        if(esNumerico(next[34]) && !next[34].matches(""))
            atencion.setActuales(next[34].toInteger())
        return atencion
    }
    
    /*
     * Carga los elementos del layout de 
     * Desempelo a Clientes.
     */
    private Desempleo setDesempleo(String[] next){
        def desempelo = new Desempleo()
        
        desempelo.setPoliza(next[0])
        desempelo.setEndoso(next[1].toInteger())
        desempelo.setId_aseguradora(next[2].toInteger())
        desempelo.setId_credito(next[3].toInteger())
        desempelo.setId_trabajador(next[4].toInteger())
        desempelo.setPaterno(next[5])
        desempelo.setMaterno(next[6])
        desempelo.setNombre(next[7])
        desempelo.setSegundo_nombre(next[8])
        desempelo.setFecha_nacimiento(next[9])
        desempelo.setSexo(next[10] as char)
        desempelo.setRfc_trabajador(next[11])
        desempelo.setImporte(next[12].toDouble())
        desempelo.setEmpresa(next[13])
        desempelo.setDomicilio_ct(next[14])
        desempelo.setTel_principal(next[15])
        desempelo.setTel_cel(next[16])
        desempelo.setTipo_seg_social(next[17].toInteger())
        desempelo.setNum_seguro_social(next[18])
        desempelo.setSucursal(next[19].toInteger())
        desempelo.setProducto(next[20].toInteger())
        desempelo.setCobertura(next[21].toInteger())
        desempelo.setId_envio(next[22].toInteger())
        desempelo.setReferencia_bancaria(next[23].toDouble())
        desempelo.setFecha_baja(next[24])
        desempelo.setSuma_reclamospag(next[25].toDouble())
        desempelo.setSuma_aseguradoratot(next[26].toDouble())
        desempelo.setFecha_inicio_recl(next[27])
        desempelo.setFecha_primervto(next[28])
        desempelo.setPlazo(next[29].toInteger())
        desempelo.setTipo_movimiento(next[30] as char)
        desempelo.setFecha_inicio(next[31])
        desempelo.setFecha_vto_final(next[32])
        if(esNumerico(next[33]) && !next[33].matches(""))
            desempelo.setMax_r_pagadas(next[33].toInteger())   
        if(esNumerico(next[34]) && !next[34].matches(""))
            desempelo.setActuales(next[34].toInteger())
        return desempelo
    }
   
    /*
     * Nos dice si el valor es alfanumerico
     */
    private boolean esAlfaNumerico (String value){
        return value.replaceAll("\\s+","").matches(alphaNumeric)
    }
    
    /*
     * Nos dice si el valor es numerico y tiene la 
     * extension especificada.
     */
    private boolean esEndoso (String value){
        return (value.matches(numeric) && value.length() == 6)
    }
    
    /*
     * Nos dice si el valor es numerico.
     */
    private boolean esNumerico (String value){
        return value.matches(numeric)
    }
    
    /*
     * Nos dice si es númerico o null
     */
    private boolean esNumericoNull (String value){
        def branch = "^[0-9]*\$"
        if(value == "")
            return true
        return value.matches(branch)
    }
    
    /*
     * Nos dice si el valor es flotante, con dos 
     * decimales
     */
    private boolean esFloat (String value){
        def floatPrecision = "^[[0-9]*+.+[0-9]+[0-9]]+\$"
        return value.matches(floatPrecision)
    }
    
    /*
     * Nos dice si el valor es numerico y es un id válido
     */
    private boolean esId (String value){
        def id = "^[1,2,4,5,6,7,8]+\$"
        return value.matches(id)
    }
    
    /*
     * Nos dice si el valor es solamente letras
     */
    private boolean esTexto (String value){
        def text = "^[a-z*A-Z*_ ]*\$"
        return value.matches(text)
    }
    
    /*
     * Nos dice si el valor es solamente letras o es 
     * null
     */
    private boolean esTextoNull (String value){
        def textNull = "^[a-z*A-Z*]*\$"
        if(value == "")
            return true
        return value.replaceAll("\\s+","").matches(textNull)
    }
    
    /*
     * Nos dice si es una fecha con formato dd/mm/aaaa 
     */
    private boolean esFecha(String value){
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy")
            format.parse(value)
            return true
        }catch(Exception e){
            return false
        }
    }
    
    /*
     * Nos dice si el sexo es correcto
     */    
    private boolean esSexo (String value){
        def sex = "^[M,F]+\$"
        return value.matches(sex)
    }
    
    /*
     * Nos dice si el RFC es correcto
     */
    private boolean esRFC (String value){
        return (value.matches(alphaNumeric) && value.length() == 13)
    }
    
    /*
     * Nos dice si el valor es un número telefónico o es 
     * null
     */
    private boolean esNumTelefono (String value){
        def alphaNumericNull = "^[0-9]*\$"
        if(value == "")
            return true
        value.replaceAll("\\W+","").matches(alphaNumeric)
    }
    
    /*
     * Nos dice si la empresa es válida
     */
    private boolean esEmpresa (String value){
        def company = "^[a-zA-Z]*\$"
        if(value == "")
            return true
        return value.replaceAll("\\s+","").matches(company)
    }
    
    /*
     * Nos dice si el es numérico y contiene 
     * solamente valores del 1 al 4.
     */    
    private boolean esSeguroSocial (String value){
        def insurance = "^[1,2,3,4]+\$"
        return value.matches(insurance)
    }
    
    /*
     * Nos dice si el valor es numerico y tiene la 
     * extension especificada.
     */    
    private boolean esNumSS (String value){
        return (value.matches(numeric) && value.length() == 11)
    }
    
    /*
     * Nos dice si el valor es numerico y tiene la 
     * extension especificada.
     */    
    private boolean esProducto (String value){
        return(value.matches(numeric) && value.length() == 3)
    }
    
    /*
     * Nos dice si el valor es numerico y tiene la 
     * extension especificada.
     */    
    private boolean esIdEnvio (String value){
        return (value.matches(numeric) && value.length() == 7)
    }
    
    /*
     * Nos dice si el valor es numerico y es un
     * plazo correcto.
     */    
    private boolean esPlazo(String value){
        def deadline = "^[6,9,12,18,24,30,36]+\$"
        return value.matches(deadline)
    }
    
    /*
     * Nos dice si el movimiento es correcto
     */    
    private boolean esMovimiento (String value){
        def mov = "^[A,R]+\$"
        return value.matches(mov)
    }
    
    /*
     * Nos dice si el valor es numerico, o nulo, 
     * y es un valor actual válido.
     */
    private boolean esActual (String value){
        def actual = "^[0,1,2,3,4,5]\$"
        if(value == "")
            return true
        return value.matches(actual)
    }
}
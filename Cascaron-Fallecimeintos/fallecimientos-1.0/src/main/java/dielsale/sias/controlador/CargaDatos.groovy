/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dielsale.sias.controlador

import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import java.nio.file.Path
import java.text.SimpleDateFormat
import org.primefaces.model.UploadedFile

/**
 *
 * @author Roberto Ponce y a-valderrama
 */
class CargaDatos {
    
    //Regex compartidas por algunos métodos
    private def numeric = "^[0-9*]+\$"
    private def alphaNumeric = "^[a-zA-Z0-9*]+\$"
    
    public boolean cargar(UploadedFile file, String ruta) {
        try {
            File d = new File(ruta)
            if(!d.exists()) {
                d.mkdirs()
                println "se crea"
            }
            String filename = file.fileName
            Path fileU = Paths.get(ruta+"/"+filename)
            InputStream input = file.inputstream
            Files.copy(input, fileU, StandardCopyOption.REPLACE_EXISTING)
            return true
        } catch(Exception e) {
            println e.class.name + ":" + e.message
        }
        return false
    }
    
    /**
     * Valida el formato de los datos del archivo con respecto
     * a la especificaión de requerimientos.
     *
     * @param file El archivo que contiene la 
     *            información que queremos validar
     */
    public String validaDatos(File file){
        try{
            FileReader filereader = new FileReader(file)
            //nos saltamos la primera línea porque es el header
            CSVReader csvReader = new CSVReaderBuilder(filereader) 
                                  .withSkipLines(1) 
                                  .build()
            def String[] next
            def int renglon = 1
            def String error = ""
            
            while((next = csvReader.readNext()) != null){
                if(!esAlfaNumerico(next[0]))
                    error += renglon.toString()+":1,\n"

                if(!esEndoso(next[1]))
                    error += renglon.toString()+":2,\n"

                if(!esId(next[2]))
                    error += renglon.toString()+":3,\n"

                if(!esNumerico(next[3]))
                    error += renglon.toString()+":4,\n"

                if(!esNumerico(next[4]))
                    error += renglon.toString()+":5,\n"

                if(!esTexto(next[5]))
                    error += renglon.toString()+":6,\n"

                if(!esTextoNull(next[6]))
                    error += renglon.toString()+":7,\n"

                if(!esTexto(next[7]))
                    error += renglon.toString()+":8,\n"

                if(!esTextoNull(next[8]))
                    error += renglon.toString()+":9,\n"

                if(!esFecha(next[9]))
                    error += renglon.toString()+":10,\n"

                if(!esSexo(next[10]))
                    error += renglon.toString()+":11,\n"

                if(!esRFC(next[11]))
                    error += renglon.toString()+":12,\n"

                if(!esFloat(next[12]))
                    error += renglon.toString()+":13,\n"

                if(!esEmpresa(next[13]))
                    error += renglon.toString()+":14,\n"

                if(!esTextoNull(next[14]))
                    error += renglon.toString()+":15,\n"

                if(!esNumTelefono(next[15]))
                    error += renglon.toString()+":16,\n"

                if(!esNumTelefono(next[16]))
                    error += renglon.toString()+":17,\n"

                if(!esSeguroSocial(next[17]))
                    error += renglon.toString()+":18,\n"

                if(!esNumSS(next[18]))
                    error += renglon.toString()+":19,\n"

                if(!esNumericoNull(next[19]))
                    error += renglon.toString()+":20,\n"

                if(!esProducto(next[20]))
                    error += renglon.toString()+":21,\n"

                if(!esSeguroSocial(next[21]))
                    error += renglon.toString()+":22,\n"

                if(!esIdEnvio(next[22]))
                    error += renglon.toString()+":23,\n"

                if(!esNumerico(next[23]))
                    error += renglon.toString()+":24,\n"

                if(!esFecha(next[24]))
                    error += renglon.toString()+":25,\n"

                if(!esNumerico(next[25]))
                    error += renglon.toString()+":26,\n"

                if(!esFloat (next[26]))
                    error += renglon.toString()+":27,\n"

                if(!esFecha(next[27]))
                    error += renglon.toString()+":28,\n"

                if(!esFecha(next[28]))
                    error += renglon.toString()+":29,\n"

                if(!esPlazo(next[29]))
                    error += renglon.toString()+":30,\n"

                if(!esMovimiento(next[30]))
                    error += renglon.toString()+":31,\n"

                if(!esFecha(next[31]))
                    error += renglon.toString()+":32,\n"

                if(!esFecha(next[32]))
                    error += renglon.toString()+":33,\n"

                if(!esNumericoNull(next[33]))
                    error += renglon.toString()+":34,\n"

                if(!esActual(next[34]))
                    error += renglon.toString()+":35,\n"
                
                renglon++
                
                //Para las pruebas. Quitarlo al terminarlas.
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
            return error[0..error.length()-3]
        }catch(Exception e){
            e.printStackTrace()
        }
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
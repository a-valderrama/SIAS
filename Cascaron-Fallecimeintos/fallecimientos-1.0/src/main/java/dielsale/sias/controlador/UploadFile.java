/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import dielsale.sias.modelo.UtilidadLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * En esta clase se administra todo lo que tiene 
 * que ver cuando un usuario sube un archivo. En
 * cualquiera de las vistas.
 * 
 * @author a-valderrama
 */
@ManagedBean
@SessionScoped
public class UploadFile {
    
    private UploadedFile file;
    private UtilidadLayout u =  new UtilidadLayout();
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    /**
     * Método básico proporcionado por primefaces
     */
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    /**
     * Método encargado de administrar la funcionalidad de 
     * subir los archivos de Layout.
     * El archivo que el usuario subirá será el layout de los
     * reclamos por fallecimiento, invalidez e incapacidad. Por
     * lo tanto será un archivo de tipo ".csv", el cual lo 
     * almacenaremos en el sistema de archivos local.
     * 
     * @param event Este evento se refiere a la subida del
     *              archivo, en la vista, por el usuario
     */
    public void handleFileUpload(FileUploadEvent event) {
        RequestContext rContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();
        //archivo actual
        file = event.getFile();
        //Mensaje de import exitoso
        String archivoExitoso = "Archivo: " + file.getFileName() + ", cargado exitosamente.";
        context.getExternalContext().getSessionMap().put("archivo", archivoExitoso);
        //generamos la dirección donde se guardará
        String rutaString = "/home/dielsale/Documentos/SIAS-Colab/Cascaron-Fallecimeintos/fallecimientos-1.0/src/resources/UploadedFiles/Fii";
        File directorio = new File(rutaString);
        //generamos el nombre
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        String filename = "layout_" + timeStamp +".csv";
        //Establecemos la ruta absoluta
        String rutaCompleta = rutaString+"/"+filename;
        try {
            if(! directorio.exists())
                directorio.mkdir();
            //Obtenemos el contenido del archivo subido
            InputStream contenido = file.getInputstream();
            File f = new File(directorio, filename);
            f.createNewFile();
            Path ruta = f.toPath();
            Files.copy(contenido, ruta, StandardCopyOption.REPLACE_EXISTING);
            /*Probando validación desde groovy*/
            CargaDatos carga = new CargaDatos();
            carga.validaDatos(f);
            /*Probando validación desde groovy*/
        }catch (IOException e) {
            rContext.execute("PF('io_error').show");
        }
        
        cargarDatos(rutaCompleta);
    }
    

    
    /**
     * Con este método estamos guardando el id del elemento
     * del layout, en el sessionMap, al cual se le adjuntará 
     * un archivo posteriormente
     * 
     * @param id id del elemento seleccionado
     * @return String cadena para la acción, posterior al 
     *         método, del botón. 
     */
    public String seleccionaSoporte(int id){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("id_layout", id);
        return "gestion-coberturas-fii_subir_soporte.xhmtl?faces-redirect=true";
    }
    
    /**
     * Método encargado de administrar la funcionalidad de 
     * subir los archivos de Layout.
     * El archivo que el usuario subirá será el soporte 
     * correspondiente a dicho elemento del layout.
     * Por lo tanto, el archivo será de tipo ".pdf", el 
     * cual lo almacenaremos en el sistema de archivos 
     * local.
     * 
     * @param event Este evento se refiere a la subida del
     *              archivo, en la vista, por el usuario.
     */
    public void soporte(FileUploadEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        file = event.getFile();
        String rutaString = "/home/dielsale/Documentos/SIAS/Cascaron-Fallecimeintos/fallecimientos-1.0/src/resources/UploadedFiles/Soporte";
        File directorio = new File(rutaString);
        //generamos el nombre
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String timeStamp = dateFormat.format(date);
        int id =  getIdElemento();
        String filename = id + "_" + "soporte_" + timeStamp + ".pdf" ;
        try {
            if(! directorio.exists())
                directorio.mkdir();
            //Obtenemos el contenido del archivo subido
            InputStream contenido = file.getInputstream();
            File f = new File(directorio, filename);
            boolean b = f.createNewFile();
            if(b){
                Path ruta = f.toPath();
                Files.copy(contenido, ruta, StandardCopyOption.REPLACE_EXISTING);
                context.execute("PF('exitoso').show()");
                u.setSoporte(id);
            }else{
                context.execute("PF('ya_existe').show()");
            }
        }catch (IOException e) {
            context.execute("PF('error').show()");
        }
    }
    
    /**
     * Regresa el id del elemento del layout en custión
     * 
     * @return el id del elemento del layout en custión
     */
    public String getNumRegistros(){
        FacesContext context = FacesContext.getCurrentInstance();
        String id = (String)context.getExternalContext().getSessionMap().get("numRegistros");
        return id;
    }
    
    /*
     * Este método extrae los datos del archivo subido por el 
     * usuario para posteriormente poder llenar la tabla 
     * correspondiente en la base de datos.
     */
    private void cargarDatos(String rutaCsv){
        String numRegistros;
        RequestContext rContext = RequestContext.getCurrentInstance();
        //Establecemos el nombre y ruta del shell script
        String rutaScript = "/home/dielsale/Documentos/SIAS-Colab/Cascaron-Fallecimeintos/fallecimientos-1.0/src/resources/ShellScripts";
        File directorio = new File(rutaScript);
        String filename = "cargaDatos.sh";
        String rutaCompleta = rutaScript+"/"+filename;
        //Creamos el script
        try{
            if(! directorio.exists())
                directorio.mkdir();
            
            File f = new File(directorio, filename);
            f.createNewFile();
            //Sobreescribimos el sript porque la ruta es dinamica
            FileWriter fw=new FileWriter(rutaCompleta);
            PrintWriter printWriter = new PrintWriter(fw);
            /*Para esto usamos el siguiente shell, pero se puede 
              usar el que se prefiera*/
            printWriter.print("#!/bin/bash\n");
            printWriter.print("dbname=\"SIAS\"\n");
            printWriter.print("psql $dbname << EOF\n");
            printWriter.printf("\\COPY layout(poliza,endoso,id_aseguradora,id_credito,id_trabajador,paterno,materno,nombre,"
                + "segundo_nombre,fecha_nacimiento,sexo,rfc_trabajador,importe,empresa,domicilio_ct,tel_principal,"
                + "tel_cel,tipo_seg_social,num_seguro_social,sucursal,producto,cobertura,id_envio,referencia_bancaria,"
                + "fecha_baja,suma_reclamospag,suma_aseguradoratot,fecha_inicio_recl,fecha_primervto,plazo,tipo_movimiento,"
                + "fecha_inicio,fecha_vto_final,max_r_pagadas,actuales) FROM %s DELIMITER ',' CSV HEADER;\n", rutaCsv);
            printWriter.print("EOF");
            printWriter.close();
        }catch (IOException e) {
            rContext.execute("PF('io_error').show()");
        }
        
        //Ejecutamos el script
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            /*le damos permiso de ejecucion al script. Solo necesitamos
              darle permiso de ejecución una vez, cuando se crea el
              archivo, pues después de eso sólo sobrescribimos.*/
            processBuilder.command("chmod", "+x", rutaCompleta);
            Process p = processBuilder.start();
            //ejecutamos el script
            processBuilder.command(rutaCompleta);
            Process process = processBuilder.start();
            //verificamos el output de la ejecución del comando
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            int exitVal = process.waitFor();
            if (exitVal == 0 && !(output.toString().equals(""))) {
                //numero de registros cargados exitosamente
                numRegistros = output.toString().substring(5);
                numRegistros = "Se han cargado " + numRegistros + " registros exitosamente.";
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().getSessionMap().put("numRegistros", numRegistros);
                rContext.execute("PF('exitoso').show()");
            }else{
                rContext.execute("PF('formato_incorrecto').show()");
            }
        } catch (IOException e) {
		e.printStackTrace();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
    }
    
    /*
     * Regresa el id del elemento del layout en custión
     */
    private int getIdElemento(){
        FacesContext context = FacesContext.getCurrentInstance();
        int id = (int)context.getExternalContext().getSessionMap().get("id_layout");
        return id;
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dielsale.sias.controlador;

import dielsale.sias.modelo.UtilidadAtencion
import dielsale.sias.modelo.UtilidadDesempleo
import dielsale.sias.modelo.UtilidadFallecimiento
import dielsale.sias.modelo.UtilidadIntegrado
import dielsale.sias.controlador.UploadFile
import dielsale.sias.modelo.Atencion
import dielsale.sias.modelo.Desempleo
import dielsale.sias.modelo.Fallecimiento
import dielsale.sias.modelo.Fallecimiento
import dielsale.sias.modelo.Integrado
import java.util.HashSet
import java.util.Set
import javax.faces.bean.ManagedBean
import javax.faces.bean.SessionScoped

/**
 * Esta clase se encarga de hacer la 
 * integración de todos los documentos,
 * máximo 3, que se encuentren en la base
 * de datos con el mismo idEnvío
 *
 * @author a-valderrama
 */
@ManagedBean
@SessionScoped
class Integrar {
    
    private def int idAtencion;
    private def int idFallecimiento;
    private def int idDesempleo;
    private def uAtencion = new UtilidadAtencion();
    private def uFallecimiento = new UtilidadFallecimiento();
    private def uDesempleo = new UtilidadDesempleo();
    private def uIntegrado = new UtilidadIntegrado();
    private def hashDesempleados = [:]
    private def hashFallecimientos = [:]
    private def hashAtenciones = [:]

    public int getIdAtencion() {
        return idAtencion;
    }

    public void setIdAtencion(int idAtencion) {
        this.idAtencion = idAtencion;
    }

    public int getIdFallecimiento() {
        return idFallecimiento;
    }

    public void setIdFallecimiento(int idFallecimiento) {
        this.idFallecimiento = idFallecimiento;
    }

    public int getIdDesempleo() {
        return idDesempleo;
    }

    public void setIdDesempleo(int idDesempleo) {
        this.idDesempleo = idDesempleo;
    }
    
    /**
     * Regresa todos los envíos existentes en la
     * base de datos para los archivos de atención
     * a cliente.
     * 
     * @return Todos los enviois en la base de datos
     */
    public Set<Integer> getAtencionEnvios(){
        def Set<Integer> envios = new HashSet<Integer>(uAtencion.getIdEnvios());
        return envios;
    }
    
    /**
     * Regresa todos los envíos existentes en la
     * base de datos para los archivos de 
     * desempleo.
     * 
     * @return Todos los enviois en la base de datos
     */
    public Set<Integer> getDesempleoEnvios(){
        def Set<Integer> envios = new HashSet<Integer>(uDesempleo.getIdEnvios());
        return envios;
    }
    
    /**
     * Regresa todos los envíos existentes en la
     * base de datos para los archivos de 
     * fallecimiento.
     * 
     * @return Todos los enviois en la base de datos
     */
    public Set<Integer> getFallecimientoEnvios(){
        def Set<Integer> envios = new HashSet<Integer>(uFallecimiento.getIdEnvios());
        return envios;
    }
    
    /**
     * Integra todos los elementos en la base de
     * datos (de las tres tablas) con el idEnvio
     * seleccionado por el usuario
     */
    public void integrar(){
        def mensaje = new MensajeIntegrado()
        def repetidos = []
        //Todos los id son iguales y validos
        if(idDesempleo == idAtencion && idAtencion == idFallecimiento && idDesempleo != 0){
            repetidos = integrarTodos(idDesempleo)
            if(repetidos.isEmpty())
                mensaje.noRep()
            else
                mensaje.rep(repetidos)
            return 
        }
        //Ningún id es valido
        if(idDesempleo == idAtencion && idAtencion == idFallecimiento && idDesempleo == 0){
            mensaje.ningunoValido()
            return 
        }
        //Dos son validos, integramos esos dos.
        if(idDesempleo == idAtencion && idAtencion != 0 && idFallecimiento == 0){
            repetidos = integraDesempleoAtencion(idDesempleo)
            if(repetidos.isEmpty())
                mensaje.noRep()
            else
                mensaje.rep(repetidos)
            return 
        }
        if(idFallecimiento == idAtencion && idAtencion != 0 && idDesempleo == 0){
            repetidos = integraFallecimientoAtencion(idFallecimiento)
            if(repetidos.isEmpty())
                mensaje.noRep()
            else
                mensaje.rep(repetidos)
            return 
        }
        if(idFallecimiento == idDesempleo && idDesempleo != 0 && idAtencion == 0){
            repetidos = integraDesempleoFallecimiento(idFallecimiento)
            if(repetidos.isEmpty())
                mensaje.noRep()
            else
                mensaje.rep(repetidos)
            return  
        }
        //Solo uno es válido.
        if(idFallecimiento == 0 && idDesempleo == 0){
            integraAtencion(idAtencion)
            mensaje.noRep()
            return 
        }
        if(idAtencion == 0 && idDesempleo == 0){
            integraFallecimiento(idFallecimiento)
            mensaje.noRep()
            return 
        }
        if(idAtencion == 0 && idFallecimiento == 0){
            integraDesempleo(idDesempleo)
            mensaje.noRep()
            return 
        }
        //Si se escogen id's distintos
        else{
            mensaje.noMatch()
        }
    }
    
    /*
     * Intrega los 3 archivos bajo un id.
     * Regresa la lista de las repeticiones
     */
    private ArrayList<Repetidos> integrarTodos(int idEnvio){
        def desempleados = uDesempleo.getRegistros(idEnvio)
        def fallecimientos = uFallecimiento.getRegistros(idEnvio)
        def atenciones = uAtencion.getRegistros(idEnvio)
        def repetidos = []
        def key, elemento
        desempleados.each{ d->
            elemento = new Repetidos()
            uIntegrado.save(desempleoAIntegrado(d))
            key = d.getId_credito().toString() + d.getId_trabajador().toString()
            elemento.setIdCredito(d.getId_credito())
            elemento.setIdTrabajador(d.getId_trabajador())
            hashDesempleados.put(key,elemento)
        }
        fallecimientos.each{ f->
            elemento = new Repetidos()
            uIntegrado.save(fallecimientoAIntegrado(f))
            key = f.getId_credito().toString() + f.getId_trabajador().toString()
            elemento.setIdCredito(f.getId_credito())
            elemento.setIdTrabajador(f.getId_trabajador())
            hashFallecimientos.put(key,elemento)
        }
        atenciones.each{ a->
            elemento = new Repetidos()
            uIntegrado.save(atencionAIntegrado(a))
            key = a.getId_credito().toString() + a.getId_trabajador().toString()
            elemento.setIdCredito(a.getId_credito())
            elemento.setIdTrabajador(a.getId_trabajador())
            hashAtenciones.put(key,elemento)
        }
        //Aquí llevaremos un registro de los datos ya vistos
        def eExistentes = [:]
        repetidos = checkDesempleados(repetidos)
        repetidos = checkFallecimientos(repetidos)
        repetidos = checkAtenciones(repetidos)
        //una verificamos los repetidos limpiamos los maps
        hashDesempleados.clear()
        hashFallecimientos.clear()
        hashAtenciones.clear()
        return repetidos
    }
    
    /*
     * Intrega los archivos de desempleo y  
     * atencion bajo un id.
     * Regresa la lista de las repeticiones
     */
    private ArrayList<Repetidos> integraDesempleoAtencion(int idEnvio){
        def desempleados = uDesempleo.getRegistros(idEnvio)
        def atenciones = uAtencion.getRegistros(idEnvio)
        def repetidos = []
        def key, elemento
        
        desempleados.each{ d->
            elemento = new Repetidos()
            uIntegrado.save(desempleoAIntegrado(d))
            key = d.getId_credito().toString() + d.getId_trabajador().toString()
            elemento.setIdCredito(d.getId_credito())
            elemento.setIdTrabajador(d.getId_trabajador())
            hashDesempleados.put(key,elemento)
        }
        atenciones.each{ a->
            elemento = new Repetidos()
            uIntegrado.save(atencionAIntegrado(a))
            key = a.getId_credito().toString() + a.getId_trabajador().toString()
            elemento.setIdCredito(a.getId_credito())
            elemento.setIdTrabajador(a.getId_trabajador())
            hashAtenciones.put(key,elemento)
        }
        //Aquí llevaremos un registro de los datos ya vistos
        def eExistentes = [:]
        repetidos = checkDesempleados(repetidos)
        repetidos = checkAtenciones(repetidos)
        hashDesempleados.clear()
        hashAtenciones.clear()
        return repetidos
    }
    
    /*
     * Intrega los archivos de atencion y  
     * fallecimiento bajo un id.
     * Regresa la lista de las repeticiones
     */
    private ArrayList<Repetidos> integraFallecimientoAtencion(int idEnvio){
        def fallecimientos = uFallecimiento.getRegistros(idEnvio)
        def atenciones = uAtencion.getRegistros(idEnvio)
        def repetidos = []
        def key, elemento
        
        fallecimientos.each{ f->
            elemento = new Repetidos()
            uIntegrado.save(fallecimientoAIntegrado(f))
            key = f.getId_credito().toString() + f.getId_trabajador().toString()
            elemento.setIdCredito(f.getId_credito())
            elemento.setIdTrabajador(f.getId_trabajador())
            hashFallecimientos.put(key,elemento)
        }
        atenciones.each{ a->
            elemento = new Repetidos()
            uIntegrado.save(atencionAIntegrado(a))
            key = a.getId_credito().toString() + a.getId_trabajador().toString()
            elemento.setIdCredito(a.getId_credito())
            elemento.setIdTrabajador(a.getId_trabajador())
            hashAtenciones.put(key,elemento)
        }
        //Aquí llevaremos un registro de los datos ya vistos
        def eExistentes = [:]
        repetidos = checkFallecimientos(repetidos)
        repetidos = checkAtenciones(repetidos)
        hashFallecimientos.clear()
        hashAtenciones.clear()
        return repetidos
    }
    
    /*
     * Intrega los archivos de desempleo y  
     * fallecimiento bajo un id.
     * Regresa la lista de las repeticiones
     */
    private ArrayList<Repetidos> integraDesempleoFallecimiento(int idEnvio){
        def desempleados = uDesempleo.getRegistros(idEnvio)
        def fallecimientos = uFallecimiento.getRegistros(idEnvio)
        def repetidos = []
        def key, elemento
        desempleados.each{ d->
            elemento = new Repetidos()
            uIntegrado.save(desempleoAIntegrado(d))
            key = d.getId_credito().toString() + d.getId_trabajador().toString()
            elemento.setIdCredito(d.getId_credito())
            elemento.setIdTrabajador(d.getId_trabajador())
            hashDesempleados.put(key,elemento)
        }
        fallecimientos.each{ f->
            elemento = new Repetidos()
            uIntegrado.save(fallecimientoAIntegrado(f))
            key = f.getId_credito().toString() + f.getId_trabajador().toString()
            elemento.setIdCredito(f.getId_credito())
            elemento.setIdTrabajador(f.getId_trabajador())
            hashFallecimientos.put(key,elemento)
        }
        //Aquí llevaremos un registro de los datos ya vistos
        def eExistentes = [:]
        repetidos = checkDesempleados(repetidos)
        repetidos = checkFallecimientos(repetidos)
        //una verificamos los repetidos limpiamos los maps
        hashDesempleados.clear()
        hashFallecimientos.clear()
        return repetidos
    }
    
    /*
     * Convierto un objeto de tipo desempleo a 
     * uno de tipo integrado.
     */
    private Integrado desempleoAIntegrado(Desempleo d){
        def integrado = new Integrado()
        integrado.setPoliza(d.getPoliza())
        integrado.setEndoso(d.getEndoso())
        integrado.setId_aseguradora(d.getId_aseguradora())
        integrado.setId_credito(d.getId_credito())
        integrado.setId_trabajador(d.getId_trabajador())
        integrado.setPaterno(d.getPaterno())
        integrado.setMaterno(d.getMaterno())
        integrado.setNombre(d.getNombre())
        integrado.setSegundo_nombre(d.getSegundo_nombre())
        integrado.setFecha_nacimiento(d.getFecha_nacimiento())
        integrado.setSexo(d.getSexo())
        integrado.setRfc_trabajador(d.getRfc_trabajador())
        integrado.setImporte(d.getImporte())
        integrado.setEmpresa(d.getEmpresa())
        integrado.setDomicilio_ct(d.getDomicilio_ct())
        integrado.setTel_principal(d.getTel_principal())
        integrado.setTel_cel(d.getTel_cel())
        integrado.setTipo_seg_social(d.getTipo_seg_social())
        integrado.setNum_seguro_social(d.getNum_seguro_social())
        integrado.setSucursal(d.getSucursal())
        integrado.setProducto(d.getProducto())
        integrado.setCobertura(d.getCobertura())
        integrado.setId_envio(d.getId_envio())
        integrado.setReferencia_bancaria(d.getReferencia_bancaria())
        integrado.setFecha_baja(d.getFecha_baja())
        integrado.setSuma_reclamospag(d.getSuma_reclamospag())
        integrado.setSuma_aseguradoratot(d.getSuma_aseguradoratot())
        integrado.setFecha_inicio_recl(d.getFecha_inicio_recl())
        integrado.setFecha_primervto(d.getFecha_primervto())
        integrado.setPlazo(d.getPlazo())
        integrado.setTipo_movimiento(d.getTipo_movimiento())
        integrado.setFecha_inicio(d.getFecha_inicio())
        integrado.setFecha_vto_final(d.getFecha_vto_final())
        integrado.setMax_r_pagadas(d.getMax_r_pagadas())   
        integrado.setActuales(d.getActuales())
        return integrado
    }
    
    /*
     * Convierto un objeto de tipo fallecimiento 
     * a uno de tipo integrado.
     */
    private Integrado fallecimientoAIntegrado(Fallecimiento f){
        def integrado = new Integrado()
        integrado.setPoliza(f.getPoliza())
        integrado.setEndoso(f.getEndoso())
        integrado.setId_aseguradora(f.getId_aseguradora())
        integrado.setId_credito(f.getId_credito())
        integrado.setId_trabajador(f.getId_trabajador())
        integrado.setPaterno(f.getPaterno())
        integrado.setMaterno(f.getMaterno())
        integrado.setNombre(f.getNombre())
        integrado.setSegundo_nombre(f.getSegundo_nombre())
        integrado.setFecha_nacimiento(f.getFecha_nacimiento())
        integrado.setSexo(f.getSexo())
        integrado.setRfc_trabajador(f.getRfc_trabajador())
        integrado.setImporte(f.getImporte())
        integrado.setEmpresa(f.getEmpresa())
        integrado.setDomicilio_ct(f.getDomicilio_ct())
        integrado.setTel_principal(f.getTel_principal())
        integrado.setTel_cel(f.getTel_cel())
        integrado.setTipo_seg_social(f.getTipo_seg_social())
        integrado.setNum_seguro_social(f.getNum_seguro_social())
        integrado.setSucursal(f.getSucursal())
        integrado.setProducto(f.getProducto())
        integrado.setCobertura(f.getCobertura())
        integrado.setId_envio(f.getId_envio())
        integrado.setReferencia_bancaria(f.getReferencia_bancaria())
        integrado.setFecha_baja(f.getFecha_baja())
        integrado.setSuma_reclamospag(f.getSuma_reclamospag())
        integrado.setSuma_aseguradoratot(f.getSuma_aseguradoratot())
        integrado.setFecha_inicio_recl(f.getFecha_inicio_recl())
        integrado.setFecha_primervto(f.getFecha_primervto())
        integrado.setPlazo(f.getPlazo())
        integrado.setTipo_movimiento(f.getTipo_movimiento())
        integrado.setFecha_inicio(f.getFecha_inicio())
        integrado.setFecha_vto_final(f.getFecha_vto_final())
        integrado.setMax_r_pagadas(f.getMax_r_pagadas())   
        integrado.setActuales(f.getActuales())
        return integrado
    }
    
    /*
     * Convierto un objeto de tipo atencion 
     * a uno de tipo integrado.
     */
    private Integrado atencionAIntegrado(Atencion a){
        def integrado = new Integrado()
        integrado.setPoliza(a.getPoliza())
        integrado.setEndoso(a.getEndoso())
        integrado.setId_aseguradora(a.getId_aseguradora())
        integrado.setId_credito(a.getId_credito())
        integrado.setId_trabajador(a.getId_trabajador())
        integrado.setPaterno(a.getPaterno())
        integrado.setMaterno(a.getMaterno())
        integrado.setNombre(a.getNombre())
        integrado.setSegundo_nombre(a.getSegundo_nombre())
        integrado.setFecha_nacimiento(a.getFecha_nacimiento())
        integrado.setSexo(a.getSexo())
        integrado.setRfc_trabajador(a.getRfc_trabajador())
        integrado.setImporte(a.getImporte())
        integrado.setEmpresa(a.getEmpresa())
        integrado.setDomicilio_ct(a.getDomicilio_ct())
        integrado.setTel_principal(a.getTel_principal())
        integrado.setTel_cel(a.getTel_cel())
        integrado.setTipo_seg_social(a.getTipo_seg_social())
        integrado.setNum_seguro_social(a.getNum_seguro_social())
        integrado.setSucursal(a.getSucursal())
        integrado.setProducto(a.getProducto())
        integrado.setCobertura(a.getCobertura())
        integrado.setId_envio(a.getId_envio())
        integrado.setReferencia_bancaria(a.getReferencia_bancaria())
        integrado.setFecha_baja(a.getFecha_baja())
        integrado.setSuma_reclamospag(a.getSuma_reclamospag())
        integrado.setSuma_aseguradoratot(a.getSuma_aseguradoratot())
        integrado.setFecha_inicio_recl(a.getFecha_inicio_recl())
        integrado.setFecha_primervto(a.getFecha_primervto())
        integrado.setPlazo(a.getPlazo())
        integrado.setTipo_movimiento(a.getTipo_movimiento())
        integrado.setFecha_inicio(a.getFecha_inicio())
        integrado.setFecha_vto_final(a.getFecha_vto_final())
        integrado.setMax_r_pagadas(a.getMax_r_pagadas())   
        integrado.setActuales(a.getActuales())
        return integrado
    }
    
    /*
     * Agrega a la lista de repetidos los repetidos 
     * existentes en el archivo de Desempleo
     */
    private ArrayList<Repetidos> checkDesempleados(ArrayList<Repetidos> repetidos){
        hashDesempleados.each{ key, value ->
            if(hashAtenciones.containsKey(key) && hashFallecimientos.containsKey(key)){
                value.setFuente("Desempleados, Atención a Clientes, Fallecimientos")
                repetidos.add(value)
                //borramos el elemeto repetido para solo reportar 1 vez
                borraDeAtencionFalle(key)
                return 
            }
            if(hashAtenciones.containsKey(key)){
                value.setFuente("Desempleados, Atención a Clientes")
                repetidos.add(value)
                hashAtenciones.remove(key)
                return
            }
            if(hashFallecimientos.containsKey(key)){
                value.setFuente("Desempleados, Fallecimientos")
                repetidos.add(value)
                hashFallecimientos.remove(key)
                return
            }
        }
        return repetidos
    }
    
    /*
     * Agrega a la lista de repetidos los repetidos 
     * existentes en el archivo de Desempleo
     */
    private ArrayList<Repetidos> checkFallecimientos(ArrayList<Repetidos> repetidos){
        hashFallecimientos.each{ key, value ->
            if(hashAtenciones.containsKey(key) && hashDesempleados.containsKey(key)){
                value.setFuente("Desempleados, Atención a Clientes, Fallecimientos")
                repetidos.add(value)
                borraDesempleoAten(key)
                return
            }
            if(hashAtenciones.containsKey(key)){
                value.setFuente("Fallecimientos, Atención a Clientes")
                repetidos.add(value)
                hashAtenciones.remove(key)
                return
            }
            if(hashDesempleados.containsKey(key)){
                value.setFuente("Fallecimientos, Desempleado")
                repetidos.add(value)
                hashDesempleados.remove(key)
                return
            }
        }
        return repetidos
    }
    
    /*
     * Agrega a la lista de repetidos los repetidos 
     * existentes en el archivo de Desempleo
     */
    private ArrayList<Repetidos> checkAtenciones(ArrayList<Repetidos> repetidos){
        hashAtenciones.each{ key, value ->
            if(hashDesempleados.containsKey(key) && hashFallecimientos.containsKey(key)){
                value.setFuente("Desempleados, Atención a Clientes, Fallecimientos")
                repetidos.add(value)
                borraDesempleoFalle(key)
                return
            }
            if(hashDesempleados.containsKey(key)){
                value.setFuente("Atención a Clientes, Desempleados")
                repetidos.add(value)
                hashDesempleados.remove(key)
                return
            }
            if(hashFallecimientos.containsKey(key)){
                value.setFuente("Atención a Clientes,Fallecimientos")
                repetidos.add(value)
                hashFallecimientos.remove(key)
                return
            }
        }
        return repetidos
    }
    
    
    private void integraDesempleo (int idDesempleo){
        def desempleados = uDesempleo.getRegistros(idDesempleo)
        desempleados.each{ d->
            uIntegrado.save(desempleoAIntegrado(d))
        }
    }
    
    
    private void integraFallecimiento (int idFallecimiento){
        def fallecimientos = uFallecimiento.getRegistros(idFallecimiento)
        fallecimientos.each{ f->
            uIntegrado.save(fallecimientoAIntegrado(f))
        }
    }
    
    
    private void integraAtencion (int idAtencion){
        def atenciones = uAtencion.getRegistros(idAtencion)
        atenciones.each{ a->
            uIntegrado.save(atencionAIntegrado(a))
        }
    }
    
    /*
     * Borra el elemento con dicha llave de 
     * los maps de fallecimiento y atencion a
     * cliente.
     */
    private void borraDeAtencionFalle(String key){
        hashAtenciones.remove(key)
        hashFallecimientos.remove(key)
    }
    
    /*
     * Borra el elemento con dicha llave de 
     * los maps de desempleo y atencion a 
     * clientes.
     */
    private void borraDesempleoAten(String key){
        hashDesempleados.remove(key)
        hashAtenciones.remove(key)
    }
    
    /*
     * Borra el elemento con dicha llave de 
     * los maps de desempleo y fallecimientos.
     */
    private void borraDesempleoFalle(String key){
        hashDesempleados.remove(key)
        hashFallecimientos.remove(key)
    }
}

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
 * atributos del layout desde la vista. 
 * Pero además definimos las columnas de la base
 * de datos que se va a leer. Así como el nombre 
 * de la tabla.
 * 
 * @author a-valderrama
 */
@Entity
@Table(catalog = "SIAS", schema = "public", name = "layout")
public class Layout {
    
    @Id
    @Column(name = "id")
    private int id; 
    
    @Column(name = "poliza")
    private String poliza ;
    
    @Column(name = "endoso")
    private int endoso;
    
    @Column(name = "id_aseguradora")
    private int id_aseguradora;
    
    @Column(name = "id_credito")
    private int id_credito;
    
    @Column(name = "id_trabajador")
    private int id_trabajador;
    
    @Column(name = "paterno")
    private String paterno;
    
    @Column(name = "materno")
    private String materno;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "segundo_nombre")
    private String segundo_nombre;
    
    @Column(name = "fecha_nacimiento")
    private String fecha_nacimiento;
    
    @Column(name = "sexo")
    private char sexo;
    
    @Column(name = "rfc_trabajador")
    private String rfc_trabajador;
    
    @Column(name = "importe")
    private float importe;
    
    @Column(name = "empresa")
    private String empresa;
    
    @Column(name = "domicilio_ct")
    private String domicilio_ct;
    
    @Column(name = "tel_principal")
    private String tel_principal;
    
    @Column(name = "tel_cel")
    private String tel_cel;
    
    @Column(name = "tipo_seg_social")
    private int tipo_seg_social;
    
    @Column(name = "num_seguro_social")
    private String num_seguro_social;
    
    @Column(name = "sucursal")
    private int sucursal;
    
    @Column(name = "producto")
    private int producto;
    
    @Column(name = "cobertura")
    private int cobertura;
    
    @Column(name = "id_envio")
    private int id_envio;
    
    @Column(name = "referencia_bancaria")
    private String referencia_bancaria;
    
    @Column(name = "fecha_baja")
    private String fecha_baja;
    
    @Column(name = "soporte")
    private boolean soporte;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public int getEndoso() {
        return endoso;
    }

    public void setEndoso(int endoso) {
        this.endoso = endoso;
    }

    public int getId_aseguradora() {
        return id_aseguradora;
    }

    public void setId_aseguradora(int id_aseguradora) {
        this.id_aseguradora = id_aseguradora;
    }

    public int getId_credito() {
        return id_credito;
    }

    public void setId_credito(int id_credito) {
        this.id_credito = id_credito;
    }

    public int getId_trabajador() {
        return id_trabajador;
    }

    public void setId_trabajador(int id_trabajador) {
        this.id_trabajador = id_trabajador;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSegundo_nombre() {
        return segundo_nombre;
    }

    public void setSegundo_nombre(String segundo_nombre) {
        this.segundo_nombre = segundo_nombre;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getRfc_trabajador() {
        return rfc_trabajador;
    }

    public void setRfc_trabajador(String rfc_trabajador) {
        this.rfc_trabajador = rfc_trabajador;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDomicilio_ct() {
        return domicilio_ct;
    }

    public void setDomicilio_ct(String domicilio_ct) {
        this.domicilio_ct = domicilio_ct;
    }

    public String getTel_principal() {
        return tel_principal;
    }

    public void setTel_principal(String tel_principal) {
        this.tel_principal = tel_principal;
    }

    public String getTel_cel() {
        return tel_cel;
    }

    public void setTel_cel(String tel_cel) {
        this.tel_cel = tel_cel;
    }

    public int getTipo_seg_social() {
        return tipo_seg_social;
    }

    public void setTipo_seg_social(int tipo_seg_social) {
        this.tipo_seg_social = tipo_seg_social;
    }

    public String getNum_seguro_social() {
        return num_seguro_social;
    }

    public void setNum_seguro_social(String num_seguro_social) {
        this.num_seguro_social = num_seguro_social;
    }

    public int getSucursal() {
        return sucursal;
    }

    public void setSucursal(int sucursal) {
        this.sucursal = sucursal;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getCobertura() {
        return cobertura;
    }

    public void setCobertura(int cobertura) {
        this.cobertura = cobertura;
    }

    public int getId_envio() {
        return id_envio;
    }

    public void setId_envio(int id_envio) {
        this.id_envio = id_envio;
    }

    public String getReferencia_bancaria() {
        return referencia_bancaria;
    }

    public void setReferencia_bancaria(String referencia_bancaria) {
        this.referencia_bancaria = referencia_bancaria;
    }

    public String getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(String fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public boolean getSoporte() {
        return soporte;
    }

    public void setSoporte(boolean soporte) {
        this.soporte = soporte;
    }
    
}

package com.example.demo.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pedido {
	
	/**
	 * CREACION DE VARIABLE ESTATICA QUE AUMENTARA LA REFERENCIA CADA VEZ QUE CREEMOS UN PEDIDO
	 */
	private static int CODIGOINCREMENTADOR=0; //AUTOMATICO
	
	/**
	 * VARIABLES DEL PEDIDO, ENTRE ELLAS LA FECHA SE CREARA AUTOMATICA CON EL DIA ACTUAL, Y LA REFERENCIA QUE NO SE PODRA MODIFICAR.
	 */
	private int referencia;
	private String direccion;
	private LocalDate fecha = LocalDate.now(); 
	private String email;
	private String telefono;
	/**
	 * MAPA DONDE GUARDAREMOS LOS PRODUCTOS Y LAS CANTIDADES QUE QUEREMOS DE CADA UNO
	 */
	Map<Producto, Integer> productos = new HashMap<>(); //MASHMAP KEY (PRODUCTO) - VALUE (CANTIDAD)
	

	/**
	 * CONSTRUCTORES VACIOS Y CON EL MAPA PARA PODER CONTROLAR LA CREACIÓN, EL INCREMENTADOR SIEMPRE DEBE ESTAR PARA QUE SE 
	 * INCREMENTE LA REFERENCIA
	 */
	public Pedido() {
		this.referencia = CODIGOINCREMENTADOR++;
	}
	
	public Pedido(String direccion,  String email, String telefono) {
		this.direccion = direccion;
		this.email = email;
		this.telefono=telefono;
	}
	

	public Pedido( String direccion, String email, String telefono, Map<Producto, Integer> cantidades) {
		super();
		this.referencia = CODIGOINCREMENTADOR++;
		this.direccion = direccion;
		this.fecha = fecha;
		this.email = email;
		this.productos = cantidades; //SE PONE AQUI EL HASMAP?
		this.telefono=telefono;
	}

	
	/**
	 * SETTERS Y GETTERS DE LAS VARIABLES
	 * @return
	 */

	public static int getCODIGOINCREMENTADOR() {
		return CODIGOINCREMENTADOR;
	}

	public static void setCODIGOINCREMENTADOR(int cODIGOINCREMENTADOR) {
		CODIGOINCREMENTADOR = cODIGOINCREMENTADOR;
	}

	public int getReferencia() {
		return referencia;
	}

	public void setReferencia(int referencia) {
		this.referencia = referencia;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<Producto, Integer> getProductos() {
		return productos;
	}

	public void setProductos(Map<Producto, Integer> productos) {
		this.productos = productos;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * HASHCODE Y EQUALS POR SI NECESITAMOS COMPARAR LAS REFERENCIAS QUE ES EL IDENTIFICADOR DE CADA PEDIDO
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + referencia;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (referencia != other.referencia)
			return false;
		return true;
	}
	
	
/**
 * TO STRING PARA IMPRIMIR LA LISTA DE PEDIDOS EN EL CASO DE QUE LO  NECESITEMOS
 */
	@Override
	public String toString() {
		return "Pedido [referencia=" + referencia + ", direccion=" + direccion + ", fecha=" + fecha + ", email=" + email
				+ ", telefono=" + telefono + ", productos=" + productos + "]";
	}

	
	
	
}

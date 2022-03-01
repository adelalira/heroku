package com.example.demo.model;

public class Producto {

	/**
	 * VARIABLES DEL PRODUCTO
	 */
	private long id;
	private String nombre;
	private String descripcion;
	private double precio;
	
	/**
	 * CONSTRUCTOR VACIO Y OTRO CON TODOS LOS ATRIBUTOS DE LOS PRODUCTOS
	 */
	public Producto() {
	}
	
	public Producto(long id, String nombre, String descripcion, double precio) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
	}
	
	
	/**
	 * GETTERS Y SETTER PARA MANIPULAR LAS VARIABLES
	 * @return
	 */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public double getPrecio() {
		return precio;
	}
	
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
	/**
	 * HASHCODE Y EQUALS DE LA ID DEL PRODUCTO PARA PODERLO COMPARAR
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Producto other = (Producto) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 * TO STRING PARA  IMPRIMIR LOS PRODUCTOS
	 */
	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ "]";
	}
	
	
}

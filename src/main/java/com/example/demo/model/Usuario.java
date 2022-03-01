package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

	/**
	 * VARIABLE ESTATICA INCREMENTADORA DE LA ID
	 */
	private static int CODIGOINCREMENTADOR=1; //AUTOMATICO
	
	/**
	 * VARIABLES DEL USUARIO
	 */
	private long id;
	private String nombre;
	private String apellidos;
	private String dni;
	private String email;
	private String telefono;
	private String direccion;
	private String nickname;
	private String contrasena;
	/**
	 * ARRAYLIST DONDE GUARDAREMOS TODOS LOS PEDIDOS QUE SE HARAN
	 */
	private List<Pedido> pedidos = new ArrayList<Pedido>();
	
	
	/**
	 * CONSTRUCTOR VARIO Y CON TODAS LAS VARIABLES
	 */
	public Usuario() {
		this.id = CODIGOINCREMENTADOR++;
	}
	
	public Usuario(long id, String nombre, String apellidos, String dni, String email, String telefono,
			String direccion, String nickname, String contrasena) {
		this.id = CODIGOINCREMENTADOR++;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.nickname = nickname;
		this.contrasena = contrasena;
	}

	
	public Usuario(long id, String nombre, String apellidos, String dni, String email, String telefono,
			String direccion, String nickname, String contrasena, List<Pedido> listaPedidos) {
		this.id = CODIGOINCREMENTADOR++;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.email = email;
		this.telefono = telefono;
		this.direccion = direccion;
		this.nickname = nickname;
		this.contrasena = contrasena;
		this.pedidos = listaPedidos; //HACERLO UN HASHMAP
	}


	/**
	 * GETTER Y SETTERS
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



	public String getApellidos() {
		return apellidos;
	}



	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedido) {
		this.pedidos = pedido;
	}


	public static int getCODIGOINCREMENTADOR() {
		return CODIGOINCREMENTADOR;
	}



/**
 * HASHCODE Y EQUALS DE LA ID PARA COMPARAR
 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
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
		Usuario other = (Usuario) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (id != other.id)
			return false;
		return true;
	}


/**
 * TO STRING PARA IMPRIMIR LOS DATOS DEL USUARIO SI FUERA PRECISO.
 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", dni=" + dni + ", email="
				+ email + ", telefono=" + telefono + ", direccion=" + direccion + ", nickname=" + nickname
				+ ", contrasena=" + contrasena + ", pedido=" + pedidos + "]";
	}
	
	
	
}

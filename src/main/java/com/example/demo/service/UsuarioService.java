package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;


@Service
public class UsuarioService {

	/**
	 * INYECTAMOS EL SERVICIO DEL PEDIDO PARA MANIPULARLO
	 */
	@Autowired
	private PedidoService servicioPedido;

	/**
	 * CREAMOS EL ARRAYLIST DE USUARIOS PARA ALMACENARLOS
	 */
	private List<Usuario> listaUsuarios = new ArrayList<>();
	
	/**
	 * METODO PARA AÑADIR USUARIOS
	 * @param e
	 * @return
	 */
	public Usuario add(Usuario e) {
		listaUsuarios.add(e);
		return e;
	}
	
	/**
	 * METODO PARA IMPRIMIR LA LISTA DE USUARIOS
	 * @return
	 */
	public List<Usuario> findAll() {
		return listaUsuarios;
	}
	
	
	/**
	 * METODO QUE SE EJECUTA DESPUES DEL CONSTRUCTOR Y CREA EL USUARIO, EN ESTE CASO SOLO TENDREMOS UNO QUE USAREMOS SIEMPRE
	 */
	@PostConstruct
	public void init() {
		listaUsuarios.addAll(
				Arrays.asList(new Usuario(1, "Adela", "Lira", "48123651S", "aalira.96@gmail.com", "722524571", 
						"C/Guadalquivir nº4 1B", "adelalira", "adelalira"))
				);
		
	}
	
	
	
	/**
	 * METODO PARA COMPROBAR QUE EL USUARIO Y CONTRASEÑA INTRODUCIDOS NO SON ERRONEOS.
	 * @param usuarioComprobacion
	 * @return
	 */
	public Usuario comprobarLogin(Usuario usuarioComprobacion) { //@Valid
		boolean existe = false;
		Usuario usuarioReturn = null;
		
		int i = 0;
		while (!existe && i<listaUsuarios.size()) {
			if(listaUsuarios.get(i).getNickname().equals(usuarioComprobacion.getNickname()) 
					&& listaUsuarios.get(i).getContrasena().equals(usuarioComprobacion.getContrasena())) {
				usuarioReturn = listaUsuarios.get(i);
			}
				i++;
		}
		return usuarioReturn;	
	}


	
	
	
	/*
	 * COLECCION USUARIOS
	 * GET MY NICKNAME
	 * LOGIN
	 * ADD PEDIDO (PEDIDO)
	 * GET ALL PEDIDOS
	 * GET PEDIDO BY REFERENCIA
	 * REMOVE PEDIDO
	 */
	
}

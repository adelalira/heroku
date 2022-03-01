package com.example.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Pedido;
import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;



@Service
public class PedidoService {

	/**
	 * CREAMOS VARIABLES EN LAS QUE GUARDAREMOS DATOS PARA MANIPULARLOS CON MAYOR FACILIDAD
	 */
	private double precioTotal;
	private double precioTotalIva;
	private int referenciaCogida;
	
	/**
	 * INYECTAMOS LA SESION Y EL SERVICIO DEL PRODUCTO
	 */
	@Autowired
	private ProductoService servicioProducto;
	
	@Autowired
	private HttpSession sesion;
	
	/**
	 * CREAMOS EL ARRAYLIST DONDE GUARDAREMOS LOS PEDIDOS, Y UN MAPA DONDE GUARDAREMOS LOS PRODUCTOS
	 */
	private List<Pedido> listaPedido = new ArrayList<Pedido>();
	Map <Producto, Integer> CompletoProductos = new HashMap <Producto, Integer>();
	
	/**
	 * METODO PARA AÑADIR PEDIDOS A LA LISTA
	 * @param e
	 * @return
	 */
	public boolean addPedido(Pedido e) {
		return listaPedido.add(e);
	}
	
	/**
	 * METODO QUE SE EJECUTARA DESPUES DEL CONSTRUCTOR,ENESTE CASO AÑADIRA PEDIDOS A LA LISTA, PERO PUEDEN INTRODUCIRSE MAS DE UNO
	 */
	@PostConstruct
	public void init() {
		listaPedido.addAll(Arrays.asList(
				//new Pedido("C/Guadalquivir nº4 1B", "aalira.96@gmail.com","722524571")
				));
		
	}

	/**
	 * METODO QUE NOS IMPRIME LA LISTA DE PEDIDOS
	 * @return
	 */
	public List<Pedido> getListaProductos() {
		return listaPedido;
	}
	

/**
 * ESTE METODO RECORRE LAS CANTIDADES QUE HEMOS INTRODUCIDO EN EL FORMULARIO Y GUARDA LOS DATOS QUE NECESITAMOS EN UN MAPA NUEVO.
 * @param datosUsuario
 */
	public void newPedido(Integer[] datosUsuario) {
		

		Map <Producto, Integer> pedidos = new HashMap <Producto, Integer>();
		List <Producto> listaProductos = this.servicioProducto.findAll();
		
		
		for (int i = 0; i < datosUsuario.length; i++) {
			if (listaProductos.get(i)!=null & datosUsuario[i]!=null) {
				precioTotal+=datosUsuario[i]*listaProductos.get(i).getPrecio();
				
			}
			pedidos.put(listaProductos.get(i), datosUsuario[i]);
			
		}
		precioTotalIva=precioTotal*1.21;
		this.CompletoProductos=pedidos;
		
		
	}
/**
 * METODO PARA INTRODUCIR LOS PRODUCTOS AL PEDIDO Y EL PEDIDO AL USUARIO
 * @param direccion
 * @param email
 * @param telefono
 * @return
 */
	public Pedido crearPedidoFinal(String direccion, String email, String telefono) {
		Pedido pedido = new Pedido(direccion, email, telefono, CompletoProductos);
		Usuario usuario = (Usuario) sesion.getAttribute("user");
		usuario.getPedidos().add(pedido);
		listaPedido.add(pedido);
		return pedido;
	}
	
/**
 * METODO PARA RECUPERAR EL ULTIMO PEDIDO
 * @return
 */
	public Pedido ultimoPedido() {
		System.out.println( listaPedido.get(listaPedido.size()-1));
		return listaPedido.get(listaPedido.size()-1);
		
	}
	
	/**
	 * METODO PARA ENCONTRAR LA ID
	 * @param referencia
	 * @return
	 */
	public Pedido findById(int referencia) {
		Pedido result = null;
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i<listaPedido.size()) {
			if (listaPedido.get(i).getReferencia() == referencia) {
				encontrado = true;
				referenciaCogida=referencia;
				result = listaPedido.get(i);
			} else {
				i++;
			}
		}
		
		return result;
	}
	
	/**
	 * METODO PARA REEMPLAZAR LOS DATOS DEL PEDIDO CUANDO HEMOS SELECCIONADO EDITAR
	 * @param e
	 */
	public void edit(Pedido e) {
		boolean encontrado = false;
		int i = 0;
		Usuario usuario = (Usuario) sesion.getAttribute("user");
		List <Pedido> listaPedidosUsuario = usuario.getPedidos();
		//referenciaCogida+=1;
		while (!encontrado && i < listaPedidosUsuario.size()) {
			if (listaPedidosUsuario.get(i).getReferencia() == referenciaCogida) {
				encontrado = true;
				e.setFecha(listaPedidosUsuario.get(i).getFecha());
				e.setReferencia(listaPedidosUsuario.get(i).getReferencia());
				usuario.getPedidos().remove(i);
				usuario.getPedidos().add(e);
				sesion.removeAttribute("user");
				sesion.setAttribute("user", usuario);
			} else {
				i++;	
			}
		}
		
	}

	/**
	 * GETTER Y SETTERS
	 * @return
	 */
	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public double getPrecioTotalIva() {
		return precioTotalIva;
	}

	public void setPrecioTotalIva(double precioTotalIva) {
		this.precioTotalIva = precioTotalIva;
	}

	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void edit(String direccion, String email, String telefono) {
		
		Pedido pedido = new Pedido(direccion, email, telefono, CompletoProductos);
		Usuario usuario = (Usuario) sesion.getAttribute("user");
		
		boolean encontrado = false;
		int i = 0;
		List <Pedido> listaPedidosUsuario = usuario.getPedidos();
		//referenciaCogida+=1;
		while (!encontrado && i < listaPedidosUsuario.size()) {
			if (listaPedidosUsuario.get(i).getReferencia() == referenciaCogida) {
				encontrado = true;
				pedido.setFecha(listaPedidosUsuario.get(i).getFecha());
				pedido.setReferencia(listaPedidosUsuario.get(i).getReferencia());
				usuario.getPedidos().remove(i);
				usuario.getPedidos().add(pedido);
				sesion.removeAttribute("user");
				sesion.setAttribute("user", usuario);
			} else {
				i++;	
			}
		}
		
		
	}
	
	
	
	
	/*
	 * COLECCION DE Pedido
	 * GET BY REFERENCIA
	 * GET ALL
	 * ADD PRODUCT
	 * REMOVE PRODUCTO
	 */
	
	
}

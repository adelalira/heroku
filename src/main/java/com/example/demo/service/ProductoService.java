package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.Producto;


@Service
public class ProductoService {

/**
 * LISTA DE PRODUCTOS QUE TENEMOS DISPONIBLES Y MAPA PARA GUARDAR LAS CANTIDADES QUE SELECCIONAMOS
 */
	private List<Producto> listaProductos = new ArrayList<>();
	Map <Producto, Integer> listaCantidades = new HashMap <Producto, Integer>();
	
	/**
	 * METODO PARA AÑADIR PRODUCTOS A LA LISTA
	 * @param e
	 * @return
	 */
	public Producto add(Producto e) {
		listaProductos.add(e);
		return e;
	}
	
	/**
	 * METODO QUE  IMPRIME LA LISTA DE PRODUCTOS
	 * @return
	 */
	public List<Producto> findAll() {
		return listaProductos;
	}
	
	/**
	 * METODO QUE IMPRIME LAS CANTIDADES DE PRODUCTO SELECCIONADAS
	 * @return
	 */
	public Map<Producto, Integer> getListaCantidades() {
		return listaCantidades;
	}

/**
 * METODO QUE SE EJECUTA DESPUES DEL CONSTRUCTOR EN EL CUAL ESTA ALMACENADOS TODOS LOS PRODUCTOS DISPONIBLES	
 */
	@PostConstruct
	public void init() {
		listaProductos.addAll(
				Arrays.asList(new Producto(1, "Reloj", "Reloj de piel de cocodrilo con manecillas de diamantes", 25.99),
						new Producto(2, "Colgante", "Colgante con piedra de rubi con cobertura de oro blanco", 35.99),
						new Producto(3, "Pendientes", "Pendiente de oro blanco con piedras asiaticas", 18.99),
						new Producto(4, "Anillo","Anillo de plata de la cruz de caravaca", 9.99),
						new Producto(5, "Tobillera", "Tobillera de plata y estrellas, ideal para verano", 8.99),
						new Producto(6, "Gemelos", "Gemelos de esmeraldas y plata", 24.99))
				);
		
	}
	
	
	/**
	 * METODO QUE CREA UN HASHMAP Y RECORRE LA LISTA DE PRODUCTOS, INTRODUCIENDO EN OTRO MAPA LOS PRODUCTOS QUE HAN SIDO SELECCIONADOS
	 * CON SUS CANTIDADES.
	 * @param datosPedido
	 */
	public void newProducto(Integer[] datosPedido) {
		
		Map <Producto, Integer> productos = new HashMap <Producto, Integer>();
		
		for (int i = 0; i < datosPedido.length; i++) {
			if(listaProductos.get(i)!=null & datosPedido[i]!=null) {
				productos.put(listaProductos.get(i), datosPedido[i]);
			}
			
		}
		
		this.listaCantidades=productos;
		
	}
	
	/*
	 * COLECCION PRODUCTOS
	 * GET MY ID
	 * GET ALL PRODUCTOS
	 */

}

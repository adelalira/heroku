package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.UsuarioService;

@Controller
public class UsuarioController {

	/**
	 * INYECTAMOS LOS SERVICIOS Y LA SESION
	 */
	
	@Autowired
	private UsuarioService servicioUsuario;
	
	@Autowired
	private PedidoService  servicioPedido;
	
	@Autowired 
	private ProductoService servicioProducto;
	
	@Autowired
	private HttpSession sesion;
	
	
	/**
	 * CREAMOS UNA LISTA DE USUARIOS QUE BUSCA LOS USUARIOS EN UNA LISTA QUE TENEMOS EN EL SERVICIO
	 * @param model
	 * @return
	 */
	
	@GetMapping({"/list"})
	public String listado(Model model) {
		model.addAttribute("listaUsuarios", servicioUsuario.findAll());
		return "list";
	}
	
	/**
	 * GET QUE NOS ENVIA AL LOGIN DEL USUARIO
	 * @param model
	 * @return
	 */
	
	@GetMapping({"/","/login"})
	public String loginUsuario (Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	

	/**
	 * ESTE METODO POST RECIBE LOS DATOS DEL USUARIO QUE INTRODUCIMOS EN EL LOGIN Y LOS COMPRUEBA EN EL METODO COMPROBARLOGIN() QUE
	 * SE UBICA EN EL USUARIO SERVICE, AL CUAL LE PASAREMOS EL USUARIO RECOGIDO Y NOS COMPROBARA SI EL USUARIO Y CONTRASEÑA SE ENCUENTRA 
	 * EN LOS USUARIOS ALMACENADOS. SI EL USUARIO ES CORRECTO NOS LLEVARA A PEDIDOS. SI EL USUARIO NO FUERA CORRECTO NOS DEVOLVERIA. 
	 * ADEMAS INTRODUCE EL USUARIO EN LA SESION.
	 * AL LOGIN.
	 * @param model
	 * @param usuarioComprobacion
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/login/submit")
	public String nuevoUsuarioSubmit(Model model, @Valid @ModelAttribute("usuario") Usuario usuarioComprobacion,
			BindingResult bindingResult) {
		Usuario usuarioComprobado= servicioUsuario.comprobarLogin(usuarioComprobacion);
		if (usuarioComprobado!=null) { // && bindingResult.hasErrors()
			sesion.setAttribute("user", usuarioComprobado);
			return "redirect:/pedidos"; //PAGINA SIGUIENTE
		} else {
			model.addAttribute("usuario", usuarioComprobado);
			return "redirect:/login";
		}
	}
	
	/**
	 * ESTE METODO NOS LLEVARA A UNA PESTAÑA DONDE NOS DEJARA SELECCIONAR SI QUEREMOS LISTAR LOS PEDIDOS O SI QUEREMOS
	 * REALIZAR UNN NUEVO PEDIDO.
	 * @param model
	 * @return
	 */
	
	@GetMapping({"/pedidos"})
	public String pedidos (Model model) {
		model.addAttribute("pedido", new Pedido());
		return "pedidos";
	}
	
	
	/**
	 * SI SELECCIONAMOS LISTAR LOS PEDIDOS RECORRERA LA LISTA DE PEDIDOS QUE TIENE EL USUARIO Y NOS  LO IMPRIMIRA. ESTE METODO TAMBIEN 
	 * ENVIAAL THYMELEAF LA LISTA DE PEDIDOS  DEL USUARIO, ADEMAS DEL USUARIO.
	 * @param model
	 * @return
	 */
	@GetMapping({"/pedidos/listaPedidos"})
	public String listadoPedidos(Model model) {
		//List<Pedido> listaPedido = servicioPedido.getListaProductos();
		model.addAttribute("listaPedidos", ((Usuario) sesion.getAttribute("user")).getPedidos());
		model.addAttribute("user", sesion.getAttribute("user"));
		return "listaPedidos";
	}
	
	
	/**
	 * ESTE METODO NOS LLEVARA A LA CREACION DE UN NUEVO PEDIDO. 
	 * @param model
	 * @return
	 */
	@GetMapping({"/pedidos/newPedido"})
	public String pedidosNuevos (Model model) {
		return "newPedido";
	}
	

	/**
	 * ESTE METODO MUESTRA LA LISTA DE PRODUCTO QUE TENEMOS DISPONIBLE EN EL SERVICIO PRODUCTO.
	 * @param model
	 * @return
	 */
	@GetMapping({"/pedidos/productos"})
	public String listadoProductos (Model model) {
		model.addAttribute("listaProductos", servicioProducto.findAll());
		return "listaProductos";
	}
	
	
/**
 * ESTE METODO POST RECIBE LOS DATOS DE LOS PRODUCTOS SELECCIONADOS Y NOS ENVIA AL GET DEL FORMULARIO.
 * @param model
 * @param datosProducto
 * @return
 */
	@PostMapping("/pedido/submit")
	public String nuevoPedidoSubmit(Model model, @RequestParam(name="cantidad") Integer[] datosProducto) {
		this.servicioPedido.newPedido(datosProducto);
		this.servicioProducto.newProducto(datosProducto);
		model.addAttribute("listaProductosCompleta", this.servicioProducto.findAll());
		sesion.setAttribute("cantidadesProductos", this.servicioProducto.getListaCantidades());
		return "redirect:/pedidos/newPedido/formulario";
		
	}
	

	/**
	 * ESTE GET NOS REDIRIGE AL FORMULARIO, ENVIANDO LAS CANTIDADES DE LOS PRODUCTOS QUE HEMOS INTRODUCIDO ANTERIORMENTE EN LA SESION.
	 * @param model
	 * @return
	 */
	@GetMapping({"/pedidos/newPedido/formulario"})
	public String resumenPedido (Model model) {
		model.addAttribute("datosCantidades", sesion.getAttribute("cantidadesProductos"));
		return "newPedido";
	}
	
	/**
	 * EN EL METODO POST RECOGEMOS LOS DATOS DE LA DIRECCION, EMAIL Y TELEFONO DEL FORMULARIO Y LLAMAMOS AL METODO CREARPEDIDOFINAL
	 * DEL SERVICIO PEDIDO, QUE SERA EL ENCARGADO DE INTRODUCIR LOS PRODUCTOS EN EL PEDIDO, Y EL PEDIDO AL USUARIO. ADEMAS NOS REDIRIGE
	 * AL RESUMEN.
	 * @param model
	 * @param direccion
	 * @param email
	 * @param telefono
	 * @return
	 */
	@PostMapping("/pedidos/newPedido/formulario/submit")
	public String recibirFormulario(Model model, 
			@RequestParam(required=true,value="direccion") String direccion,
            @RequestParam(required=false,value="email") String email,
            @RequestParam(required=false,value="telefono") String telefono) {
		if(direccion=="" || email=="" | telefono=="") {
			return "redirect:/pedidos/newPedido/formulario";
		}
		else {
			this.servicioPedido.crearPedidoFinal(direccion, email, telefono);
			return "redirect:/pedidos/newPedido/resumen";
		}
		
		
	}
	

	/**
	 * ESTTE METODO GET ENVIA TODA LA INFORMACION QUE NECESITA AL RESUMEN PARA IMPRIMIR DATOS DEL USUARIO, EL PEDIDO, LOS PRODUCTOS
	 * Y EL PRECIO FINAL.
	 * @param model
	 * @return
	 */
	@GetMapping("/pedidos/newPedido/resumen")
	public String imprimeResumen (Model model) {
		model.addAttribute("user", sesion.getAttribute("user"));
		model.addAttribute("ultimoPedido", this.servicioPedido.ultimoPedido());
		model.addAttribute("datosCantidades", sesion.getAttribute("cantidadesProductos"));
		model.addAttribute("precioTotal", servicioPedido.getPrecioTotal());
		model.addAttribute("precioTotalIva", this.servicioPedido.getPrecioTotalIva());
		return "resumen";	
	}
	
	
	/**
	 * ESTE METODO GET SERA LLAMADO CUANDO PRESIONEMOS EL BOTON EDITAR. CUANDO ESTO SUCEDA BUSCARA LA REFERENCIA
	 * DEL PEDIDO QUE HAYAMOS SELECCIONADO Y NOS REDIRIGIRA A LA EDICION DEL PEDIDO.
	 * @param referencia
	 * @param model
	 * @return
	 */
	@GetMapping("/pedido/edit/{referencia}")
	public String editarEmpleadoForm(@PathVariable int referencia, Model model) {
		Pedido pedido = servicioPedido.findById(referencia);
		if (pedido != null) {
			model.addAttribute("pedido", pedido);
			model.addAttribute("datosProductos", servicioProducto.findAll());
			return "listaProductosEditar";
		} else {
			return "redirect:/pedidos/newPedido";
		}
	}
	
	
	@PostMapping("/pedido/edit/edit/submit")
	public String nuevoEditPedidoSubida(Model model, @RequestParam(name="cantidad") Integer[] datosProducto) {
		this.servicioPedido.newPedido(datosProducto);
		this.servicioProducto.newProducto(datosProducto);
		model.addAttribute("listaProductosCompleta", this.servicioProducto.findAll());
		sesion.setAttribute("cantidadesProductos", this.servicioProducto.getListaCantidades());
		return "redirect:/pedidos/edit/newPedido/formulario";
		
	}
		

	/**
	 * ESTE GET NOS REDIRIGE AL FORMULARIO, ENVIANDO LAS CANTIDADES DE LOS PRODUCTOS QUE HEMOS INTRODUCIDO ANTERIORMENTE EN LA SESION.
	 * @param model
	 * @return
	 */
	@GetMapping({"/pedidos/edit/newPedido/formulario"})
	public String resumenEditPedido (Model model) {
		model.addAttribute("datosCantidades", sesion.getAttribute("cantidadesProductos"));
		return "modificaPedido";
	}
	
	
	/**
	 * EN EL METODO POST RECOGEMOS LOS DATOS DE LA DIRECCION, EMAIL Y TELEFONO DEL FORMULARIO Y LLAMAMOS AL METODO CREARPEDIDOFINAL
	 * DEL SERVICIO PEDIDO, QUE SERA EL ENCARGADO DE INTRODUCIR LOS PRODUCTOS EN EL PEDIDO, Y EL PEDIDO AL USUARIO. ADEMAS NOS REDIRIGE
	 * AL RESUMEN.
	 * @param model
	 * @param direccion
	 * @param email
	 * @param telefono
	 * @return
	 */
	@PostMapping("/pedidos/edit/newPedido/formulario/submit")
	public String recibirEditFormulario(Model model, 
			@RequestParam(required=true,value="direccion") String direccion,
            @RequestParam(required=false,value="email") String email,
            @RequestParam(required=false,value="telefono") String telefono) {
		if(direccion=="" || email=="" | telefono=="") {
			return "redirect:/pedidos/edit/newPedido/formulario";
		}
		else {
			this.servicioPedido.edit(direccion, email, telefono);
			return "redirect:/pedidos/edit/newPedido/resumen";
		}
		
		
	}
	
	
	
	@GetMapping("/pedidos/edit/newPedido/resumen")
	public String imprimeEditResumen (Model model) {
		model.addAttribute("user", sesion.getAttribute("user"));
		model.addAttribute("ultimoPedido", this.servicioPedido.ultimoPedido());
		model.addAttribute("datosCantidades", sesion.getAttribute("cantidadesProductos"));
		model.addAttribute("precioTotal", servicioPedido.getPrecioTotal());
		model.addAttribute("precioTotalIva", this.servicioPedido.getPrecioTotalIva());
		return "resumenEdit";	
	}
	
		


	
	/**
	 * TENDREMOS LA OPCION DE INVALIDAR LA SESION UNA VEZ HAYAMOS TERMINADO, DANDOLE AL BOTÓN DE CERRAR SESION.
	 * @return
	 */
	@GetMapping({"/pedidos/invalidar"})
	public String invalidarSesion () { 
		sesion.invalidate();
		return "redirect:/login";
	}
}
	

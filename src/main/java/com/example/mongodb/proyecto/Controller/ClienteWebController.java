package com.example.mongodb.proyecto.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.mongodb.proyecto.Repository.ClienteRepository;
import com.example.mongodb.proyecto.Repository.OpinionRepository;
import com.example.mongodb.proyecto.Repository.PedidosRepository;
import com.example.mongodb.proyecto.Repository.ProductoRepository;
import com.example.mongodb.proyecto.Repository.ReservasRepository;
import com.example.mongodb.proyecto.entity.Cliente;
import com.example.mongodb.proyecto.entity.Empleado;
import com.example.mongodb.proyecto.entity.Opinion;
import com.example.mongodb.proyecto.entity.Pedidos;
import com.example.mongodb.proyecto.entity.Producto;
import com.example.mongodb.proyecto.entity.Reservas;
import com.example.mongodb.proyecto.exception.NotFoundException;

@Controller
@RequestMapping(value = "clientes")
public class ClienteWebController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservasRepository reservasRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private PedidosRepository pedidosRepository;
    
    @Autowired
    private OpinionRepository opinionRepository;
    

    // Métodos para clientes
    @GetMapping("/clientes")
    public String getAllClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        return "clientes/list"; // Retorna la vista clientes/list.html
    }
    
    @GetMapping("/new")
    public String newCliente(Model model) {
        model.addAttribute("cliente", new Cliente()); // Crear un nuevo objeto cliente vacío
        return "clientes/form"; // Devuelve la vista del formulario para agregar un nuevo cliente
    }

    @GetMapping("/{id}")
    public String getClienteById(@PathVariable String id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/detalle"; // Retorna la vista clientes/detalle.html
    }

    @PostMapping("/save")
    public String saveCliente(@ModelAttribute Cliente cliente, Model model) {
        // Si el cliente no tiene id, MongoDB generará uno automáticamente.
        if (cliente.getId() == null || cliente.getId().isEmpty()) {
            clienteRepository.save(cliente);  // Guarda el cliente sin el id explícito
        } else {
            clienteRepository.save(cliente);  // Si el id ya está presente, actualiza el cliente
        }
        return "redirect:/clientes/clientes"; // Redirige a la lista de clientes
    }

    @GetMapping("/edit/{id}")
    public String editCliente(@PathVariable String id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        model.addAttribute("cliente", cliente);
        return "clientes/form"; // Devuelve la vista del formulario para editar el cliente
    }

    @GetMapping("/delete/{id}")
    public String deleteCliente(@PathVariable String id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes"; // Redirige a la lista de clientes después de eliminar
    }
    // Métodos para reservas
    @GetMapping("/reservas")
    public String getAllReservas(Model model) {
        List<Reservas> reservas = reservasRepository.findAll();
        model.addAttribute("reservas", reservas);
        return "reservas/list"; // Retorna la vista reservas/list.html
    }

    @GetMapping("/reservas/{id}")
    public String getReservasById(@PathVariable String id, Model model) {
        Reservas reserva = reservasRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        model.addAttribute("reserva", reserva);
        return "reservas/detalle"; // Retorna la vista reservas/detalle.html
    }

    @PostMapping("/reservas/save")
    public String saveReservas(@ModelAttribute Reservas reserva, Model model) {
        if (reserva.getId() == null || reserva.getId().isEmpty()) {
            // Si no tiene id, MongoDB genera uno automáticamente
            reservasRepository.save(reserva);
        } else {
            // Si tiene id (es una edición), se actualiza
            reservasRepository.save(reserva);
        }
        return "redirect:/clientes/reservas"; // Redirige a la lista de reservas
    }

    @GetMapping("/reservas/edit/{id}")
    public String editReservas(@PathVariable String id, Model model) {
        Reservas reserva = reservasRepository.findById(id).orElseThrow(() -> new NotFoundException("Reserva no encontrada"));
        model.addAttribute("reserva", reserva);
        return "reservas/form"; // Devuelve la vista del formulario para editar la reserva
    }

    @GetMapping("/reservas/delete/{id}")
    public String deleteReservas(@PathVariable String id) {
        reservasRepository.deleteById(id);
        return "redirect:/clientes/reservas"; // Redirige a la lista de reservas después de eliminar
    }

    @GetMapping("/reservas/new")
    public String newReserva(Model model) {
        model.addAttribute("reserva", new Reservas()); // Crear una nueva reserva vacía
        return "reservas/form"; // Devuelve la vista del formulario para agregar una nueva reserva
    }
    
 
 // Métodos para Producto
    @GetMapping("/productos")
    public String getAllProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "productos/list"; // Retorna la vista productos/list.html
    }

    @GetMapping("/productos/{id}")
    public String getProductoById(@PathVariable String id, Model model) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        model.addAttribute("producto", producto);
        return "productos/detalle"; // Retorna la vista productos/detalle.html
    }
    
    
 // Métodos para pedidos
    @GetMapping("/pedidos")
    public String getAllPedidos(Model model) {
        List<Pedidos> pedidos = pedidosRepository.findAll();
        List<Pedidos> pedidos1 = new ArrayList<>();
        for (Pedidos pedido : pedidos) {
            Cliente cliente = clienteRepository.findById(pedido.getClienteId()).orElse(null);
            Producto producto = productoRepository.findById(pedido.getProductoId()).orElse(null);
            Pedidos nuevoPedido = new Pedidos();
            nuevoPedido.setId(pedido.getId());
            nuevoPedido.setFechapedido(pedido.getFechapedido());
            nuevoPedido.setObservaciones(pedido.getObservaciones());
            nuevoPedido.setClienteNombre(cliente != null ? cliente.getNombre() : "Desconocido");
            nuevoPedido.setProductoNombre(producto != null ? producto.getNombre() : "Desconocido");
            pedidos1.add(nuevoPedido);
        }
        model.addAttribute("pedidos", pedidos1);
        return "pedidos/list";
    }

    @GetMapping("/pedidos/new")
    public String newPedidosForm(Model model) {
        List<Cliente> clientes = clienteRepository.findAll(); // Obtener todos los clientes
        List<Producto> productos = productoRepository.findAll(); // Obtener todos los productos
        model.addAttribute("clientes", clientes); // Agregar clientes al modelo
        model.addAttribute("productos", productos); // Agregar productos al modelo
        model.addAttribute("pedido", new Pedidos()); // Crear un nuevo pedido vacío
        return "pedidos/form"; // Retorna la vista formulario de pedidos
    }


    @GetMapping("/pedidos/{id}")
    public String getPedidosById(@PathVariable String id, Model model) {
        Pedidos pedido = pedidosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado"));
        
        // Obtiene cliente y producto relacionados
        Cliente cliente = clienteRepository.findById(pedido.getClienteId()).orElse(null);
        Producto producto = productoRepository.findById(pedido.getProductoId()).orElse(null);
        
        // Asigna los nombres
        pedido.setClienteNombre(cliente != null ? cliente.getNombre() : "Cliente desconocido");
        pedido.setProductoNombre(producto != null ? producto.getNombre() : "Producto desconocido");
        
        model.addAttribute("pedido", pedido);
        return "pedidos/detalle"; // Retorna la vista pedidos/detalle.html
    }

    @PostMapping("/pedidos/save")
    public String savePedidos(@ModelAttribute Pedidos pedido) {
        // Guardar el pedido con las relaciones de cliente y producto
        pedidosRepository.save(pedido);
        return "redirect:/clientes/pedidos"; // Redirige a la lista de pedidos
    }


    @GetMapping("/pedidos/editar/{id}")
    public String editPedidos(@PathVariable String id, Model model) {
        Pedidos pedido = pedidosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado"));
        
        // Obtener todos los clientes y productos para la lista desplegable
        List<Cliente> clientes = clienteRepository.findAll();
        List<Producto> productos = productoRepository.findAll();
        
        // Agregar los datos al modelo
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clientes); // Agregar clientes al modelo
        model.addAttribute("productos", productos); // Agregar productos al modelo
        
        return "pedidos/form"; // Retorna la vista del formulario de pedidos
    }

    @PostMapping("/pedidos/delete/{id}")
    public String deletePedidos(@PathVariable String id) {
        Pedidos pedido = pedidosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado"));
        pedidosRepository.delete(pedido);
        return "redirect:/clientes/pedidos";
    }
    
 // Formulario para escribir una nueva opinión
    @GetMapping("/opiniones/new")
    public String newOpinionForm(Model model) {
        model.addAttribute("opinion", new Opinion()); // Crear una nueva opinión vacía
        List<Cliente> clientes = clienteRepository.findAll(); // Obtener lista de clientes
        model.addAttribute("clientes", clientes);
        return "opiniones/form"; // Vista del formulario
    }
    
 // Guardar la opinión
    @PostMapping("/opiniones/save")
    public String saveOpinion(@ModelAttribute Opinion opinion, Model model) {
        opinionRepository.save(opinion); // Guardar la opinión en la base de datos
        List<Opinion> opiniones = opinionRepository.findAll(); // Obtener todas las opiniones
        model.addAttribute("opiniones", opiniones);
        return "opiniones/list"; // Redirigir a la vista de lista de opiniones
    }

    
 // Mostrar lista de opiniones
    @GetMapping("/opiniones")
    public String listOpinions(Model model) {
        List<Opinion> opiniones = opinionRepository.findAll(); // Obtener todas las opiniones
        model.addAttribute("opiniones", opiniones);
        return "opiniones/list"; // Vista de lista de opiniones
    }
}

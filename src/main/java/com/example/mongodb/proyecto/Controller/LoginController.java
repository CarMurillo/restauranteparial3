package com.example.mongodb.proyecto.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mongodb.proyecto.entity.Administrador;
import com.example.mongodb.proyecto.entity.Empleado;
import com.example.mongodb.proyecto.entity.Cliente;
import com.example.mongodb.proyecto.Repository.AdministradorRepository;
import com.example.mongodb.proyecto.Repository.EmpleadoRepository;
import com.example.mongodb.proyecto.Repository.ClienteRepository;

@Controller
public class LoginController {

    @Autowired
    private AdministradorRepository administradorRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/")
    public String landingPage() {
        return "logins/Rol"; 
    }

    @GetMapping("/login/administrador")
    public String administradorLogin() {
        return "logins/Login-administrador"; 
    }

    @PostMapping("/login/administrador")
    public String administradorAuth(@RequestParam("user") String user, @RequestParam("password") String password, Model model) {
        Administrador administrador = administradorRepository.findByUser(user);

        if (administrador != null && administrador.getPassword().equals(password)) {
            // Redirige al dashboard o página principal del administrador
            return "redirect:/administrador/paginaprincipal"; 
        }

        model.addAttribute("error", "Usuario o contraseña incorrecta");
        return "logins/Login-administrador";
    }

    // Agregar un controlador para la página principal del administrador
    @GetMapping("/administrador/paginaprincipal")
    public String administradorPage() {
        return "administrador/paginaprincipal";  // Asegúrate de que este archivo esté en la ruta correcta
    }

    @GetMapping("/login/empleado")
    public String empleadoLogin() {
        return "logins/Login-empleado"; 
    }

    @PostMapping("/login/empleado")
    public String empleadoAuth(@RequestParam("user") String user, @RequestParam("password") String password, Model model) {
        System.out.println("Usuario: " + user + ", Contraseña: " + password);  // Imprimir para depurar
        Empleado empleado = empleadoRepository.findByUser(user);

        if (empleado != null && empleado.getPassword().equals(password)) {
            System.out.println("Empleado autenticado: " + empleado.getNombre());  // Verificar si el empleado es encontrado
            return "redirect:/empleados/paginaprincipal";
        }

        model.addAttribute("error", "Usuario o contraseña incorrecta");
        return "logins/Login-empleado";
    }

    
    @GetMapping("/empleados/paginaprincipal")
    public String empleadoPage() {
        return "empleados/paginaprincipal";  // Verifica que esta página exista en la ruta correcta
    }



    @GetMapping("/login/cliente")
    public String clienteLogin() {
        return "logins/Login-cliente"; 
    }

    @PostMapping("/login/cliente")
    public String clienteAuth(@RequestParam("user") String user, @RequestParam("password") String password, Model model) {
        Cliente cliente = clienteRepository.findByUser(user);

        if (cliente != null && cliente.getPassword().equals(password)) {
            return "redirect:/clientes/paginaprincipal"; 
        }

        model.addAttribute("error", "Usuario o contraseña incorrecta");
        return "logins/Login-cliente";
    }
    
    @GetMapping("/clientes/paginaprincipal")
    public String clientePage() {
        return "clientes/paginaprincipal";  // Verifica que esta página exista en la ruta correcta
    }
}



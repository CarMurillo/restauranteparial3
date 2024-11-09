package com.example.mongodb.proyecto.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongodb.proyecto.Repository.ClienteRepository;
import com.example.mongodb.proyecto.Repository.ReservasRepository;
import com.example.mongodb.proyecto.entity.Cliente;
import com.example.mongodb.proyecto.entity.Reservas;
import com.example.mongodb.proyecto.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/api/cliente")
public class ClienteRestController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ReservasRepository reservasRepository;

    // Métodos para cliente
    @GetMapping("/cliente/")
    public List<Cliente> getAllCliente() {
        return clienteRepository.findAll();
    }

    @GetMapping("/cliente/{id}")
    public Cliente getClienteById(@PathVariable String id) {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    }

    @PostMapping("/cliente/")
    public Cliente saveCliente(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Cliente empleado = mapper.convertValue(body, Cliente.class);
        return clienteRepository.save(empleado);
    }

    @PutMapping("/empleados/{id}")
    public Cliente updateEmpleado(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Cliente empleado = mapper.convertValue(body, Cliente.class);
        empleado.setId(id);
        return clienteRepository.save(empleado);
    }

    @DeleteMapping("/cliente/{id}")
    public Cliente deleteCliente(@PathVariable String id) {
    	Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    	clienteRepository.deleteById(id);
        return cliente;
    }

 // Métodos para reservas
    @GetMapping("/reservas/")
    public List<Reservas> getAllReservas() {
        return reservasRepository.findAll();
    }

    @GetMapping("/reservas/{id}")
    public Reservas getReservasById(@PathVariable String id) {
        return reservasRepository.findById(id).orElseThrow(() -> new NotFoundException("Reservas no encontrado"));
    }

    @PostMapping("/reservas/")
    public Reservas saveReservas(@RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Reservas reservas = mapper.convertValue(body, Reservas.class);
        return reservasRepository.save(reservas);
    }

    @PutMapping("/reservas/{id}")
    public Reservas updateReservas(@PathVariable String id, @RequestBody Map<String, Object> body) {
        ObjectMapper mapper = new ObjectMapper();
        Reservas reservas = mapper.convertValue(body, Reservas.class);
        reservas.setId(id);
        return reservasRepository.save(reservas);
    }

    @DeleteMapping("/reservas/{id}")
    public Reservas deleteReservas(@PathVariable String id) {
    	Reservas reservas = reservasRepository.findById(id).orElseThrow(() -> new NotFoundException("reservas no encontrado"));
    	reservasRepository.deleteById(id);
        return reservas;
    }
}
package co.edu.uptc.sistemasdistribuidos.ventas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *Clase Index Controller
 *  @author Juliana Rincon
 *  @author Luisa Merchan
 *  @author Juan Diego
 * @author Sebastian Camargo
 */
// Anotación que indica que esta clase es un controlador en una aplicación Spring MVC
@Controller
// Mapea las solicitudes que comienzan con "/app" a este controlador
@RequestMapping("/app")
public class IndexController {

    // Mapea las solicitudes GET para las rutas "/index", "/", y "/home" al método index()
    @GetMapping({"/index","/","/home"})
    public String index(Model model){
        // Retorna el nombre de la vista "index"
        // Spring MVC buscará una plantilla llamada "index" para renderizar la respuesta
        return "index";
    }
}

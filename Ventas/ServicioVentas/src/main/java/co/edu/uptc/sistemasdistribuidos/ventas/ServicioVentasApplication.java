package co.edu.uptc.sistemasdistribuidos.ventas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Clase de inicia de la palicacion de servicio de ventas
 * @author Juliana Rincon
 * @author Luisa Merchan
 * @author Juan Diego
 * @author Sebastian Camargo
 **/
// Anotación que marca esta clase como una aplicación Spring Boot
@SpringBootApplication
public class ServicioVentasApplication {

    // Método principal que inicia la aplicación Spring Boot
    public static void main(String[] args) {
        // Método que lanza la aplicación
        SpringApplication.run(ServicioVentasApplication.class, args);
    }
}

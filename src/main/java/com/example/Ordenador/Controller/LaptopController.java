package com.example.Ordenador.Controller;

import com.example.Ordenador.Entity.Laptop;
import com.example.Ordenador.Repository.LaptopRepository;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
public class LaptopController {

    private final Logger log = LoggerFactory.getLogger(LaptopController.class);
    private final LaptopRepository laptopRepository;

    public LaptopController(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    /**
     * Buscar todas las Laptop que hay en la base de datos
     * http://localhost:8080/api/laptops
     * @return ArrayList<Libros>
     */
    @GetMapping("/api/laptops")
    public List<Laptop> findAll(){
        return laptopRepository.findAll();
    }


    /**
     * Buscar una laptop mediante su id
     * http://localhost:8080/api/laptop/1
     * @param id
     * @return
     */
    @GetMapping("/api/laptop/{id}")
    @Operation(description = "Buscar una laptop por clave primaria id Long")
    public ResponseEntity<Laptop> findOneById(@Parameter(description = "Clave primaria tipo Long") @PathVariable Long id){

        Optional<Laptop> laptopOpt = laptopRepository.findById(id);
        if(laptopOpt.isPresent()){
            return ResponseEntity.ok(laptopOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Crear una nueva laptop en la base de datos
     * http://localhost:8080/api/laptops
     * @param laptop
     * @param headers
     * @return
     */
    @PostMapping("/api/laptops")
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop, @RequestHeader HttpHeaders headers){
        System.out.println(headers.get("User-Agent"));
        if(laptop.getId() != null){
            log.warn("trying to create a laptop with id");
            //System.out.println("trying to create a laptop with id");
            return ResponseEntity.badRequest().build();
        }
        Laptop result = laptopRepository.save(laptop);
        return  ResponseEntity.ok(result);
    }


    /**
     * Actualizar una laptop existente en la base de datos
     * http://localhost:8080/api/laptops
     * @param laptop
     * @return
     */
    @PutMapping("/api/laptops")
    public ResponseEntity<Laptop> update(@RequestBody Laptop laptop){
        if(laptop.getId() == null){
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.badRequest().build();
        }
        if(!laptopRepository.existsById(laptop.getId())){
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.notFound().build();
        }

        Laptop result = laptopRepository.save(laptop);
        return ResponseEntity.ok(result);
    }


    /**
     * Eliminar una laptop de la base de datos mediante su id
     * http://localhost:8080/api/laptops/1
     * @param id
     * @return
     */
    @DeleteMapping("/api/laptops/{id}")
    public ResponseEntity<Laptop> delete(@PathVariable Long id){

        if(!laptopRepository.existsById(id)){
            log.warn("Trying to delete a non existent laptop");
            return ResponseEntity.notFound().build();
        }
        laptopRepository.deleteById(id);
        return  ResponseEntity.noContent().build();
    }


    /**
     * Eliminar todas las laptops de la base de datos
     * http://localhost:8080/api/laptops
     * @return
     */
    @Hidden
    @DeleteMapping("/api/laptops")
    public ResponseEntity<Laptop> deleteAll(){
        log.info("REST Request for delete all laptops");
        laptopRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}




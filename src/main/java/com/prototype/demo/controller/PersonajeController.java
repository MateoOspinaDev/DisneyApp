package com.prototype.demo.controller;

import com.prototype.demo.excepciones.PersonajeNotFoundException;
import com.prototype.demo.excepciones.RequestException;
import com.prototype.demo.model.Personaje;
import com.prototype.demo.dtos.PersonajeSinDetallesDto;
import com.prototype.demo.service.IPersonajeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@AllArgsConstructor
public class PersonajeController {

    @Autowired
    private IPersonajeService iPersonajeService;

    @GetMapping
    public ResponseEntity<List<PersonajeSinDetallesDto>> obtenerPersonajes(){
        if(iPersonajeService.getPersonajesSinDetalles().isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().body(iPersonajeService.getPersonajesSinDetalles());
    }

    @GetMapping("/details")
    public ResponseEntity<List<Personaje>> obtenerPersonajesConDetalles(){
        if(iPersonajeService.getPersonajes().isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok().body(iPersonajeService.getPersonajes());
    }

    @GetMapping(params = "name")
    public ResponseEntity<PersonajeSinDetallesDto> obtenerPersonajePorNombre(@RequestParam("name") String name){
        if(!iPersonajeService.existByNombre(name)){
            throw new PersonajeNotFoundException(HttpStatus.BAD_REQUEST,"EC-003","personaje no existe o parametro de busqueda incorrecto");
        }
        return ResponseEntity.ok().body(iPersonajeService.getPersonajeByNombre(name));
    }


    @GetMapping(params = "age")
    public ResponseEntity<List<PersonajeSinDetallesDto>> obtenerPersonajesPorEdad(@RequestParam("age") int age){
        if(!iPersonajeService.existByEdad(age)){
            throw new PersonajeNotFoundException(HttpStatus.BAD_REQUEST,"EC-003","personaje no existe o parametro de busqueda incorrecto");
        }
        return ResponseEntity.ok().body(iPersonajeService.GetPersonajeByEdad(age));
    }

    @GetMapping(params = "peso")
    public ResponseEntity<List<PersonajeSinDetallesDto>> obtenerPersonajesPorpeso(@RequestParam("peso") float peso){
        if(!iPersonajeService.existByPeso(peso)){
            throw new PersonajeNotFoundException(HttpStatus.BAD_REQUEST,"EC-003","personaje no existe o parametro de busqueda incorrecto");
        }
        return ResponseEntity.ok().body(iPersonajeService.getPersonajeByPeso(peso));
    }


    ///************Por hacer/////********
    @GetMapping(params = "movies")
    public ResponseEntity<List<PersonajeSinDetallesDto>> obtenerPersonajesPorIdMovie(@RequestParam("movies") Long movies){
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.getPersonajeByIdPelicula(movies));
    }
    ///************Por hacer/////********



    @PostMapping
    public ResponseEntity<Personaje> guardarPersonaje (@RequestBody Personaje personaje) throws RuntimeException{
        if(personaje.getHistoria()==null || personaje.getEdad()==0 || personaje.getNombre()==null || personaje.getImagen()==null)
        {
            throw new RequestException(HttpStatus.BAD_REQUEST,"EC-002","ningún dato puede ser nulo");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.savePersonaje(personaje));
    }




    @PutMapping
    public ResponseEntity<Personaje> editarPersonaje ( @RequestBody Personaje personaje){
        if(!iPersonajeService.existById(personaje.getId())){
            throw new PersonajeNotFoundException(HttpStatus.BAD_REQUEST,"EC-005","personaje a editar no existe");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(iPersonajeService.updatePersonaje(personaje));
    }



    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> borrarPersonaje(@PathVariable ("id") Long id){
        if(!iPersonajeService.existById(id)){
            throw new PersonajeNotFoundException(HttpStatus.BAD_REQUEST,"EC-003","personaje no existe o parametro de busqueda incorrecto");
        }
        iPersonajeService.deletePersonaje(id);
        return ResponseEntity.ok().build();
    }


}

package com.ipn.mx.Controller;

import com.ipn.mx.Servicios.CategoriaService;
import com.ipn.mx.Servicios.ProductoService;
import com.ipn.mx.entidades.Categoria;
import com.ipn.mx.entidades.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/producto")
public class ProductoController {
    @Autowired
    private ProductoService servicio;
    @Autowired
    private CategoriaService servicioCategoria;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(servicio.findAll());
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(@PathVariable Long id, @Valid @RequestBody Producto producto, BindingResult resultado){
        Producto productoActual = new Producto();
        Producto productoNuevo;
        Optional<Categoria> categoria = servicioCategoria.findById(id);

        Map<String, Object> response = new HashMap<>();

        if(resultado.hasErrors()) {
            List<String> errores = resultado.getFieldErrors().stream()
                    .map(err -> "error " + err.getField() + " "+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores", errores);

            return new ResponseEntity<Map<String, Object>> (response, HttpStatus.BAD_REQUEST);
        }

        if(categoria.isEmpty()){
            response.put("mensaje ","La Categoria ID = ".concat(id.toString().concat(" no existe")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            productoActual.setNombreProducto(producto.getNombreProducto());
            productoActual.setDescripcionProducto(producto.getDescripcionProducto());
            productoActual.setPrecioProducto(producto.getPrecioProducto());
            productoActual.setExistencia(producto.getExistencia());
            productoActual.setIdCategoria(categoria.get());

            productoNuevo = servicio.save(productoActual);

        }catch(DataAccessException e) {
            response.put("mensaje", "Error al insertar");
            response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
        }

        response.put("mensaje", "insertado satisfactoriamente");
        response.put("categoria", productoNuevo);
        return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CREATED); //codigo 201
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<?> read(@PathVariable Long id) {
        Optional<Producto> producto = null;
        Map<String, Object> response = new HashMap<>();
        try {
            producto = servicio.findById(id);
        } catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(producto.isEmpty()){
            response.put("mensaje: ",
                    "La categoria ID: ".concat(id.toString().concat("No exixte en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        response.put("Producto", producto);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Producto producto, BindingResult resultado, @PathVariable Long id){
        Optional<Producto> productoActual = servicio.findById(id);
        Optional<Categoria> categoria = servicioCategoria.findById(id);

        Producto productoAtualizado = new Producto();

        Map<String, Object> respuesta = new  HashMap<>();

        if(resultado.hasErrors()){
            List<String> errores = resultado .getFieldErrors().stream()
                    .map(err -> "La columna" + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            respuesta.put("errores", errores);
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
        }
        if(productoActual.isEmpty()){
            respuesta.put("mensaje", "Error al actualizar la Categoria ".concat(id.toString()).concat("no existe la base de datos"));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        }
        try{
            productoActual.get().setNombreProducto(producto.getNombreProducto());
            productoActual.get().setDescripcionProducto(producto.getDescripcionProducto());
            productoActual.get().setPrecioProducto(producto.getPrecioProducto());
            productoActual.get().setExistencia(producto.getExistencia());
            productoActual.get().setIdCategoria(categoria.get());

            productoAtualizado = servicio.save(productoActual.get());
        }catch(DataAccessException e){
            respuesta.put("mensaje", "Error al ACtualizar");
            respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "La categria se actualizó satisfactoriamente");
        respuesta.put("categoria", productoAtualizado);

        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        try{
            servicio.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje ", "Error al eliminar la Categoria de la abse de datos");
            response.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El producto ID = ".concat(id.toString().concat(" se ha eliminado satisfactoriamente")));
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}

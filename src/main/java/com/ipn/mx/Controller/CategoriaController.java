package com.ipn.mx.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.ipn.mx.Servicios.CategoriaService;
import com.ipn.mx.entidades.Categoria;



@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api/categoria")
public class CategoriaController {
	@Autowired
	private CategoriaService servicio;
	
	@GetMapping("/get-all")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(servicio.findAll());
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Categoria categoria, BindingResult resultado){
		Categoria categoriaNueva = null;
		Map<String, Object> response = new HashMap<>();

		if(resultado.hasErrors()) {
			List<String> errores = resultado.getFieldErrors().stream()
					.map(err -> "error" + err.getField() + " "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores", errores);

			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.BAD_REQUEST);
		}

		try {
			categoriaNueva = servicio.save(categoria);

		}catch(DataAccessException e) {
			response.put("mensaje", "Error al insertar");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR); //error 500
		}

		response.put("mensaje", "insertado satisfactoriamente");
		response.put("categoria", categoriaNueva);
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CREATED); //codigo 201
	}

	@GetMapping("/get-one/{id}")
	public ResponseEntity<?> read(@PathVariable Long id) {
		Optional<Categoria> categoria = null;
		Map<String, Object> response = new HashMap<>();
		try {
			categoria = servicio.findById(id);
		} catch (DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(" : ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(categoria.isEmpty()){
			response.put("mensaje:",
				"La categoria ID: ".concat(id.toString().concat("No exixte en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		response.put("categoria", categoria);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Categoria categoria, BindingResult resultado, @PathVariable Long id){
		Optional<Categoria> categoriaActual = servicio.findById(id);
		Categoria categoriaAtualizada = new Categoria();

		Map<String, Object> respuesta = new  HashMap<>();
		if(resultado.hasErrors()){
			List<String> errores = resultado .getFieldErrors().stream()
					.map(err -> "La columna" + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.toList());
			respuesta.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
		if(categoriaActual.isEmpty()){
			respuesta.put("mensaje", "Error al actualizar la Categoria ".concat(id.toString()).concat("no existe la base de datos"));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
		}
		try{
			categoriaActual.get().setNombreCategoria(categoria.getNombreCategoria());
			categoriaActual.get().setDescripcionCategoria(categoria.getDescripcionCategoria());
			categoriaAtualizada = servicio.save(categoriaActual.get());
		}catch(DataAccessException e){
			respuesta.put("mensaje", "Error al ACtualizar");
			respuesta.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		respuesta.put("mensaje", "La categoria se actualizó satisfactoriamente");
		respuesta.put("categoria", categoriaAtualizada);

		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		try{
			servicio.delete(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al eliminar la Categoria de la abse de datos");
			response.put("error", e.getMessage().concat(" = ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje","La categoria ID = ".concat(id.toString().concat(" se ha eliminado satisfactoriamente")));
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}

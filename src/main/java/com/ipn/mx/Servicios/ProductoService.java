package com.ipn.mx.Servicios;

import java.util.List;
import java.util.Optional;

import com.ipn.mx.entidades.Producto;

public interface ProductoService {
	List<Producto> findAll();
	Optional<Producto> findById(Long id);
	Producto save(Producto producto);
	void delete(Long id);
}

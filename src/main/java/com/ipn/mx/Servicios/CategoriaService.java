package com.ipn.mx.Servicios;

import java.util.List;
import java.util.Optional;

import com.ipn.mx.entidades.Categoria;

public interface CategoriaService {
	List<Categoria> findAll();
	Optional<Categoria> findById(Long id);
	Categoria save(Categoria categoria);
	void delete(Long id);
}

package com.ipn.mx.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ipn.mx.entidades.Categoria;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Long>{
	@Query("from Categoria")
	public List<Categoria> findAllCategorias();
}

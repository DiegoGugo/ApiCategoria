package com.ipn.mx.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipn.mx.DAO.CategoriaDAO;
import com.ipn.mx.entidades.Categoria;

@Service
public class CategoriaServiceImp implements CategoriaService{
	@Autowired
	private CategoriaDAO servicio;
	
	@Override
	@Transactional(readOnly=true)
	public List<Categoria> findAll() {
		return servicio.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Categoria> findById(Long id) {
		return servicio.findById(id);
	}

	@Override
	@Transactional
	public Categoria save(Categoria categoria) {	 
		return servicio.save(categoria);
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		servicio.deleteById(id);		
	}

}

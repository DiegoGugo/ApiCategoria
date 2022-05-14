package com.ipn.mx.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipn.mx.entidades.Producto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoDAO extends JpaRepository<Producto, Long>{

}

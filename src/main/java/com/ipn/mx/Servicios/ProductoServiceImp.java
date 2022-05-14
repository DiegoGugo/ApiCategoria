package com.ipn.mx.Servicios;

import com.ipn.mx.DAO.ProductoDAO;
import com.ipn.mx.entidades.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImp implements ProductoService{
    @Autowired
    private ProductoDAO servicio;

    @Override
    public List<Producto> findAll() {
        return servicio.findAll();
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return servicio.findById(id);
    }

    @Override
    public Producto save(Producto producto) {
        return servicio.save(producto);
    }

    @Override
    public void delete(Long id) {
        servicio.deleteById(id);
    }
}

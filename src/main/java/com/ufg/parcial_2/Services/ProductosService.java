package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Models.Productos;
import com.ufg.parcial_2.Repositories.ProductosRepository;

import java.util.List;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductosService {
    @Autowired
    private ProductosRepository productosRepository;

    public List<Productos> getAllProductos() {
        return productosRepository.findAll();
    }

    public Productos saveProducto(Productos producto) {
        return productosRepository.save(producto);
    }

    public Productos getProductoById(Long id) {
        return productosRepository.findById(id).orElse(null);
    }

    public boolean actualizarProducto(Long id, Productos producto) {
        Productos productoExistente = productosRepository.findById(id).orElse(null);

        if (productoExistente != null) {
            productoExistente.setCategoria(producto.getCategoria());
            productoExistente.setProducto(producto.getProducto());
            productoExistente.setDescripcion(producto.getDescripcion());
            productoExistente.setImagen(producto.getImagen());
            productoExistente.setPrecio(producto.getPrecio());
            productoExistente.setStock(producto.getStock());

            productoExistente.setUsuarioModificacion(producto.getUsuarioModificacion());
            productoExistente.setFechaModificacion(LocalDate.now());
            productosRepository.save(productoExistente);

            return true;
        }
        else {
            return false;
        }
    }

    public void deleteProducto(Long id) {
        productosRepository.deleteById(id);
    }

    public List<Productos> listarProductos() {
        return productosRepository.findAll();
    }
}
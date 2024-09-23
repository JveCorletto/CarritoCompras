package com.ufg.parcial_2.Controllers;

import com.ufg.parcial_2.DTO.APIResponses;
import com.ufg.parcial_2.Models.Productos;
import com.ufg.parcial_2.Services.ProductosService;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("API/Productos")
public class ProductosController {
    @Autowired
    private ProductosService productosService;

    @GetMapping
    public ResponseEntity<APIResponses> getAll() {
        try {
            List<Productos> productos = productosService.getAllProductos();
            return ResponseEntity.ok(new APIResponses(1, null, productos));
        }
        catch(Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al obtener el listado de productos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponses> getById(@PathVariable Long id) {
        try {
            Productos producto = productosService.getProductoById(id);
            if (producto != null) {
                return ResponseEntity.ok(new APIResponses(1, null, producto));
            } else {
                return ResponseEntity.ok(new APIResponses(0, "No se encontró el producto", null));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al obtener el producto: " + e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<APIResponses> save(@RequestBody Productos producto) {
        try {
            if (producto != null) {
                producto.setFechaCreacion(LocalDate.now());
                Productos newProducto = productosService.saveProducto(producto);

                if (newProducto.getIdProducto() > 0) {
                    return ResponseEntity.ok(new APIResponses(1, "Producto guardado correctamente.", null));
                }
                else {
                    return ResponseEntity.ok(new APIResponses(0, "Hubo un error al crear el producto.", null));
                }
            }
            else {
                return ResponseEntity.ok(new APIResponses(0, "Debe de brindar un producto válido a guardar.", null));
            }
        }
        catch (Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al realizar la operación: " + e.getMessage(), null));
        }
    }

    @PutMapping
    public ResponseEntity<APIResponses> update(@RequestBody Productos producto) {
        try {
            if (producto != null) {
                producto.setFechaModificacion(LocalDate.now());
                if (productosService.updateProducto(producto.getIdProducto(), producto)) {
                    return ResponseEntity.ok(new APIResponses(1, "Producto modificado correctamente.", null));
                }
                else {
                    return ResponseEntity.ok(new APIResponses(0, "Hubo un error al modificar el producto.", null));
                }
            }
            else {
                return ResponseEntity.ok(new APIResponses(0, "Debe de brindar un producto válido a modificar.", null));
            }
        }
        catch (Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al realizar la operación: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponses> delete(@PathVariable Long id) {
        try {
            productosService.deleteProducto(id);

            if (productosService.getProductoById(id) == null) {
                return ResponseEntity.ok(new APIResponses(1, "Producto eliminado correctamente.", null));
            }
            else {
                return ResponseEntity.ok(new APIResponses(0, "Hubo un error al eliminar el producto.", null));
            }
        }
        catch (Exception e) {
            return ResponseEntity.ok(new APIResponses(0, "Error al realizar la operación: " + e.getMessage(), null));
        }
    }
}
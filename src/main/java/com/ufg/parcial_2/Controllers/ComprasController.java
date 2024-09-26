package com.ufg.parcial_2.Controllers;

import com.ufg.parcial_2.DTO.MyKartDTO;
import com.ufg.parcial_2.DTO.MyKartDetails;
import com.ufg.parcial_2.Models.*;
import com.ufg.parcial_2.Repositories.*;
import com.ufg.parcial_2.DTO.KartRequest;
import com.ufg.parcial_2.DTO.APIResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.security.Principal;

import com.ufg.parcial_2.Services.ComprasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("API/Compras")
public class ComprasController {
    @Autowired
    private ComprasRepository comprasRepository;

    @Autowired
    private ComprasService comprasService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private EstadosComprasRepository estadosComprasRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private DetallesComprasRepository detallesComprasRepository;

    @PostMapping("/AddToKart")
    public ResponseEntity<APIResponses> AddToKart(@RequestBody KartRequest kartRequest, Principal principal) {
        String username = principal.getName();
        Usuarios usuario = usuariosRepository.findByUsuario(username);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponses(0, "Usuario no encontrado.", null));
        }

        // 2. Buscar la última compra activa del usuario
        Optional<Compras> compraOpt = comprasRepository.getActiveCompra(usuario.getIdUsuario());

        Compras compra;
        if (compraOpt.isPresent()) {
            compra = compraOpt.get();
        } else {
            // 3. Verificar que el estado de la compra esté presente
            EstadosCompras estadoCompra = estadosComprasRepository.findById(1).orElse(null);
            if (estadoCompra == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new APIResponses(0, "Estado de compra inválido.", null));
            }

            // Crear una nueva compra
            compra = new Compras();
            compra.setIdUsuario(usuario);
            compra.setEstadoCompra(estadoCompra);
            compra.setFechaCompra(LocalDate.now());
            compra = comprasRepository.save(compra);
        }

        // 4. Verificar que el producto exista
        Productos producto = productosRepository.findById(kartRequest.getIdProducto()).orElse(null);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponses(0, "Producto no encontrado.", null));
        }

        // 5. Validar que la cantidad sea mayor a 0
        if (kartRequest.getCantidad() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponses(0, "La cantidad debe ser mayor que cero.", null));
        }

        // 6. Verificar que haya suficiente stock
        if (kartRequest.getCantidad() > producto.getStock()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new APIResponses(0, "Stock insuficiente. Solo quedan " + producto.getStock() + " unidades.", null));
        }

        // 7. Verificar si el producto ya está en el carrito
        Optional<DetallesCompras> detalleOpt = detallesComprasRepository.findByCompraAndProducto(compra.getIdCompra(), producto.getIdProducto());

        DetallesCompras detalle;
        if (detalleOpt.isPresent()) {
            // Si el producto ya está en el carrito, actualizamos la cantidad y el subtotal
            detalle = detalleOpt.get();
            detalle.setCantidad(detalle.getCantidad() + kartRequest.getCantidad()); // Actualizar la cantidad
            detalle.setSubTotal(detalle.getPrecioUnitario() * detalle.getCantidad()); // Actualizar el subtotal
        } else {
            // Si no está, creamos un nuevo detalle
            detalle = new DetallesCompras();
            detalle.setProducto(producto);
            detalle.setCompra(compra);
            detalle.setCantidad(kartRequest.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubTotal(producto.getPrecio() * kartRequest.getCantidad());
        }

        // Guardar el detalle de la compra
        detallesComprasRepository.save(detalle);

        // Actualizar el stock del producto
        producto.setStock(producto.getStock() - kartRequest.getCantidad());
        productosRepository.save(producto);  // Guardar el nuevo stock del producto

        Integer totalProductos = detallesComprasRepository.sumCantidadByCompra(compra.getIdCompra());
        if (totalProductos == null) {
            totalProductos = 0;
        }

        // 8. Retornar una respuesta exitosa
        return ResponseEntity.ok(new APIResponses(1, "Producto agregado al carrito exitosamente.", totalProductos));
    }

    @GetMapping("/GetProductsOnKart")
    public ResponseEntity<APIResponses> GetProductsOnKart(Principal principal) {
        Integer totalProductos = 0;
        String username = principal.getName();
        Usuarios usuario = usuariosRepository.findByUsuario(username);
        if (usuario == null) {
            return ResponseEntity.ok(new APIResponses(0, null, totalProductos));
        }

        Optional<Compras> compraOpt = comprasRepository.getActiveCompra(usuario.getIdUsuario());
        if (compraOpt.isPresent()) {
            Compras compra = compraOpt.get();
            totalProductos = detallesComprasRepository.sumCantidadByCompra(compra.getIdCompra());

            if (totalProductos == null) {
                totalProductos = 0;
            }
        }

        // 8. Retornar una respuesta exitosa
        return ResponseEntity.ok(new APIResponses(1, null, totalProductos));
    }

    @GetMapping("/GetMyKart")
    public ResponseEntity<APIResponses> GetMyKart(Principal principal) {
        String username = principal.getName();
        Usuarios usuario = usuariosRepository.findByUsuario(username);
        if (usuario == null) {
            return ResponseEntity.ok(new APIResponses(0, "Error al identificar el usuario", null));
        }

        Optional<Compras> compraOpt = comprasRepository.getActiveCompra(usuario.getIdUsuario());
        if (compraOpt.isPresent()) {
            Compras compra = compraOpt.get();

            MyKartDTO myKartDTO = new MyKartDTO();
            myKartDTO.setIdCompra(compra.getIdCompra());
            myKartDTO.setEstadoCompra(compra.getEstadoCompra().getEstadoCompra());
            myKartDTO.setUsuarioCompra(compra.getIdUsuario().getUsuario());
            myKartDTO.setFechaCompra(compra.getFechaCompra());

            List<MyKartDetails> kartDetailsList = new ArrayList<>();
            for (DetallesCompras detalle : detallesComprasRepository.findByIdCompra(compra.getIdCompra())) {
                MyKartDetails kartDetails = new MyKartDetails();
                kartDetails.setIdDetalleCompra(detalle.getIdDetalleCompra());
                kartDetails.setCantidad(detalle.getCantidad());
                kartDetails.setProducto(detalle.getProducto().getProducto());
                kartDetails.setImagen(detalle.getProducto().getImagen());
                kartDetails.setPrecioUnitario(detalle.getPrecioUnitario());
                kartDetails.setSubTotal(detalle.getSubTotal());
                kartDetailsList.add(kartDetails);
            }

            myKartDTO.setKartDetails(kartDetailsList);
            return ResponseEntity.ok(new APIResponses(1, null, myKartDTO));
        }
        else {
            return ResponseEntity.ok(new APIResponses(0, "No se pudo obtener el detalle del carrito.", null));
        }
    }
}
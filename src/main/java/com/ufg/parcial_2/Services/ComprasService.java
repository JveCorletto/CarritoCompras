package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Models.Compras;
import com.ufg.parcial_2.Repositories.ComprasRepository;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ComprasService {
    @Autowired
    private ComprasRepository comprasRepository;

    public Compras saveCompra(Compras compra) {
        return comprasRepository.save(compra);
    }

    public Compras getCompraById(Long id) {
        return comprasRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrada"));
    }

    public boolean updateCompra(Long id, Compras compra) {
        Compras compraExistente = comprasRepository.findById(id).orElse(null);
        if (compraExistente != null) {
            compraExistente.setFechaCompra(compra.getFechaCompra());
            compraExistente.setTotalCompra(compra.getTotalCompra());
            compraExistente.setEstadoCompra(compra.getEstadoCompra());
            compraExistente.setComprobante(compra.getComprobante());
            comprasRepository.save(compraExistente);

            return true;
        }
        else {
            return false;
        }
    }

    public void deleteCompra(Long id) {
        comprasRepository.deleteById(id);
    }

    public List<Compras> getAllCompras() {
        return comprasRepository.findAll();
    }
}
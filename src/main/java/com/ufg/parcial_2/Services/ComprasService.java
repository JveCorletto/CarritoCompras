package com.ufg.parcial_2.Services;
import com.ufg.parcial_2.Models.Compras;
import com.ufg.parcial_2.Models.EstadosCompras;
import com.ufg.parcial_2.Repositories.ComprasRepository;
import com.ufg.parcial_2.Repositories.DetallesComprasRepository;
import com.ufg.parcial_2.Repositories.EstadosComprasRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComprasService {
    @Autowired
    private ComprasRepository comprasRepository;

    @Autowired
    private EstadosComprasRepository estadosComprasRepository;

    @Autowired
    private DetallesComprasRepository detallesComprasRepository;

    public Compras saveCompra(Compras compra) {
        return comprasRepository.save(compra);
    }

    public Compras getCompraById(Long id) {
        return comprasRepository.findById(id).orElse(null);
    }

    public void deleteCompra(Long id) {
        comprasRepository.deleteById(id);
    }

    @Transactional
    public boolean markAsPayed(Long id) {
        EstadosCompras estadoPagado = estadosComprasRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Estado 'Pagada' no encontrado"));

        Compras compraExistente = comprasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra N° " + id + " no encontrada"));

        compraExistente.setEstadoCompra(estadoPagado);
        comprasRepository.save(compraExistente);
        return true;
    }

    @Transactional
    public boolean updateTotal(Long idCompra) {
        Double sumaSubTotal = detallesComprasRepository.sumByCompraId(idCompra)
                .orElse(0.00);

        Compras compra = comprasRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra N° " + idCompra + " no encontrada"));

        compra.setTotalCompra(sumaSubTotal);
        comprasRepository.save(compra);
        return true;
    }
}
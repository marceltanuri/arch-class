package com.app.infrastructure.persistence;

import com.app.application.ports.IPedidoRepository;
import com.app.domain.Pedido;
import org.springframework.stereotype.Component;

@Component
public class JpaPedidoRepository implements IPedidoRepository {

    private final SpringDataJpaPedidoRepository repository;

    public JpaPedidoRepository(SpringDataJpaPedidoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void salvar(Pedido pedido) {
        repository.save(pedido);
    }
}

package com.app.application.usecases;

import com.app.application.ports.IEstoqueService;
import com.app.application.ports.IPedidoRepository;
import com.app.domain.Pedido;
import org.springframework.stereotype.Service;

@Service
public class CriarPedidoUseCase {
    private final IPedidoRepository pedidoRepository;
    private final IEstoqueService estoqueService;

    public CriarPedidoUseCase(IPedidoRepository pedidoRepository, IEstoqueService estoqueService) {
        this.pedidoRepository = pedidoRepository;
        this.estoqueService = estoqueService;
    }

    public void execute(Pedido pedido) {
        if (estoqueService.verificarDisponibilidade(pedido.getProductId())) {
            pedidoRepository.salvar(pedido);
        } else {
            throw new IllegalStateException("Produto fora de estoque.");
        }
    }
}

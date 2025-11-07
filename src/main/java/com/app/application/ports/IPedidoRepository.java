package com.app.application.ports;

import com.app.domain.Pedido;

public interface IPedidoRepository {
    void salvar(Pedido pedido);
}

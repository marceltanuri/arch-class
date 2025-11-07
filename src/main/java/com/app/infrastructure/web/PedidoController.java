package com.app.infrastructure.web;

import com.app.application.usecases.CriarPedidoUseCase;
import com.app.domain.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CriarPedidoUseCase criarPedidoUseCase;

    public PedidoController(CriarPedidoUseCase criarPedidoUseCase) {
        this.criarPedidoUseCase = criarPedidoUseCase;
    }

    @PostMapping
    public ResponseEntity<String> criarPedido(@RequestBody CriarPedidoRequest request) {
        // O Controller é o "adaptador" que traduz o DTO da requisição
        // para o objeto de domínio (Entity).
        Pedido pedido = new Pedido(request.getProductId(), request.getQuantidade(), request.getValor());

        try {
            criarPedidoUseCase.execute(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pedido criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

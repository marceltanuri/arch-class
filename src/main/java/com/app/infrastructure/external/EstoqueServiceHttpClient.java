package com.app.infrastructure.external;

import com.app.application.ports.IEstoqueService;
import org.springframework.stereotype.Component;

@Component
public class EstoqueServiceHttpClient implements IEstoqueService {

    private final FeignEstoqueClient feignClient;

    public EstoqueServiceHttpClient(FeignEstoqueClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public boolean verificarDisponibilidade(String productId) {
        // A lógica de "tradução" e chamada ao serviço externo fica aqui.
        try {
            return feignClient.verificarEstoque(productId);
        } catch (Exception e) {
            // Em um caso real, trataríamos exceções de rede, timeouts, etc.
            System.err.println("Falha ao comunicar com o serviço de estoque: " + e.getMessage());
            return false;
        }
    }
}

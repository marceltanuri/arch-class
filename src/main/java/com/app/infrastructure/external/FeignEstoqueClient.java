package com.app.infrastructure.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "estoque-service", url = "${clients.estoque.url}")
public interface FeignEstoqueClient {
    @GetMapping("/api/estoque/{productId}")
    boolean verificarEstoque(@PathVariable("productId") String productId);
}

package com.app.infrastructure.web;

// DTO (Data Transfer Object) para receber os dados da requisição de criação de pedido.
public class CriarPedidoRequest {
    private String productId;
    private int quantidade;
    private double valor;

    // Getters e Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

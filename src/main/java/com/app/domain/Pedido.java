package com.app.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String productId;
    private int quantidade;
    private double valor;

    public Pedido() {
        // Construtor padrão para JPA
    }

    public Pedido(String productId, int quantidade, double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor do pedido não pode ser negativo.");
        }
        this.productId = productId;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValor() {
        return valor;
    }
}

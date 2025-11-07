package com.app.infrastructure.persistence;

import com.app.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaPedidoRepository extends JpaRepository<Pedido, Long> {
}

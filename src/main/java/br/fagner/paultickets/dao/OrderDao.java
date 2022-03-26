package br.fagner.paultickets.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fagner.paultickets.model.Order;

public interface OrderDao extends JpaRepository<Order, String> {

}

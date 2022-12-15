package br.fagner.paultickets.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fagner.paultickets.model.EventOrder;

public interface OrderDao extends JpaRepository<EventOrder, String> {

}

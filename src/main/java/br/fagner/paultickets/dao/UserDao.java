package br.fagner.paultickets.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.fagner.paultickets.model.User;

public interface UserDao extends JpaRepository<User, String> {

}

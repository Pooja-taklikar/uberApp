package com.codingshuttle.uber.uberApp.repositories;

import com.codingshuttle.uber.uberApp.entities.User;
import com.codingshuttle.uber.uberApp.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByUser(User user);
}

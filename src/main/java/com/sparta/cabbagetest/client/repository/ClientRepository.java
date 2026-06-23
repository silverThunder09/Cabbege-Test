package com.sparta.cabbagetest.client.repository;

import com.sparta.cabbagetest.client.domain.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);
}

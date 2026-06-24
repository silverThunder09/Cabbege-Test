package com.example.cabbagemarket10.domain.client.repository;

import com.example.cabbagemarket10.domain.client.domain.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);
}

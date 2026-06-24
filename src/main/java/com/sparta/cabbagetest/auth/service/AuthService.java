package com.sparta.cabbagetest.auth.service;

import com.sparta.cabbagetest.auth.dto.request.SignupRequest;
import com.sparta.cabbagetest.auth.dto.response.SignupResponse;
import com.sparta.cabbagetest.auth.exception.DuplicateEmailException;
import com.sparta.cabbagetest.client.domain.Client;
import com.sparta.cabbagetest.client.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException();
        }

        Client client = Client.create(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getName(),
                request.getPhone()
        );

        return SignupResponse.from(clientRepository.save(client));
    }
}

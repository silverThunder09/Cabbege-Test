package com.sparta.cabbagetest.auth.service;

import com.sparta.cabbagetest.auth.dto.SignupRequest;
import com.sparta.cabbagetest.auth.dto.SignupResponse;
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
        if (clientRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException();
        }

        Client client = Client.create(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname(),
                request.name(),
                request.phone()
        );

        return SignupResponse.from(clientRepository.save(client));
    }
}

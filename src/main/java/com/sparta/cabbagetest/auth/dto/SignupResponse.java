package com.sparta.cabbagetest.auth.dto;

import com.sparta.cabbagetest.client.domain.Client;
import com.sparta.cabbagetest.client.domain.ClientRole;
import com.sparta.cabbagetest.client.domain.ClientStatus;
import java.time.LocalDateTime;

public record SignupResponse(
        Long id,
        String email,
        String nickname,
        String name,
        ClientRole role,
        ClientStatus status,
        boolean verified,
        LocalDateTime createdAt
) {

    public static SignupResponse from(Client client) {
        return new SignupResponse(
                client.getId(),
                client.getEmail(),
                client.getNickname(),
                client.getName(),
                client.getRole(),
                client.getStatus(),
                client.isVerified(),
                client.getCreatedAt()
        );
    }
}

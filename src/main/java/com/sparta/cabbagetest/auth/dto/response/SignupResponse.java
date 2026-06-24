package com.sparta.cabbagetest.auth.dto.response;

import com.sparta.cabbagetest.client.domain.Client;
import com.sparta.cabbagetest.client.domain.ClientRole;
import com.sparta.cabbagetest.client.domain.ClientStatus;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SignupResponse {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String name;
    private final ClientRole role;
    private final ClientStatus status;
    private final boolean verified;
    private final LocalDateTime createdAt;

    private SignupResponse(
            Long id,
            String email,
            String nickname,
            String name,
            ClientRole role,
            ClientStatus status,
            boolean verified,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.role = role;
        this.status = status;
        this.verified = verified;
        this.createdAt = createdAt;
    }

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

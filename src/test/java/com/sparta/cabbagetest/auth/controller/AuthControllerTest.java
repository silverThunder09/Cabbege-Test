package com.sparta.cabbagetest.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.cabbagetest.auth.dto.request.SignupRequest;
import com.sparta.cabbagetest.client.domain.Client;
import com.sparta.cabbagetest.client.repository.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:signup-test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.flyway.enabled=false"
})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        clientRepository.deleteAll();
    }

    @Test
    void 회원가입_성공시_비밀번호와_전화번호를_제외한_회원정보를_반환한다() throws Exception {
        SignupRequest request = new SignupRequest(
                "signup@example.com",
                "password123!",
                "배추판매자",
                "홍길동",
                "010-1234-5678"
        );

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("signup@example.com"))
                .andExpect(jsonPath("$.nickname").value("배추판매자"))
                .andExpect(jsonPath("$.name").value("홍길동"))
                .andExpect(jsonPath("$.role").value("USER"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.verified").value(false))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.phone").doesNotExist());
    }

    @Test
    void 회원가입_성공시_비밀번호는_해시로_저장한다() throws Exception {
        SignupRequest request = new SignupRequest(
                "encoded@example.com",
                "password123!",
                "배추구매자",
                "김철수",
                null
        );

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        Client client = clientRepository.findAll().get(0);
        assertThat(client.getPassword()).isNotEqualTo("password123!");
        assertThat(passwordEncoder.matches("password123!", client.getPassword())).isTrue();
    }

    @Test
    void 이미_가입된_이메일이면_회원가입에_실패한다() throws Exception {
        clientRepository.save(Client.create(
                "duplicate@example.com",
                passwordEncoder.encode("password123!"),
                "기존회원",
                "이영희",
                null
        ));

        SignupRequest request = new SignupRequest(
                "duplicate@example.com",
                "password123!",
                "신규회원",
                "박민수",
                null
        );

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void 필수값이_누락되면_회원가입에_실패한다() throws Exception {
        String request = """
                {
                  "email": "",
                  "password": "password123!",
                  "nickname": "닉네임"
                }
                """;

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.password").doesNotExist());
    }
}

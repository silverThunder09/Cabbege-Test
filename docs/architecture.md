# 아키텍처

## 기술 기준

- Java 17, Spring Boot 4.1.x, Gradle Wrapper
- Spring MVC, Spring Data JPA, QueryDSL 5.1 Jakarta
- Spring Security, MySQL, Flyway
- 테스트 H2
- 기능 도입 시 Redis, Caffeine, WebSocket/STOMP, AWS S3, PortOne

## 계층

```text
Controller -> Service -> Repository -> DB
                         -> External Adapter
```

- Controller: HTTP Adapter
- Service: 권한, 비즈니스 규칙, 상태 전이, 트랜잭션
- Repository: 조회와 영속화
- External Adapter: PortOne, S3 등 외부 시스템 격리
- DTO: API 계약이며 Entity와 분리

## 패키지

```text
com.sparta.cabbagetest
├── domain
│   ├── client, item, chat, coupon
│   ├── trade, payment, review
│   └── inquiry, follow
└── global
    ├── auth, config, error
    ├── common
    └── external
```

각 도메인은 필요에 따라 `controller`, `service`, `repository`, `entity`, `dto`를 둔다.

## 데이터와 캐시

| 데이터 | 저장소 |
|---|---|
| 영속 도메인 데이터 | MySQL |
| 인기 검색어 | Redis ZSet |
| 선착순 쿠폰 수량·발급 식별 | Redis |
| Refresh Token | Redis |
| 카테고리·빈번한 조회 | Caffeine |

## 트랜잭션

- 트랜잭션 경계는 Service에 둔다.
- 조회 Service는 `readOnly = true`를 고려한다.
- 느린 외부 API 호출을 DB 락 구간에 포함하지 않도록 설계한다.
- 외부 API 성공과 DB 반영 사이의 실패 및 재시도 전략을 정의한다.

## 기술 결정 필요 항목

- 상품 구매: 낙관적 락 또는 비관적 락
- 쿠폰 발급: Redis Lua 또는 분산 락
- 이미지 저장: 초기 로컬 또는 S3
- 채팅 확장: 단일 서버 또는 Redis Pub/Sub

결정 시 `docs/adr/`에 근거와 결과를 기록한다.

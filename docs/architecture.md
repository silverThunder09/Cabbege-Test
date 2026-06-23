# 아키텍처

## 기술 기준

- Java 17
- Spring Boot 4.1.x
- Spring MVC
- Spring WebSocket
- Spring Security
- Spring Data JPA
- MySQL
- Gradle

## 계층

```text
Controller -> Service -> Repository -> DB
Controller -> WebSocket Handler -> Service -> Repository -> DB
```

- Controller: HTTP 요청 검증, 인증 사용자 전달, DTO 변환
- WebSocket Handler: 연결, 구독, 메시지 라우팅
- Service: 트랜잭션, 권한, 상태 변경, 비즈니스 규칙
- Repository: Entity 조회와 영속화
- Entity: 도메인 상태와 최소 행위
- DTO: API 요청·응답 계약

## 패키지

```text
com.sparta.cabbagetest
├── domain
│   ├── auth
│   ├── client
│   ├── category
│   ├── item
│   ├── itemimage
│   ├── itemlike
│   ├── inquiry
│   ├── follow
│   ├── chat
│   ├── review
│   └── auction
└── global
    ├── config
    ├── error
    ├── security
    └── common
```

각 도메인은 필요에 따라 `controller`, `service`, `repository`, `entity`, `dto`를 둔다.

## 트랜잭션

- 트랜잭션 경계는 Service에 둔다.
- 조회 메서드는 `readOnly = true`를 기본으로 검토한다.
- 카운트 컬럼 변경은 원본 행 변경과 같은 트랜잭션에서 처리한다.
- 입찰은 동시성 제어 전략을 정한 뒤 구현한다.

## 외부 시스템 후보

| 목적 | 후보 |
|---|---|
| 이미지 저장 | 로컬, S3 |
| 인기 검색어 | Redis Sorted Set |
| WebSocket 확장 | Redis Pub/Sub |
| 캐시 | Caffeine |

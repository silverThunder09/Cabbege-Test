# 개발 컨벤션

## Java와 Spring

- Service 메서드는 하나의 유스케이스를 표현한다.
- Controller에 비즈니스 로직을 두지 않는다.
- 생성자 주입만 사용한다.
- Lombok `@Data`를 Entity에 사용하지 않는다.
- 의미 없는 축약어와 범용 이름 `Util`, `Manager`, `Data`를 남용하지 않는다.

## DTO와 예외

- Request DTO와 Response DTO를 분리한다.
- Entity를 API 응답으로 직접 반환하지 않는다.
- 도메인 예외는 전역 예외 처리기에서 HTTP 응답으로 변환한다.
- 내부 구현 메시지와 stack trace를 응답에 노출하지 않는다.

## JPA

- 연관관계는 필요한 방향만 매핑한다.
- 연관관계 fetch는 기본 LAZY로 둔다.
- 컬렉션을 JSON으로 직접 직렬화하지 않는다.
- equals/hashCode에 변경 가능한 필드나 연관관계를 포함하지 않는다.
- Soft Delete 대상은 일반 조회에서 제외한다.
- 복합키 Entity는 식별자 클래스를 명확히 분리한다.

## 네이밍

- Java 클래스명은 PascalCase를 사용한다.
- Java 필드명은 camelCase를 사용한다.
- DB 테이블과 컬럼은 snake_case를 사용한다.
- API path는 kebab-case를 사용한다.

## 문서 동기화

- Entity 컬럼과 관계 변경 시 `docs/ERD.md`를 수정한다.
- API path, method, 인증 기준 변경 시 `docs/api.md`를 수정한다.
- 도메인 상태나 권한 규칙 변경 시 `docs/business-rules.md`를 수정한다.

# 개발 컨벤션

## Java와 Spring

- Service 메서드는 한 유스케이스를 표현한다.
- 의미 없는 축약어와 범용 이름 `Util`, `Manager`, `Data`를 남용하지 않는다.
- Lombok `@Data`를 Entity에 사용하지 않는다.

## DTO와 예외

- Request와 Response DTO를 분리한다.
- 도메인 예외와 오류 코드는 전역 예외 처리기에서 HTTP 응답으로 변환한다.
- 오류 메시지는 사용자가 이해할 수 있어야 하며 내부 구현을 노출하지 않는다.

## JPA

- 연관관계는 필요한 방향만 매핑한다.
- `FetchType.LAZY`를 기본으로 한다.
- 컬렉션을 직렬화하지 않는다.
- equals/hashCode에 변경 가능한 필드나 연관관계를 포함하지 않는다.
- N+1, 과도한 Fetch Join, 무제한 전체 조회를 점검한다.

## QueryDSL

- 동적 검색·필터·정렬은 Custom Repository에서 구현한다.
- 핵심 조회에 `QuerydslPredicateExecutor`를 사용하지 않는다.
- 조회 DTO Projection과 Entity 조회를 목적에 맞게 구분한다.
- Q 클래스 생성 경로는 `build/generated/sources/annotationProcessor/java/main`이다.
- 생성된 Q 클래스를 Git에 커밋하지 않는다.

## Flyway

- 스키마 변경은 migration 파일로 관리한다.
- 이미 적용된 migration은 수정하지 않고 새 버전을 만든다.
- 파괴적 변경은 롤백·데이터 이전 계획을 PR에 작성한다.

## 의존성

- 기능 코드와 필요한 의존성을 같은 PR에 추가한다.
- Spring Boot가 관리하는 버전은 임의로 덮어쓰지 않는다.
- 중복 기능 라이브러리를 추가하지 않는다. BCrypt는 Spring Security 구현을 사용한다.
- 사용하지 않는 의존성을 미리 추가하지 않는다.

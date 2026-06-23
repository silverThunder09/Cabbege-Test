---
name: review
description: |
  현재 브랜치의 변경사항을 기준 브랜치와 비교해 코드 리뷰를 수행한다.
  문서 정합성, 컨벤션, 도메인 규칙, 테스트 시나리오 커버리지, 테스트 실행 결과를 포함한다.
  트리거: "리뷰해줘", "코드 봐줘", "머지 전에 확인해줘", "코드리뷰", "/review", "/rv"
---

# review

## 목적

병합 전 변경사항에서 버그·보안 위험, 문서 정합성 위반, 컨벤션 위반, 도메인 규칙 위반, 필수 테스트 시나리오 누락을 찾는다.

## 입력

```text
/review base=<branch>
/rv base=<branch>
```

기본값: `base` 없으면 `dev`, `dev` 없으면 `main`.

## 절차

### 1단계 — 상태·범위 파악

```bash
git status --short --branch
git diff --name-only <base>...HEAD
git diff --stat <base>...HEAD
```

변경된 도메인(auth, client, item, …)과 계층(controller, service, entity, dto, test)을 파악한다.

### 2단계 — diff 읽기

```bash
git diff --unified=10 <base>...HEAD -- <file>
```

변경된 파일별로 diff만 읽는다. 변경되지 않은 소스 파일은 읽지 않는다.

### 3단계 — 문서 읽기 (조건부)

`docs/adr/README.md`는 항상 읽는다. 나머지는 변경 범위에 해당할 때만 읽는다.

| 조건 | 읽을 문서 |
|---|---|
| 항상 | `docs/adr/README.md` |
| Entity·DB·migration 변경 | `docs/ERD.md` |
| Controller·DTO·API 경로 변경 | `docs/api.md` |
| 인증·권한·Security 설정 변경 | `docs/security.md` |
| Service·도메인 로직 변경 | `docs/business-rules.md` |

convention과 testing 필수 시나리오는 아래에 인라인되어 있으므로 별도로 읽지 않는다.

### 4단계 — ADR 열린 결정 확인

`docs/adr/README.md`의 Proposed 항목과 변경 기능을 대조한다.  
Proposed 상태 결정에 해당하는 기능이 구현되어 있으면 **P1**으로 즉시 보고하고 리뷰를 중단한다.

### 5단계 — 문서 정합성 대조

읽은 문서와 diff를 항목별로 비교한다.

**ERD** (Entity 변경 시):
- 컬럼명·타입이 `docs/ERD.md`와 일치하는가
- 삭제 정책이 아래 표와 일치하는가

  | Entity | 정책 |
  |---|---|
  | Client, Inquiry, ChatMessage, Review | Soft Delete (`deleted_at`) |
  | Item | Soft Delete (`is_deleted`) |
  | ItemImage, ItemLike, Follow | Hard Delete |
  | Category | `is_active` 비활성화 |
  | ChatMember | `left_at` 기록 |

- 복합키(`ItemLike`, `Follow`, `ChatMember`) Entity가 식별자 클래스를 올바르게 분리했는가
- 카운트 컬럼(`like_count`, `inquiry_count`) 변경이 원본 행 변경과 같은 트랜잭션인가

**API** (Controller·DTO 변경 시):
- 경로·HTTP 메서드가 `docs/api.md`와 일치하는가
- 인증 필요 여부가 "인증 기준" 표와 일치하는가

**보안** (Security 변경 시):
- 소유권 검증(내 정보 수정, 상품 수정, 문의 작성자, 판매자 답변 등)이 Service에 있는가
- 민감정보(password, token, phone)가 응답 DTO나 로그에 노출되지 않는가

**도메인 규칙** (Service·Entity 변경 시):
- `docs/business-rules.md`의 규칙이 구현에 반영되었는가
- Soft Delete 대상이 일반 조회에서 제외되는가

### 6단계 — 컨벤션 체크

diff를 기준으로 아래 항목을 확인한다. 별도 파일 읽기 없이 이 목록으로 판단한다.

| 항목 | 기준 |
|---|---|
| 계층 분리 | Controller에 비즈니스 로직 없음 |
| 주입 방식 | 생성자 주입만 사용 (필드·setter 주입 없음) |
| DTO 분리 | Request DTO와 Response DTO 분리 |
| Entity 노출 | Entity를 API 응답으로 직접 반환 안 함 |
| Fetch 전략 | 연관관계 fetch 기본 LAZY |
| 트랜잭션 경계 | Service에만 선언, 조회는 readOnly = true |
| 예외 처리 | 도메인 예외 → GlobalExceptionHandler에서 HTTP 변환 |
| Lombok | Entity에 @Data 없음 |
| 네이밍 | 클래스 PascalCase, 필드 camelCase, DB snake_case |

### 7단계 — 테스트 시나리오 커버리지

변경된 도메인과 관련된 아래 필수 시나리오가 테스트 파일에 존재하는지 확인한다.  
별도 파일 읽기 없이 이 목록으로 판단한다.

| 도메인 | 필수 시나리오 |
|---|---|
| auth / client | 중복 이메일 회원가입 차단 |
| client | 탈퇴 회원 일반 조회 제외 |
| category | 비활성 카테고리 일반 목록 제외 |
| item | 임시저장/판매중 상품 구분, 판매자 외 수정 차단 |
| itemLike | 좋아요 중복 차단, like_count 정합성 |
| inquiry | 작성자 외 수정·삭제 차단, 판매자 외 답변 차단 |
| follow | 자기 자신 팔로우 차단 |
| chat | 참여자 외 메시지 조회 차단 |
| review | 작성자 외 수정·삭제 차단 |
| auction | 마감된 경매 입찰 차단, 현재 입찰가 이하 입찰 차단 |

정상·실패·권한·경계값이 모두 있는지 확인한다.

### 8단계 — 테스트 실행

```bash
./gradlew test
```

실패한 테스트가 있으면 클래스명#메서드명을 명시한다.  
실행 불가 시 이유를 명시하고 미검증으로 표기한다.

## 우선순위

| 등급 | 내용 |
|---|---|
| P1 | Proposed ADR에 해당하는 기능 구현 |
| P1 | 데이터 손실, 권한 우회, 인증 실패 |
| P1 | ERD 정합성 위반 (컬럼명, 삭제 정책, 복합키) |
| P1 | API 정합성 위반 (경로, 인증 기준) |
| P2 | 컨벤션 위반 |
| P2 | 트랜잭션 경계 누락, 카운트 컬럼 정합성 |
| P2 | 도메인 규칙 위반, Soft Delete 미적용 |
| P2 | 경매 동시성 문제, WebSocket 참여자 검증 누락 |
| P3 | 필수 시나리오 테스트 누락 |
| P3 | 테스트 실패 |

## 출력 형식

### 문제가 있을 때

```
## 리뷰 결과

### 발견 사항

| 등급 | 위치 | 문제 | 제안 |
|---|---|---|---|
| P1 | `path:line` | 내용 | 내용 |
| P2 | `path:line` | 내용 | 내용 |

### 체크 결과

| 항목 | 상태 | 비고 |
|---|---|---|
| ADR 열린 결정 | ✅ 해당 없음 | |
| ERD 정합성 | ❌ 불일치 | Client.deleted_at 누락 |
| API 정합성 | ✅ 일치 | |
| 보안·권한 | ✅ 이상 없음 | |
| 도메인 규칙 | ✅ 이상 없음 | |
| 컨벤션 | ❌ 위반 | ClientController:34 필드 주입 |

### 테스트 시나리오

| 시나리오 | 커버 |
|---|---|
| 중복 이메일 회원가입 차단 | ✅ |
| 탈퇴 회원 일반 조회 제외 | ❌ |

### 테스트 실행

✅ 전체 통과 (12건)
```

### 문제가 없을 때

```
## 리뷰 결과

치명적인 문제는 발견하지 못했습니다.

| 항목 | 상태 |
|---|---|
| ADR 열린 결정 | ✅ 해당 없음 |
| ERD 정합성 | ✅ 일치 |
| API 정합성 | ✅ 일치 |
| 보안·권한 | ✅ 이상 없음 |
| 도메인 규칙 | ✅ 이상 없음 |
| 컨벤션 | ✅ 이상 없음 |
| 테스트 시나리오 | ✅ 전체 커버 |
| 테스트 실행 | ✅ 전체 통과 (N건) |

남은 위험: 없음
```

## 주의

- 리뷰 요청에서는 명시적 수정 요청 없이 코드를 변경하지 않는다.
- diff만으로 확인할 수 없는 사항은 비고에 `추가 문맥 필요`로 표시한다.
- 테스트를 실행하지 못했으면 이유와 미검증 범위를 명시한다.

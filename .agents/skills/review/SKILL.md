---
name: review
description: |
  현재 브랜치의 변경사항을 기준 브랜치와 비교해 코드 리뷰를 수행한다.
  버그, 회귀, 보안, 권한, 테스트 누락을 우선 확인한다.
  사용자가 "리뷰", "코드리뷰", "/review", "/rv"를 요청할 때 사용한다.
---

# review

## 목적

병합 전 변경사항에서 실제 문제가 될 수 있는 버그, 회귀, 보안 위험, 테스트 누락을 찾는다.

## 입력

명령 형식:

```text
/review base=<branch>
/rv base=<branch>
```

기본값:

- `base`가 없으면 `dev`를 우선 사용한다.
- `dev`가 없으면 `main`을 사용한다.

## 절차

1. 작업 상태를 확인한다.

```bash
git status --short --branch
git branch --show-current
```

2. 변경 범위를 확인한다.

```bash
git diff --name-only <base>...HEAD
git diff --stat <base>...HEAD
git diff --check <base>...HEAD
```

3. 변경된 파일별 diff만 읽는다.

```bash
git diff --unified=10 <base>...HEAD -- <file>
```

4. 필요한 경우 관련 문서만 읽는다.

- Entity 변경: `docs/ERD.md`
- API 변경: `docs/api.md`
- 인증·권한 변경: `docs/security.md`
- 도메인 규칙 변경: `docs/business-rules.md`
- 테스트 기준 확인: `docs/testing.md`

## 리뷰 우선순위

1. 데이터 손실, 권한 우회, 인증 실패
2. Entity 관계, 복합키, Soft Delete 누락
3. Controller에 들어간 비즈니스 로직
4. Entity 직접 응답
5. 트랜잭션 경계 누락
6. 카운트 컬럼 정합성 문제
7. 경매 입찰 동시성 문제
8. WebSocket 참여자 검증 누락
9. 테스트 누락

## 출력 형식

문제가 있으면 발견 사항을 먼저 쓴다.

```markdown
## 발견 사항

- [P1] 제목
  - 위치: `path:line`
  - 문제:
  - 영향:
  - 제안:

## 질문

- 없으면 생략

## 테스트

- 실행한 테스트 또는 미실행 이유
```

문제가 없으면 다음 형식으로 짧게 답한다.

```markdown
리뷰 결과, 치명적인 문제는 발견하지 못했습니다.

남은 위험:
- 없으면 "없음"으로 적는다.

테스트:
- 실행한 테스트 또는 미실행 이유를 적는다.
```

## 주의

- 리뷰 요청에서는 명시적 수정 요청 없이 코드를 변경하지 않는다.
- 발견 사항은 파일과 줄 번호를 포함한다.
- 확실하지 않은 내용은 질문 또는 위험 요소로 분리한다.
- 스타일 취향보다 실제 버그와 회귀를 우선한다.

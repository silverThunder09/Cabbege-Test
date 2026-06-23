# 이슈 기반 기능 구현 흐름

## 목표

이슈에 적힌 범위만 구현하고, 테스트와 리뷰를 통과한 뒤 PR을 만든다.

## 이슈 생성

사용자가 `{기능명} 기능 구현 이슈 만들어줘`라고 요청하면:

1. `git status --short --branch`로 현재 상태를 확인한다.
2. 같은 제목의 열린 이슈가 있는지 `gh issue list`로 확인한다.
3. `.github/ISSUE_TEMPLATE/feature.yml`의 항목에 맞춰 이슈를 만든다.
4. 이슈 본문에는 구현 브랜치명을 `feature/{issueNumber}-{topic}`으로 적는다.
5. 인증 방식, Redis, 외부 연동, DB 구조, 아키텍처 결정이 관련되면 Notes에 `docs/adr/README.md`를 반드시 포함한다.

이슈 본문에는 아래 항목만 둔다.

- Goal
- Scope
- Acceptance Criteria
- Notes

## 구현 시작

사용자가 이슈를 토대로 구현을 요청하면:

1. 이슈 본문만 먼저 읽는다.
2. 이슈 Notes에 적힌 관련 문서만 읽는다.
3. Notes에 `docs/adr/README.md`가 있거나 인증 방식, Redis, 외부 연동, DB 구조, 아키텍처 결정이 관련되면 ADR을 반드시 확인한다.
4. 개발 기준 브랜치(`develop` 또는 `dev`)에서 `feature/{issueNumber}-{topic}` 브랜치를 만든다.
5. 이슈의 Scope와 Acceptance Criteria에 있는 항목만 구현한다.

## 범위 제한

- 이슈에 없는 기능은 구현하지 않는다.
- 공통 예외 핸들러, 공통 응답 포맷, 인증 필터, 새 의존성, 문서 대량 수정이 필요하면 먼저 사용자에게 알린다.
- 기능이 컴파일되기 위해 필요한 최소 기반 코드만 허용한다.
- 허용된 기반 코드도 기능 커밋과 분리한다.
- 범위 밖 기반 변경이 승인되지 않으면 stash 또는 별도 브랜치로 분리하고 기능 PR에 포함하지 않는다.

예시:

```text
feat(auth): implement signup endpoint
test(auth): add signup api tests
chore(build): add flyway boot integration
```

## 문서 수정 기준

기능 구현마다 문서를 자동으로 추가하지 않는다.

문서는 아래 경우에만 수정한다.

- API 경로, 메서드, 요청·응답 DTO, 오류 계약이 바뀜
- DB 스키마, 인덱스, 관계, 영속 enum이 바뀜
- 인증, 권한, 민감정보 정책이 바뀜
- 도메인 상태 전이, 삭제 정책, 복합키 규칙이 바뀜
- ADR의 열린 결정이 확정됨

이슈 내용을 docs에 반복해서 옮기지 않는다.

## 테스트

- 테스트 메서드명은 한글로 작성한다.
- 한 테스트는 한 기대 동작만 검증한다.
- 정상, 실패, 경계값, 권한 시나리오 중 이슈 Acceptance Criteria에 해당하는 것만 우선 작성한다.

예시:

```java
@Test
void 회원가입_성공시_비밀번호를_제외한_회원정보를_반환한다() {
}

@Test
void 이미_가입된_이메일이면_회원가입에_실패한다() {
}
```

## 리뷰와 PR

1. 구현 후 `/review base=<develop|dev>`를 실행한다.
2. 리뷰에서 P1/P2 문제가 있으면 PR을 만들지 않는다.
3. 테스트가 통과하면 `$git-pr create-pr base=<develop|dev>` 흐름으로 PR 본문을 만든다.
4. PR 본문에는 `Closes #{issueNumber}`를 포함한다.

## 토큰 절약

- 이슈 생성 시 전체 docs를 읽지 않는다.
- 구현 시작 시 이슈 본문과 Notes에 적힌 관련 문서의 관련 구간만 읽는다.
- ADR은 Notes에 있거나 기술 결정과 관련된 기능일 때 반드시 읽는다.
- `docs/README.md`는 어떤 문서가 필요한지 모를 때만 인덱스로 사용한다.
- 리뷰는 diff 기준으로만 수행한다.
- 변경되지 않은 소스 파일 전체를 읽지 않는다.

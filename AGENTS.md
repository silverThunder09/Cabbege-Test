# AGENTS.md

* 코드, 설정, 문서, Git 작업 전 `git status --short --branch`로 브랜치와 작업 트리를 확인한다.
* 사용자 변경을 덮어쓰거나 되돌리지 않는다.
* 사용자 승인 없이 커밋, push, PR 생성, 병합, 배포를 수행하지 않는다.
* 브랜치 생성·커밋·PR·기능 구현은 `docs/workflow.md` 흐름을 따른다.
* 관련 없는 파일, 대규모 포맷팅, 리팩터링을 함께 수행하지 않는다.
* 새 의존성, 환경 변수, 외부 서비스 추가 시 필요성과 영향 범위를 설명한다.
* 스킬 파일(.agents/skills/)은 미리 읽지 않는다. 스킬 호출 시 자동 로드된다.

## 프로젝트 기준

* 열린 기술 결정 확인이 필요한 경우에만 `docs/adr/README.md`를 읽는다. Proposed 상태에 해당하면 구현하지 않고 사용자에게 알린다.
* ADR 결정 확정 시 `docs/adr/README.md` 열린 결정 표에서 해당 행을 제거하고 `docs/adr/ADR-{번호}.md` 파일을 만든다.
* 상세 설계가 필요할 때만 `docs/README.md`에서 관련 문서를 찾아 읽는다.
* PR·이슈 초안은 `$git-pr <draft-pr|create-pr|issue> base=<branch>` 흐름을 사용한다.
* 작업 트리 리뷰는 `/review`를 사용한다.
* API prefix는 `/api`, WebSocket 연결 경로는 `/ws/chat`을 사용한다.
* DB 스키마, 관계, 인덱스, 영속 enum 변경 시 `docs/ERD.md`를 갱신한다.
* 외부 API의 경로, HTTP 메서드, 요청·응답 DTO, 오류 계약 변경 시 `docs/api.md`를 갱신한다.
* 공개 API 추가·변경 시 `SecurityConfig`의 `permitAll` 설정과 `docs/api.md`의 인증 기준 표를 함께 갱신한다.
* 여러 세션에 걸쳐 이어질 작업만 `docs/plans/{topic}.md`에 Goal, Decisions, Current Status, Next Step을 기록한다.

## 코드 원칙

* 구현 규칙은 필요한 경우 `docs/convention.md`를 따른다.
* 인증, 권한, 민감정보 변경은 `docs/security.md`를 확인하고 관련 테스트를 실행한다.
* 도메인 규칙, 삭제 정책, 복합키, 상태 전이는 필요한 경우 `docs/business-rules.md`와 `docs/ERD.md`를 따른다.
* 테스트를 실행하지 못했으면 이유와 미검증 범위를 최종 답변에 명시한다.

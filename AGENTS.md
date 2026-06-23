# AGENTS.md

- 한국어 존댓말을 사용한다.
- 항상 결론부터 짧게 답한다.
- 질문 범위를 벗어나지 않는다.
- 긴 로그와 파일은 요약하고 핵심만 보여준다.
- 작업 전 `git status --short --branch`로 브랜치와 작업 트리를 확인한다.
- 사용자 변경을 덮어쓰거나 되돌리지 않는다.
- 사용자 승인 없이 커밋, push, PR, 병합, 배포를 수행하지 않는다.
- 사용자가 브랜치 생성을 요청하면 `codex/` prefix를 기본으로 사용한다.

## 프로젝트 기준

- 상세 설계는 `docs/README.md`에서 필요한 문서만 찾아 읽는다.
- PR·이슈 초안은 `$git-pr <review|draft-pr|create-pr|issue> base=<branch>` 흐름을 사용한다.
- 코드 리뷰는 `/review` 또는 `/rv` 흐름을 사용한다.
- API prefix는 `/api`를 사용한다.
- WebSocket 연결 경로는 `/ws/chat`을 사용한다.
- Entity와 테이블 설계 변경 시 `docs/ERD.md`를 먼저 갱신한다.
- API 경로, 요청, 응답, 상태 코드 변경 시 `docs/api.md`를 함께 갱신한다.
- 장기 작업은 `docs/plans/{topic}.md`에 Goal, Decisions, Current Status, Next Step만 남긴다.

## 코드 원칙

- 기본 구조는 `Controller -> Service -> Repository -> DB`를 따른다.
- Controller는 요청 검증, 인증 사용자 전달, DTO 변환만 담당한다.
- 비즈니스 규칙은 Service 또는 Domain 메서드에 둔다.
- Entity를 API 응답으로 직접 반환하지 않는다.
- Request DTO와 Response DTO를 분리한다.
- 생성자 주입만 사용한다.
- JPA 연관관계는 필요한 방향만 매핑하고 기본은 LAZY로 둔다.
- Soft Delete 대상은 일반 조회에서 제외한다.
- 리뷰 요청에서는 명시적 수정 요청 없이 코드를 변경하지 않는다.

## 도메인 기준

- 핵심 도메인은 Client, Category, Item, ItemImage, ItemLike, Inquiry, Follow, ChatRoom, ChatMember, ChatMessage, Review, AuctionStatus다.
- Client 삭제는 `deleted_at`, Item 삭제는 `is_deleted`, Inquiry/Review/ChatMessage 삭제는 `deleted_at` 기준으로 처리한다.
- ItemLike, Follow, ChatMember는 복합키를 사용한다.
- AuctionStatus는 Item과 1:1이며 `item_id`를 PK/FK로 사용한다.

## 응답 형식

- 수정 시 `수정 전 / 수정 후 / 변경 이유 / 확인 방법`을 짧게 제공한다.
- 가능한 경우 최종 답변 끝에 토큰 사용량을 표시한다.
- 정확한 수치를 확인할 수 없으면 `토큰 사용량: 현재 환경에서 확인 불가`라고 적는다.

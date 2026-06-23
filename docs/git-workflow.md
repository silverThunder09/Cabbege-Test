# Git 작업 흐름

## main

- main은 항상 빌드와 테스트가 통과해야 한다.
- 직접 작업보다 작은 브랜치와 PR을 사용한다.
- 사용하지 않는 의존성을 main에 미리 추가하지 않는다.

## 브랜치

```text
feat/{issue-number}-{topic}
fix/{issue-number}-{topic}
refactor/{issue-number}-{topic}
chore/{topic}
docs/{topic}
```

- 다른 미병합 기능 브랜치에 의존하지 않는다.
- 공통 기반 변경은 작은 `chore/*` PR로 먼저 병합한다.

## Commit

- 하나의 커밋은 하나의 논리적 변경을 담는다.
- 생성 파일, 비밀정보, IDE 개인 설정을 포함하지 않는다.
- 권장 prefix: `feat`, `fix`, `refactor`, `test`, `docs`, `chore`.

## PR

- 목적, 변경 내용, API·DB 영향, 테스트 결과, 위험과 롤백을 작성한다.
- 기능 의존성은 사용하는 코드와 같은 PR에 포함한다.
- API, ERD, 비즈니스 규칙 변경 시 docs를 함께 수정한다.
- 리뷰 전 전체 diff와 테스트 결과를 스스로 확인한다.
- PR·리뷰·이슈 초안은 `.agents/skills/git-pr/SKILL.md`를 따른다.

## Git Hooks

- 로컬 훅 파일은 `.githooks/`에 둔다.
- 최초 1회 `git config core.hooksPath .githooks`로 연결한다.
- pre-commit은 빠른 컴파일 검증을 담당한다.
- pre-push는 전체 테스트 검증을 담당한다.
- 훅과 GitHub 템플릿 셋업은 `.agents/skills/git-setup/SKILL.md`를 따른다.

## Review 우선순위

1. 요구사항과 범위
2. 인증과 소유권
3. 상태 전이와 금액
4. 트랜잭션, 락, 멱등성
5. SQL, 인덱스, N+1, 페이징
6. Soft Delete와 보존
7. 결제와 웹훅
8. 계층과 DTO
9. 예외와 민감정보
10. 테스트 누락

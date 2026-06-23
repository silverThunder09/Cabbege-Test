---
name: git-pr
description: 현재 브랜치 변경사항을 좁은 diff 기준으로 리뷰하고, PR 본문 또는 GitHub Issue 초안을 작성한다.
---

# git-pr

사용 목적:

- `$git-pr review base=develop`
- `$git-pr draft-pr base=develop`
- `$git-pr create-pr base=develop`
- `$git-pr issue type=feature`

## 명령 구분

- `review`는 코드 리뷰만 수행하고 PR 본문 작성, PR 생성, Issue 생성을 하지 않는다.
- `draft-pr`는 PR 본문 초안만 작성하고 PR을 생성하지 않는다.
- `create-pr`는 사용자가 명시적으로 PR 생성을 요청한 경우에만 수행한다.
- `issue`는 Issue 초안만 작성하고, 실제 생성은 별도 승인을 받은 경우에만 수행한다.

## 먼저 읽기

1. `AGENTS.md`
2. `docs/git-workflow.md`
3. 변경 파일이 특정 도메인 규칙을 필요로 할 때만 관련 docs

## Diff 토큰 예산

먼저 좁게 확인한다:

```bash
git merge-base <base> HEAD
git diff --name-only <merge-base>...HEAD
git diff --stat <merge-base>...HEAD
git diff --check <merge-base>...HEAD
```

그다음 변경 파일만 확인한다:

```bash
git diff --unified=10 <merge-base>...HEAD -- <changed-file>
```

전체 파일 읽기와 저장소 전체 `rg` 검색은 피한다.
필요하면 diff에 나온 클래스명 또는 메서드명만 제한 검색한다.

## 리뷰

- 발견사항을 먼저 말하고 심각도 `[P1]`, `[P2]`, `[P3]`를 붙인다.
- 구체적 위험, 파일 위치, 수정 방향을 함께 적는다.
- 사용자가 수정을 요청하지 않으면 코드를 변경하지 않는다.

## PR / Issue

- 목적을 한 문장으로 요약한다.
- 사용자 영향, API/DB 영향, 내부 변경을 분리한다.
- 실제 실행한 테스트만 적는다.
- 위험, 롤백, 문서 영향을 포함한다.
- 명시적 승인 없이 PR 또는 Issue를 생성하지 않는다.

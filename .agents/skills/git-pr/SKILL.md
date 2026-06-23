---
name: git-pr
description: |
  현재 브랜치의 변경사항을 기준 브랜치와 비교해 PR 본문, GitHub Issue 초안,
  또는 PR 생성 전 점검 결과를 작성한다. 사용자가 "PR 작성", "PR 본문",
  "이슈 초안", "$git-pr"를 요청할 때 사용한다.
---

# git-pr

## 목적

현재 브랜치의 변경사항을 좁은 diff 기준으로 파악하고, 바로 PR에 붙일 수 있는 본문 또는 이슈 초안을 만든다.

## 입력

명령 형식:

```text
$git-pr <review|draft-pr|create-pr|issue> base=<branch>
```

기본값:

- `base`가 없으면 `dev`를 우선 사용한다.
- `dev`가 없으면 `main`을 사용한다.
- mode가 없으면 `draft-pr`로 처리한다.

## 절차

1. 작업 상태를 확인한다.

```bash
git status --short --branch
git branch --show-current
```

2. 기준 브랜치가 있는지 확인한다.

```bash
git rev-parse --verify <base>
```

3. 변경 범위를 확인한다.

```bash
git diff --name-only <base>...HEAD
git diff --stat <base>...HEAD
git diff --check <base>...HEAD
```

4. 변경 파일별 diff만 확인한다.

```bash
git diff --unified=10 <base>...HEAD -- <file>
```

5. 산출물을 작성한다.

## mode별 산출물

### review

PR 생성 전 점검 결과를 작성한다.

형식:

```markdown
## 점검 결과

- 문제 없음 또는 발견 사항

## 위험 요소

- 위험 요소가 없으면 "없음"

## 테스트

- 실행한 명령
- 실행하지 못한 경우 이유
```

### draft-pr

PR 본문 초안을 작성한다.

형식:

```markdown
## 변경 요약

- 변경 내용을 적는다.

## 변경 이유

- 변경 이유를 적는다.

## 주요 변경 파일

- 주요 파일과 역할을 적는다.

## 테스트

- [ ] 실행한 테스트를 적는다.

## 확인 필요

- 리뷰어가 확인할 내용을 적는다.
```

### create-pr

PR 본문 초안을 만든 뒤 사용자에게 PR 생성 승인을 요청한다.

- 사용자 승인 없이 `gh pr create`, push를 실행하지 않는다.
- 승인 전에는 제목, base, head, 본문을 먼저 보여준다.

### issue

GitHub Issue 초안을 작성한다.

형식:

```markdown
## 배경

## 작업 범위

## 완료 조건

## 참고 문서
```

## 주의

- 변경되지 않은 소스 파일 전체를 읽지 않는다.
- 긴 diff는 파일별 핵심만 요약한다.
- 사용자 변경을 되돌리지 않는다.
- 커밋, push, PR 생성은 사용자 승인 후에만 수행한다.

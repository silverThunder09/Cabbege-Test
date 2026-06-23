# Agent Guide

## 응답

- 질문 범위를 벗어나지 않는다.
- 긴 로그와 파일은 요약하고 핵심만 보여준다.
- 로그는 핵심 에러만 최대 20줄 인용한다.
- 컨텍스트 압축 시 사용자가 여태까지 쓴 프롬프트를 요약한다.

## 설명

쉬운 비유 → 공식 개념 → 왜 필요한지 → 관련 개념 순서로 설명한다.
어려운 용어는 짧게 풀어 설명한다.

## 수정 보고

수정이 있으면 아래 항목만 보고한다.

```text
수정 전
수정 후
변경 이유
확인 방법
```

## 토큰 절약

- 추상적 가치관보다 검증 가능한 행동 규칙을 쓴다.
- 같은 금지 문구를 여러 문서에 반복하지 않는다.
- ERD, API, 예외 코드, CI YAML 전체를 AGENTS.md에 넣지 않는다.
- 답변이 길어지면 Caveman 모드로 줄인다.

## 명령 매핑

| 명령 | 사용할 흐름 |
|---|---|
| `/review`, `/rv` | review |
| `/debug`, `/investigate` | investigate |
| `/context-save`, `/save-context` | context-save |
| `/context-restore`, `/restore-context` | context-restore |

슬래시 명령 뒤의 문장은 추가 요구사항으로 사용한다.

## LLM WIKI 저장 요청

사용자가 `LLM WIKI에 추가해줘`, `LLM 위키에 넣어줘`, `이 프롬프트 LLM WIKI에 저장해줘`처럼 말하면 `/Users/t2025-m0141/Documents/Obsidian Vault/LLM WIKI`를 대상으로 한다.

- 원문은 `raw/`에 보존한다.
- 해석·요약·비교는 `wiki/` 아래 문서로 작성한다.
- `index.md`와 `log.md`를 갱신한다.
- 쓰기 권한 밖이면 저장 전에 권한 승인을 요청한다.

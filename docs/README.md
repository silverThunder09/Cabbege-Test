# 배추마켓 문서 인덱스

| 문서 | 책임 |
|---|---|
| [product-overview.md](product-overview.md) | 서비스 목표, 사용자, 기능 범위 |
| [architecture.md](architecture.md) | 기술 스택, 계층, 패키지 기준 |
| [ERD.md](ERD.md) | Entity, 컬럼, 관계, 삭제 정책 |
| [api.md](api.md) | REST API와 WebSocket 경로 |
| [business-rules.md](business-rules.md) | 도메인 규칙과 상태 기준 |
| [convention.md](convention.md) | Java, Spring, JPA 구현 규칙 |
| [security.md](security.md) | 인증, 권한, 민감정보, WebSocket 보안 |
| [testing.md](testing.md) | 테스트 범위와 필수 시나리오 |
| [git-workflow.md](git-workflow.md) | 브랜치, 커밋, PR 기준 |
| [adr/README.md](adr/README.md) | 중요한 기술 결정 기록 |
| [plans/](plans/) | 장기 작업 진행 상태 |

## 로컬 스킬

| 스킬 | 경로 | 용도 |
|---|---|---|
| git-pr | `.agents/skills/git-pr/SKILL.md` | PR 본문, 이슈 초안, PR 전 점검 |
| review | `.agents/skills/review/SKILL.md` | 변경사항 코드 리뷰 |
| git-setup | `.agents/skills/git-setup/SKILL.md` | Git hook, PR·이슈 템플릿 셋업 |

## 빠른 참조

- API prefix: `/api`
- WebSocket 연결 경로: `/ws/chat`
- 구현 규칙: [convention.md](convention.md)
- 확정 전 작업을 막는 결정: [adr/README.md](adr/README.md)

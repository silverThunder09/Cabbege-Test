# 배추마켓 문서 인덱스

| 문서 | 책임 |
|---|---|
| [agent-guide.md](agent-guide.md) | 에이전트 응답, 명령, LLM WIKI 저장 규칙 |
| [product-overview.md](product-overview.md) | 서비스 목표, 사용자, 기능·제외 범위 |
| [business-rules.md](business-rules.md) | 상품·쿠폰·거래·결제·채팅 상태와 규칙 |
| [architecture.md](architecture.md) | 기술 스택, 계층, 패키지, 외부 시스템 |
| [ERD.md](ERD.md) | Entity, 관계, 키, 삭제·보존 정책 |
| [api.md](api.md) | API 설계·응답·페이징·변경 규칙 |
| [convention.md](convention.md) | Java, JPA, QueryDSL, Flyway, 의존성 규칙 |
| [testing.md](testing.md) | 테스트 범위, 시나리오, 실행 명령 |
| [security.md](security.md) | 인증, 권한, 결제·웹훅, 민감정보 |
| [git-workflow.md](git-workflow.md) | 브랜치, 커밋, PR, 리뷰 정책 |
| [plans/](plans/) | 장기 작업의 목표, 결정, 현재 상태, 다음 단계 |
| [adr/README.md](adr/README.md) | 중요한 기술 결정 기록 |

## 스킬

- PR·리뷰·이슈 초안: `.agents/skills/git-pr/SKILL.md`
- GitHub 템플릿·로컬 훅 셋업: `.agents/skills/git-setup/SKILL.md`

## 문서 관리

- 각 규칙은 책임 문서 한 곳에서만 상세히 설명한다.
- 다른 문서에서는 상세 내용을 복제하지 말고 링크한다.
- 구현 변경으로 규칙이 달라지면 같은 PR에서 문서를 갱신한다.
- 아직 결정되지 않은 내용은 확정된 규칙처럼 작성하지 않고 `TBD`로 표시한다.

# Git 작업 규칙

## 브랜치 구조

- `main`: 제품 배포 브랜치. 직접 push 금지.
- `develop`: 기능을 합치고 테스트하는 공용 개발 브랜치. 직접 push 금지.
- `chore/{name}`: 프로젝트 세팅, 빌드, 도구 설정.
- `feature/{name}`: 기능 작업.
- `docs/{name}`: 문서 작업.
- `refactor/{name}`: 리팩터링.
- `fix/{name}`: 버그 수정.

로컬에 `develop`이 없고 `dev`만 있으면 `dev`를 개발 기준 브랜치로 사용한다.

## 브랜치 생성

- 기능 구현은 개발 기준 브랜치에서 새 작업 브랜치를 만든 뒤 진행한다.
- 이슈 기반 기능 구현 브랜치: `feature/{issueNumber}-{topic}`
- 문서 작업 브랜치: `docs/{topic}`
- 버그 수정 브랜치: `fix/{issueNumber}-{topic}` 또는 `fix/{topic}`
- 브랜치명은 소문자 영문, 숫자, 하이픈을 사용한다.

예시:

```text
feature/6-signup
feature/5-login
docs/git-rules
fix/signup-duplicate-email
```

## 커밋 메시지

형식:

```text
type(scope): subject
```

`type`과 `scope`는 영문 소문자를 사용하고, `subject`는 한글 명사형 단답으로 작성한다.

type:

- `feat`: 기능 구현
- `fix`: 버그 수정
- `test`: 테스트 추가·수정
- `docs`: 문서 수정
- `refactor`: 동작 변경 없는 구조 개선
- `chore`: 빌드, 설정, 의존성, 기타 작업

원칙:

- 한 커밋에는 한 목적만 담는다.
- subject는 `구현`, `추가`, `수정`, `정리`, `삭제`, `분리`처럼 짧은 명사형으로 작성한다.
- PR 제목 tag와 커밋 type은 같은 의미를 사용한다.
- 이슈 범위 밖 작업은 같은 커밋에 넣지 않는다.
- 공통 핸들러, 보안 설정, 새 의존성처럼 기능 구현을 위해 필요한 기반 변경은 별도 커밋으로 분리한다.
- 이슈 범위 밖 기반 작업이 필요하면 먼저 사용자에게 알리고 별도 이슈 또는 별도 커밋으로 처리한다.
- 기능 이슈에 명시되지 않은 공통 응답 코드, 전역 예외 처리, 설정 변경은 승인 전 커밋하지 않는다.

예시:

```text
feat(auth): 회원가입 API 구현
test(auth): 회원가입 API 테스트 추가
docs(auth): 회원가입 API 계약 정리
chore(build): Flyway 설정 추가
```

## PR

- 사용자 승인 없이 PR을 만들지 않는다.
- PR base는 개발 기준 브랜치(`develop` 또는 `dev`)로 한다.
- PR 제목은 `[type] 제목` 형식으로 작성한다.
- PR 제목 tag는 `[feat]`, `[fix]`, `[refactor]`, `[docs]`, `[test]`, `[chore]` 중 하나만 사용한다.
- PR 제목은 한글로 작성한다.
- PR 본문에 관련 이슈를 `Closes #{issueNumber}`로 연결한다.
- PR에는 구현 요약, 테스트 결과, 리뷰 결과, 범위 밖 변경 여부를 적는다.
- 문서 변경이 불필요하면 PR 체크리스트에서 "문서 변경 불필요"를 선택한다.

예시:

```text
[feat] 1:1 채팅 메시지 전송 기능 구현
[fix] 회원가입 중복 이메일 검증 수정
[docs] 커밋 컨벤션 정리
```

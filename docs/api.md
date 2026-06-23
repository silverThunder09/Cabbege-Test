# API 규칙

## 기본

- prefix: `/api/v1`
- URI는 리소스 중심의 복수 명사를 사용한다.
- Request는 Bean Validation으로 1차 검증한다.
- 목록 API는 Pageable 또는 커서 페이징을 사용한다.
- 내부 예외 메시지와 스택 트레이스를 응답에 노출하지 않는다.

## API 그룹

| 그룹 | 기본 경로 |
|---|---|
| 인증 | `/api/v1/auth` |
| 회원 | `/api/v1/clients` |
| 카테고리 | `/api/v1/categories` |
| 상품 | `/api/v1/items` |
| 문의 | `/api/v1/inquiries` |
| 채팅 | `/api/v1/chat-rooms` |
| 쿠폰 | `/api/v1/coupons` |
| 거래 | `/api/v1/trades` |
| 결제 | `/api/v1/payments` |
| 후기 | `/api/v1/reviews` |
| PortOne 웹훅 | `/api/v1/webhooks/portone` |

## HTTP

- GET: 조회
- POST: 생성 또는 명시적 행위
- PATCH: 부분 수정과 상태 변경
- DELETE: 삭제 또는 관계 해제
- 생성 성공은 201, 일반 성공은 200 또는 204를 사용한다.

## 페이징

- 상품·후기·문의: 페이지 기반 페이징
- 채팅 메시지: `cursor`, `size` 기반 커서 페이징
- 최대 size를 제한한다.

## 인증과 권한

- 공개: 상품 목록·상세, 검색, 판매자 공개 프로필
- 인증 필요: 등록, 관심, 문의, 채팅, 쿠폰, 거래, 결제, 후기
- 관리자: 카테고리·쿠폰 정책·회원 상태 관리

## 변경 규칙

- URI, 요청·응답, 상태 코드, 오류 코드를 바꾸면 이 문서를 갱신한다.
- 하위 호환이 깨지는 변경은 별도 버전 또는 명시적 마이그레이션 계획을 둔다.
- 실제 Endpoint 구현이 시작되면 각 API의 요청·응답 예시와 오류 코드를 이 문서에 추가한다.

# 보안 정책

## 인증

- 비밀번호는 Spring Security `PasswordEncoder`로 단방향 해시한다.
- Access Token과 Refresh Token의 수명과 책임을 분리한다.
- 로그아웃·재발급을 위해 Refresh Token은 Redis 저장을 고려한다.

## 권한

- 인증 여부와 별도로 리소스 소유권을 Service에서 확인한다.
- 판매자만 자신의 Item, 판매 상태, 문의 답변을 변경한다.
- 채팅 참여자만 방과 메시지에 접근한다.
- 거래 당사자만 Trade와 Payment를 조회한다.
- 관리자 API는 Role을 확인한다.

## 결제와 웹훅

- 클라이언트의 결제 성공 값과 금액을 신뢰하지 않는다.
- PortOne 서버 API로 결제 상태와 금액을 검증한다.
- 웹훅 서명 또는 서버 재조회로 진위를 확인한다.
- merchant_uid, pg_payment_id, event_id로 멱등 처리한다.

## 민감정보

- 비밀번호, JWT, 이메일 인증번호, 전화번호, 결제 원문을 로그에 남기지 않는다.
- 비밀키는 환경변수 또는 Secret Store로 주입한다.
- 저장소에 `.env`, 운영 설정, 인증서, 실제 키를 커밋하지 않는다.

## 입력과 파일

- Bean Validation과 비즈니스 검증을 모두 적용한다.
- 업로드 파일의 크기, 확장자, Content-Type, 파일명을 검증한다.
- 사용자 입력을 로그나 쿼리에 안전하지 않게 결합하지 않는다.

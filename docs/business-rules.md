# 비즈니스 규칙

## 상품

- `Item`은 판매 게시글이자 거래 대상 단일 상품이다.
- 상태는 `DRAFT`, `ON_SALE`, `RESERVED`, `SOLD`다.
- 임시저장은 별도 테이블 없이 `DRAFT`로 처리한다.
- 게시 시 제목, 카테고리, 가격, 상품 상태, 설명, 이미지 최소 1장을 검증한다.
- 이미지는 최대 10장이며 대표 이미지는 하나다.

```text
DRAFT -> ON_SALE -> RESERVED -> SOLD
                    -> ON_SALE  결제 실패·취소·만료
```

## 관심상품·팔로우·문의

- ItemLike와 Follow는 복합키로 중복을 차단하고 해제 시 Hard Delete한다.
- Inquiry는 공개 Q&A이고 Chat은 비공개 거래 협의다.
- 판매자만 자신의 상품 문의에 답변할 수 있다.

## 채팅

- 같은 상품·구매자·판매자 조합의 활성 채팅방은 하나만 사용한다.
- Client는 계정이고 ChatMember는 특정 방의 참여 기록이다.
- 참여자만 방 조회, 구독, 메시지 전송이 가능하다.
- 메시지는 ID 기반 커서 페이징을 사용한다.

## 선착순 쿠폰

- 기본 쿠폰: 3만 원 이상 구매 시 3천 원 할인, 100장, 1인 1장, 발급 후 7일.
- Coupon은 정책, ClientCoupon은 회원에게 발급된 쿠폰이다.
- Coupon 상태: `READY`, `ACTIVE`, `EXHAUSTED`, `EXPIRED`.
- ClientCoupon 상태: `ISSUED`, `USED`, `EXPIRED`.
- Redis 원자 연산 또는 합의된 락 전략으로 수량을 제어한다.
- DB `UNIQUE(coupon_id, client_id)`를 최종 중복 방어선으로 사용한다.

## 거래와 결제

- 단일 중고 상품 거래이므로 `Order`가 아닌 `Trade`를 사용한다.
- 거래 방식은 `DIRECT`, `DELIVERY`다.
- 주소, 배송사, 송장번호는 저장하지 않고 채팅에서 협의한다.
- Trade 상태: `PAYMENT_PENDING`, `IN_PROGRESS`, `COMPLETED`, `CANCELED`.
- Payment 상태: `READY`, `PAID`, `FAILED`, `CANCELED`.
- 서버가 상품을 잠그고 `ON_SALE`인지 재확인한 뒤 거래를 생성한다.
- 결제 금액은 서버가 상품 가격과 쿠폰으로 다시 계산한다.
- PortOne 서버 API로 paymentId, 결제 상태, 결제 금액을 확인한다.
- 한 Trade에서 결제 재시도가 가능하므로 Trade와 Payment는 1:N이다.
- 실패·취소·만료 시 Trade, Item, ClientCoupon 상태를 복구한다.
- 완료된 Trade의 구매자만 판매자 후기를 한 번 작성할 수 있다.

## 멱등성과 동시성

- 상품당 활성 Trade는 하나만 허용한다.
- Trade당 성공 Payment는 하나만 허용한다.
- `merchant_uid`, `pg_payment_id`, 웹훅 `event_id`는 유일해야 한다.
- 상품 구매 락과 쿠폰 발급 전략은 각각 ADR로 기록한다.

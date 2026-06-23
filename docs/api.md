# API

## 기본 규칙

- REST API prefix는 `/api`다.
- URI는 리소스 중심으로 작성한다.
- Request는 Bean Validation으로 1차 검증한다.
- Entity를 응답으로 직접 반환하지 않는다.
- 목록 조회는 Pageable 또는 cursor를 사용한다.
- 내부 예외 메시지와 stack trace를 응답에 노출하지 않는다.

## API 목록

| 기능 | 그룹 | Method | Path |
|---|---|---:|---|
| 회원가입 | 인증 | POST | `/api/auth/signup` |
| 로그인 | 인증 | POST | `/api/auth/login` |
| 로그아웃 | 인증 | POST | `/api/auth/logout` |
| 내 정보 조회 | 마이 페이지 | GET | `/api/clients/me` |
| 내 정보 수정 | 마이 페이지 | PATCH | `/api/clients/me` |
| 회원 프로필 조회 | 마이 페이지 | GET | `/api/clients/{clientId}` |
| 카테고리 목록 조회 | 카테고리 | GET | `/api/categories` |
| 상품 등록 | 상품 게시글 | POST | `/api/items` |
| 상품 임시저장 | 상품 게시글 | POST | `/api/items/drafts` |
| 상품 목록 조회 | 상품 게시글 | GET | `/api/items` |
| 상품 상세 조회 | 상품 게시글 | GET | `/api/items/{itemId}` |
| 인기 검색어 조회 | 검색어 | GET | `/api/search/popular` |
| 상품 검색 | 검색어 | GET | `/api/items?keyword={keyword}` |
| 상품 정보 수정 | 상품 게시글 | PATCH | `/api/items/{itemId}` |
| 판매 상태 변경 | 상품 게시글 | PATCH | `/api/items/{itemId}/status` |
| 상품 삭제 | 상품 게시글 | DELETE | `/api/items/{itemId}` |
| 내 판매글 목록 | 마이 페이지 | GET | `/api/clients/me/items` |
| 내 관심목록 조회 | 마이 페이지 | GET | `/api/clients/me/likes` |
| 대표 이미지 설정 | 상품 이미지 | PATCH | `/api/items/{itemId}/images/{imageId}/thumbnail` |
| 상품 이미지 삭제 | 상품 이미지 | DELETE | `/api/items/{itemId}/images/{imageId}` |
| 상품 좋아요 토글 | 좋아요 | POST | `/api/items/{itemId}/likes` |
| 상품 문의 작성 | 문의 | POST | `/api/items/{itemId}/inquiries` |
| 상품 문의 목록 조회 | 문의 | GET | `/api/items/{itemId}/inquiries` |
| 상품 문의 수정 | 문의 | PATCH | `/api/inquiries/{inquiryId}` |
| 상품 문의 삭제 | 문의 | DELETE | `/api/inquiries/{inquiryId}` |
| 상품 문의 답변 등록 | 문의 | POST | `/api/inquiries/{inquiryId}/answer` |
| 상품 문의 답변 수정 | 문의 | PATCH | `/api/inquiries/{inquiryId}/answer` |
| 상품 문의 답변 삭제 | 문의 | DELETE | `/api/inquiries/{inquiryId}/answer` |
| 회원 팔로우 | 팔로우 | POST | `/api/clients/{clientId}/follows` |
| 회원 팔로우 취소 | 팔로우 | DELETE | `/api/clients/{clientId}/follows` |
| 내가 팔로우한 회원 목록 | 팔로우 | GET | `/api/clients/me/followings` |
| 나를 팔로우한 회원 목록 | 팔로우 | GET | `/api/clients/me/followers` |
| 채팅방 생성 | 채팅 | POST | `/api/items/{itemId}/chat-rooms` |
| 채팅방 조회 | 채팅 | GET | `/api/chat-rooms` |
| 내 채팅방 목록 | 채팅 | GET | `/api/chat-rooms` |
| 채팅방 상세 조회 | 채팅 | GET | `/api/chat-rooms/{chatRoomId}` |
| 채팅방 나가기 | 채팅 | POST | `/api/chat-rooms/{chatRoomId}/leave` |
| 메시지 목록 조회 | 채팅 | GET | `/api/chat-rooms/{chatRoomId}/messages` |
| 메시지 전송 | 채팅 | POST | `/api/chat-rooms/{chatRoomId}/messages` |
| 메시지 삭제 | 채팅 | DELETE | `/api/chat-messages/{messageId}` |
| 메시지 읽음 처리 | 채팅 | POST | `/api/chat-rooms/{chatRoomId}/read` |
| 회원 리뷰 작성 | 리뷰 | POST | `/api/clients/{clientId}/reviews` |
| 받은 리뷰 목록 조회 | 리뷰 | GET | `/api/clients/{clientId}/reviews` |
| 내가 작성한 리뷰 목록 | 리뷰 | GET | `/api/clients/me/reviews/written` |
| 리뷰 수정 | 리뷰 | PATCH | `/api/reviews/{reviewId}` |
| 리뷰 삭제 | 리뷰 | DELETE | `/api/reviews/{reviewId}` |
| 입찰하기 | 경매 | POST | `/api/items/{itemId}/auction-status/bid` |

## WebSocket

| 기능 | Path |
|---|---|
| WebSocket 연결 | `/ws/chat` |

메시지 publish/subscribe destination은 채팅 구현 시 별도 확정한다.

## 인증 기준

| 공개 API | 인증 필요 API |
|---|---|
| 상품 목록·상세, 카테고리, 회원 공개 프로필 | 내 정보, 상품 등록·수정·삭제, 좋아요, 문의 작성, 팔로우, 채팅, 리뷰, 입찰 |

## 열린 결정

- 로그인 토큰 형식, 채팅 WebSocket destination, 목록 페이징 방식, 이미지 업로드 API 분리는 [adr/README.md](adr/README.md)에서 관리한다.

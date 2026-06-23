# ADR

중요한 기술 결정은 이 폴더에 기록한다.

## 열린 결정

확정 전 구현을 막는 결정을 한곳에서 관리한다. 확정되면 `ADR-{번호}` 문서로 옮기고 상태를 Accepted로 바꾼다.

| 결정 | 상태 | 막는 작업 |
|---|---|---|
| 이미지 저장소(로컬/S3)와 업로드 API 분리 여부 | Proposed | 상품 이미지 업로드 |
| 인기 검색어 집계 저장소(Redis 여부) | Proposed | 인기 검색어 조회 |
| 경매 입찰 동시성 제어 방식 | Proposed | 입찰 |
| WebSocket 확장 구조와 채팅 destination | Proposed | 채팅 실시간 |
| 목록 조회 페이징 방식(Pageable/cursor) | Proposed | 목록 조회 API |

## 확정된 결정

| 결정 | 내용 |
|---|---|
| 인증 방식 | JWT |
| Access Token | API 인증에 사용 |
| Refresh Token 저장소 | Redis |
| 로그인 응답 | `accessToken`, `refreshToken`, `tokenType`, `expiresIn` |
| 토큰 재발급 API | `POST /api/auth/refresh` |
| 로그아웃 | Redis의 Refresh Token 삭제 또는 무효화 |

## 형식

```text
# ADR-{번호}: 제목

## 상태

Proposed | Accepted | Deprecated

## 배경

## 결정

## 결과
```

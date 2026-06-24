# Auth, Client, Search, Like, Follow, Review API 명세

## 문서 대조 결과

| 구분 | 결과 |
| --- | --- |
| 제공 목록 중 `docs/api.md` 누락 | 없음 |
| 경로 표기 차이 | 상품 검색: 제공 목록은 `GET /api/items/`, `docs/api.md`는 `GET /api/items?keyword={keyword}` |
| `docs/api.md`에만 있는 추가 API | 토큰 재발급, 카테고리, 상품 등록·임시저장·목록·상세·수정·상태변경·삭제, 상품 이미지, 문의, 채팅, 경매 |

> 아래 명세는 제공된 템플릿 기준 초안이다. 현재 구현된 API 중 일부는 공통 응답 래퍼(`status`, `data`)가 아직 적용되지 않았을 수 있다.

---

<aside>
📘

</aside>

## 회원가입

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | POST |
| **Path** | `/api/auth/signup` |
| **인증** | 불필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{
  "email": "client@example.com",
  "password": "password123!",
  "nickname": "배추판매자",
  "name": "홍길동",
  "phone": "010-1234-5678"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| email | string | ✅ | 이메일 |
| password | string | ✅ | 비밀번호 |
| nickname | string | ✅ | 닉네임 |
| name | string | ✅ | 이름 |
| phone | string | ❌ | 전화번호 |

## 3. Response

**✅ Success — 201 Created**

```json
{
  "status": 201,
  "data": {
    "id": 1,
    "email": "client@example.com",
    "nickname": "배추판매자",
    "name": "홍길동",
    "role": "USER",
    "status": "ACTIVE",
    "verified": false,
    "createdAt": "2026-06-24T03:00:00"
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 회원 ID |
| email | string | 이메일 |
| nickname | string | 닉네임 |
| name | string | 이름 |
| role | string | 회원 권한 |
| status | string | 회원 상태 |
| verified | boolean | 인증 여부 |
| createdAt | string | 생성 일시 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | VALIDATION_ERROR | 요청값 검증 실패 |
| 409 | DUPLICATE_EMAIL | 이미 가입된 이메일 |

---

## 로그인

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | POST |
| **Path** | `/api/auth/login` |
| **인증** | 불필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{
  "email": "client@example.com",
  "password": "password123!"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| email | string | ✅ | 이메일 |
| password | string | ✅ | 비밀번호 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "accessToken": "jwt-access-token",
    "refreshToken": "jwt-refresh-token",
    "tokenType": "Bearer",
    "expiresIn": 3600
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| accessToken | string | API 인증 토큰 |
| refreshToken | string | 재발급 토큰 |
| tokenType | string | 토큰 타입 |
| expiresIn | number | Access Token 만료 시간 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | VALIDATION_ERROR | 요청값 검증 실패 |
| 401 | INVALID_CREDENTIALS | 이메일 또는 비밀번호 불일치 |
| 403 | CLIENT_DISABLED | 탈퇴 또는 비활성 회원 |

---

## 토큰 재발급

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | POST |
| **Path** | `/api/auth/refresh` |
| **인증** | 불필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{
  "refreshToken": "jwt-refresh-token"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| refreshToken | string | ✅ | Redis에 저장된 Refresh Token과 대조할 토큰 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "accessToken": "new-jwt-access-token",
    "refreshToken": "new-jwt-refresh-token",
    "tokenType": "Bearer",
    "expiresIn": 3600
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| accessToken | string | 새 API 인증 토큰 |
| refreshToken | string | 새 재발급 토큰 |
| tokenType | string | 토큰 타입 |
| expiresIn | number | Access Token 만료 시간 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | VALIDATION_ERROR | 요청값 검증 실패 |
| 401 | INVALID_REFRESH_TOKEN | Refresh Token이 유효하지 않음 |
| 401 | REFRESH_TOKEN_EXPIRED | Refresh Token 만료 |
| 404 | REFRESH_TOKEN_NOT_FOUND | Redis에 저장된 Refresh Token 없음 |

---

## 로그아웃

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | POST |
| **Path** | `/api/auth/logout` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| - | - | - | 요청 Body 없음 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "message": "로그아웃되었습니다."
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| message | string | 처리 결과 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |

---

## 내 정보 조회

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/me` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| - | - | - | 요청 Body 없음 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "id": 1,
    "email": "client@example.com",
    "nickname": "배추판매자",
    "name": "홍길동",
    "phone": "010-1234-5678",
    "profileImageUrl": null,
    "role": "USER",
    "status": "ACTIVE",
    "verified": false,
    "createdAt": "2026-06-24T03:00:00"
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 회원 ID |
| email | string | 이메일 |
| nickname | string | 닉네임 |
| name | string | 이름 |
| phone | string | 전화번호 |
| profileImageUrl | string | 프로필 이미지 URL |
| role | string | 회원 권한 |
| status | string | 회원 상태 |
| verified | boolean | 인증 여부 |
| createdAt | string | 생성 일시 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |
| 404 | CLIENT_NOT_FOUND | 회원 없음 |

---

## 내 정보 수정

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | PATCH |
| **Path** | `/api/clients/me` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{
  "nickname": "새닉네임",
  "name": "홍길동",
  "phone": "010-1111-2222",
  "profileImageUrl": "https://example.com/profile.png"
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| nickname | string | ❌ | 닉네임 |
| name | string | ❌ | 이름 |
| phone | string | ❌ | 전화번호 |
| profileImageUrl | string | ❌ | 프로필 이미지 URL |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "id": 1,
    "email": "client@example.com",
    "nickname": "새닉네임",
    "name": "홍길동",
    "phone": "010-1111-2222",
    "profileImageUrl": "https://example.com/profile.png",
    "updatedAt": "2026-06-24T04:00:00"
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 회원 ID |
| email | string | 이메일 |
| nickname | string | 닉네임 |
| name | string | 이름 |
| phone | string | 전화번호 |
| profileImageUrl | string | 프로필 이미지 URL |
| updatedAt | string | 수정 일시 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | VALIDATION_ERROR | 요청값 검증 실패 |
| 401 | UNAUTHORIZED | 인증 실패 |

---

## 회원 프로필 조회

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/{clientId}` |
| **인증** | 불필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| - | - | - | 요청 Header 없음 |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| clientId | number | ✅ | Path Variable, 회원 ID |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "id": 1,
    "nickname": "배추판매자",
    "profileImageUrl": null
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 회원 ID |
| nickname | string | 닉네임 |
| profileImageUrl | string | 프로필 이미지 URL |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 404 | CLIENT_NOT_FOUND | 회원 없음 |

---

## 인기 검색어 조회

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/search/popular` |
| **인증** | 불필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| - | - | - | 요청 Header 없음 |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| - | - | - | 요청 Body 없음 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "keywords": [
      {
        "keyword": "배추",
        "rank": 1,
        "searchCount": 120
      }
    ]
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| keywords | array | 인기 검색어 목록 |
| keyword | string | 검색어 |
| rank | number | 순위 |
| searchCount | number | 검색 횟수 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 500 | INTERNAL_SERVER_ERROR | 서버 오류 |

---

## 상품 검색

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/items?keyword={keyword}` |
| **인증** | 불필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| - | - | - | 요청 Header 없음 |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| keyword | string | ❌ | Query Parameter, 검색어 |
| page | number | ❌ | 페이지 번호 |
| size | number | ❌ | 페이지 크기 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "items": [
      {
        "id": 1,
        "title": "싱싱한 배추",
        "initialPrice": 12000,
        "tradeStatus": "ON_SALE",
        "likeCount": 0
      }
    ]
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| items | array | 상품 목록 |
| id | number | 상품 ID |
| title | string | 제목 |
| initialPrice | number | 최초 가격 |
| tradeStatus | string | 판매 상태 |
| likeCount | number | 좋아요 수 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | VALIDATION_ERROR | 요청값 검증 실패 |

---

## 내 판매글 목록

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/me/items` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| page | number | ❌ | Query Parameter, 페이지 번호 |
| size | number | ❌ | Query Parameter, 페이지 크기 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "items": []
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| items | array | 내 판매글 목록 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |

---

## 내 관심목록 조회

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/me/likes` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| page | number | ❌ | Query Parameter, 페이지 번호 |
| size | number | ❌ | Query Parameter, 페이지 크기 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "items": []
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| items | array | 관심 상품 목록 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |

---

## 상품 좋아요 토글

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | POST |
| **Path** | `/api/items/{itemId}/likes` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| itemId | number | ✅ | Path Variable, 상품 ID |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "itemId": 1,
    "liked": true,
    "likeCount": 1
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| itemId | number | 상품 ID |
| liked | boolean | 좋아요 여부 |
| likeCount | number | 좋아요 수 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |
| 404 | ITEM_NOT_FOUND | 상품 없음 |

---

## 회원 팔로우

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | POST |
| **Path** | `/api/clients/{clientId}/follows` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| clientId | number | ✅ | Path Variable, 팔로우 대상 회원 ID |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "followingId": 2,
    "followed": true
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| followingId | number | 팔로우 대상 회원 ID |
| followed | boolean | 팔로우 여부 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | SELF_FOLLOW_NOT_ALLOWED | 자기 자신 팔로우 불가 |
| 401 | UNAUTHORIZED | 인증 실패 |
| 404 | CLIENT_NOT_FOUND | 회원 없음 |
| 409 | ALREADY_FOLLOWING | 이미 팔로우 중 |

---

## 회원 팔로우 취소

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | DELETE |
| **Path** | `/api/clients/{clientId}/follows` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| clientId | number | ✅ | Path Variable, 팔로우 취소 대상 회원 ID |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "followingId": 2,
    "followed": false
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| followingId | number | 팔로우 대상 회원 ID |
| followed | boolean | 팔로우 여부 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |
| 404 | FOLLOW_NOT_FOUND | 팔로우 관계 없음 |

---

## 내가 팔로우한 회원 목록

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/me/followings` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| page | number | ❌ | Query Parameter, 페이지 번호 |
| size | number | ❌ | Query Parameter, 페이지 크기 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "clients": []
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| clients | array | 내가 팔로우한 회원 목록 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |

---

## 나를 팔로우한 회원 목록

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/me/followers` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| page | number | ❌ | Query Parameter, 페이지 번호 |
| size | number | ❌ | Query Parameter, 페이지 크기 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "clients": []
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| clients | array | 나를 팔로우한 회원 목록 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |

---

## 회원 리뷰 작성

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | POST |
| **Path** | `/api/clients/{clientId}/reviews` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{
  "rating": 5,
  "content": "친절하고 거래가 빨랐습니다."
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| clientId | number | ✅ | Path Variable, 리뷰 대상 회원 ID |
| rating | number | ✅ | 평점 |
| content | string | ❌ | 리뷰 내용 |

## 3. Response

**✅ Success — 201 Created**

```json
{
  "status": 201,
  "data": {
    "id": 1,
    "reviewerId": 1,
    "revieweeId": 2,
    "rating": 5,
    "content": "친절하고 거래가 빨랐습니다.",
    "createdAt": "2026-06-24T04:00:00"
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 리뷰 ID |
| reviewerId | number | 작성자 ID |
| revieweeId | number | 대상 회원 ID |
| rating | number | 평점 |
| content | string | 리뷰 내용 |
| createdAt | string | 생성 일시 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | VALIDATION_ERROR | 요청값 검증 실패 |
| 401 | UNAUTHORIZED | 인증 실패 |
| 404 | CLIENT_NOT_FOUND | 회원 없음 |

---

## 받은 리뷰 목록 조회

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/{clientId}/reviews` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| clientId | number | ✅ | Path Variable, 리뷰 대상 회원 ID |
| page | number | ❌ | Query Parameter, 페이지 번호 |
| size | number | ❌ | Query Parameter, 페이지 크기 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "reviews": []
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| reviews | array | 받은 리뷰 목록 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |
| 404 | CLIENT_NOT_FOUND | 회원 없음 |

---

## 내가 작성한 리뷰 목록

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | GET |
| **Path** | `/api/clients/me/reviews/written` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| page | number | ❌ | Query Parameter, 페이지 번호 |
| size | number | ❌ | Query Parameter, 페이지 크기 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "reviews": []
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| reviews | array | 내가 작성한 리뷰 목록 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |

---

## 리뷰 수정

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | PATCH |
| **Path** | `/api/reviews/{reviewId}` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |
| Content-Type | application/json | ✅ | 데이터 타입 |

**🔹 Body**

```json
{
  "rating": 4,
  "content": "내용을 수정합니다."
}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| reviewId | number | ✅ | Path Variable, 리뷰 ID |
| rating | number | ❌ | 평점 |
| content | string | ❌ | 리뷰 내용 |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "id": 1,
    "rating": 4,
    "content": "내용을 수정합니다.",
    "updatedAt": "2026-06-24T04:10:00"
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| id | number | 리뷰 ID |
| rating | number | 평점 |
| content | string | 리뷰 내용 |
| updatedAt | string | 수정 일시 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 400 | VALIDATION_ERROR | 요청값 검증 실패 |
| 401 | UNAUTHORIZED | 인증 실패 |
| 403 | FORBIDDEN | 작성자 아님 |
| 404 | REVIEW_NOT_FOUND | 리뷰 없음 |

---

## 리뷰 삭제

## 1. INFO

| 항목 | 내용 |
| --- | --- |
| **Method** | DELETE |
| **Path** | `/api/reviews/{reviewId}` |
| **인증** | 필요 |

## 2. Request

**🔹 Headers**

| Key | Value | 필수 | 설명 |
| --- | --- | --- | --- |
| Authorization | Bearer `{accessToken}` | ✅ | Access Token |

**🔹 Body**

```json
{}
```

| 필드 | 타입 | 필수 | 설명 |
| --- | --- | --- | --- |
| reviewId | number | ✅ | Path Variable, 리뷰 ID |

## 3. Response

**✅ Success — 200 OK**

```json
{
  "status": 200,
  "data": {
    "deleted": true
  }
}
```

| 필드 | 타입 | 설명 |
| --- | --- | --- |
| deleted | boolean | 삭제 여부 |

**⚠️ Error**

| Status | Code | 설명 |
| --- | --- | --- |
| 401 | UNAUTHORIZED | 인증 실패 |
| 403 | FORBIDDEN | 작성자 아님 |
| 404 | REVIEW_NOT_FOUND | 리뷰 없음 |

---

*— END OF DOCUMENTATION —*

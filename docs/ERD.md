# ERD

## Mermaid

```mermaid
erDiagram
    direction LR

    category ||--o{ item : classifies
    itemImage }o--|| item : belongs_to
    item ||--|{ itemLike : receives
    itemLike }o--|| client : added_by
    inquiry }o--|| item : belongs_to
    inquiry }o--|| client : written_by
    item }o--|| client : sold_by

    client ||--o{ follow : follows
    client ||--o{ follow : followed_by

    client ||--o{ chatRoom : creates
    item ||--o{ chatRoom : discussed_in
    chatRoom ||--|{ chatMember : has
    client ||--o{ chatMember : joins
    chatRoom ||--o{ chatMessage : contains
    client ||--o{ chatMessage : sends

    client ||--o{ review : writes
    client ||--o{ review : receives

    item |o--|| auctionStatus : open
    client ||--|| auctionStatus : bids

    inquiry |o--|| inquiry : answers

    client["CLIENT"] {
        bigint id PK
        varchar email UK
        varchar password
        varchar nickname
        varchar name
        varchar phone
        varchar profile_image_url
        varchar role
        varchar status
        boolean is_verified
        datetime created_at
        datetime updated_at
        datetime deleted_at
    }

    category["CATEGORY"] {
        bigint id PK
        bigint parent_id FK
        varchar name UK
        int sort_order
        boolean is_active
        datetime created_at
        datetime updated_at
    }

    item["ITEM"] {
        bigint id PK
        bigint seller_id FK
        bigint category_id FK
        varchar trade_type
        varchar title
        text description
        bigint initial_price
        varchar condition_type
        varchar trade_status
        bigint view_count
        bigint like_count
        bigint inquiry_count
        boolean is_draft
        datetime created_at
        datetime updated_at
        boolean is_deleted
    }

    itemImage["ITEM_IMAGE"] {
        bigint id PK
        bigint item_id FK
        varchar image_url
        int sort_order
        boolean is_thumbnail
        datetime created_at
    }

    itemLike["ITEM_LIKE"] {
        bigint client_id PK, FK
        bigint item_id PK, FK
        datetime created_at
    }

    inquiry["INQUIRY_LOG"] {
        bigint id PK
        bigint item_id FK
        bigint author_id FK
        bigint target_inquiry_id FK
        varchar title
        text description
        varchar status
        datetime created_at
        datetime updated_at
        boolean is_deleted
    }

    follow["FOLLOW"] {
        bigint follower_id PK, FK
        bigint following_id PK, FK
        datetime created_at
    }

    chatRoom["CHAT_ROOM"] {
        bigint id PK
        bigint item_id FK
        bigint created_by FK
        varchar status
        datetime last_message_at
        datetime created_at
        datetime updated_at
    }

    chatMember["CHAT_MEMBER"] {
        bigint chat_room_id PK, FK
        bigint client_id PK, FK
        bigint last_read_message_id FK
        datetime joined_at
        datetime left_at
    }

    chatMessage["CHAT_MESSAGE"] {
        bigint id PK
        bigint chat_room_id FK
        bigint sender_id FK
        varchar message_type
        text content
        varchar image_url
        datetime created_at
        datetime deleted_at
    }

    review["REVIEW"] {
        bigint id PK
        bigint reviewer_id FK
        bigint reviewee_id FK
        int rating
        text content
        datetime created_at
        datetime updated_at
        datetime deleted_at
    }

    auctionStatus["AUCTION_STATUS"] {
        bigint item_id PK, FK
        bigint current_bid
        bigint current_bidder_id FK
        datetime close_date
    }
```

## Entity 목록

| 도메인 | Entity |
|---|---|
| 인증·회원 | Client |
| 카테고리 | Category |
| 상품 | Item, ItemImage, ItemLike, AuctionStatus |
| 문의 | InquiryLog |
| 팔로우 | Follow |
| 채팅 | ChatRoom, ChatMember, ChatMessage |
| 리뷰 | Review |

## 주요 제약

- `client.email`은 유일하다.
- `category.name`은 유일하다.
- `category.parent_id`는 `category.id`를 참조한다.
- `item.seller_id`는 `client.id`를 참조한다.
- `item.category_id`는 `category.id`를 참조한다.
- `item_like` PK는 `(client_id, item_id)`다.
- `inquiry_log.target_inquiry_id`는 답변 대상 `inquiry_log.id`를 참조한다.
- 문의 API의 `contents` 요청 필드는 `inquiry_log.description`에 저장한다.
- `follow` PK는 `(follower_id, following_id)`다.
- `chat_member` PK는 `(chat_room_id, client_id)`다.
- `auction_status.item_id`는 PK이자 `item.id` FK다.
- `auction_status.current_bidder_id`는 현재 최고 입찰자 `client.id`를 참조하며 입찰 전에는 nullable일 수 있다.

## 삭제 정책

| Entity | 정책 |
|---|---|
| Client | Soft Delete, `deleted_at` |
| Item | Soft Delete, `is_deleted` |
| InquiryLog | Soft Delete, `is_deleted` |
| ChatMessage | Soft Delete, `deleted_at` |
| Review | Soft Delete, `deleted_at` |
| ItemImage | Hard Delete |
| ItemLike | Hard Delete |
| Follow | Hard Delete |
| ChatMember | `left_at` 기록 |
| Category | `is_active` 비활성화 |
| AuctionStatus | Item 생명주기와 함께 관리 |

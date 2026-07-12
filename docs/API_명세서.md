# CommercePJT API 명세서

이커머스 어드민 백엔드 프로젝트 API 명세입니다.

**공통 규칙**

- Base URL: `/`  (예: `http://localhost:8080/admins`)
- 요청/응답 형식: JSON
- 인증: 세션 + 쿠키 (`JSESSIONID`, HttpOnly) — 회원가입·로그인 제외 전 API 인증 필요
- 수정은 PATCH로 통일 (부분 수정)
- 날짜 형식: `yyyy-MM-dd'T'HH:mm:ss`

**공통 에러 코드**

| 코드 | 의미 | 예시 |
|------|------|------|
| 400 Bad Request | 형식 오류, 필수값 누락, 비즈니스 규칙 위반 | 이메일 형식 오류, 재고 부족, 상태 순서 위반 |
| 401 Unauthorized | 인증 실패 | 비밀번호 불일치, 미로그인 요청 |
| 403 Forbidden | 인증됐으나 접근 불가 | 승인대기·거부·정지·비활성 계정 로그인 |
| 404 Not Found | 리소스 없음 | 존재하지 않는 ID 조회 |
| 409 Conflict | 현재 데이터와 충돌 | 이메일 중복 |

**상태값 정의**

| 도메인 | 상태 |
|--------|------|
| 관리자/고객 | `PENDING`(승인대기), `ACTIVE`(활성), `INACTIVE`(비활성), `SUSPENDED`(정지), `REJECTED`(거부) ※ 고객은 ACTIVE/INACTIVE/SUSPENDED만 사용 |
| 상품 | `ON_SALE`(판매중), `SOLD_OUT`(품절), `DISCONTINUED`(단종) |
| 주문 | `PREPARING`(준비중), `SHIPPING`(배송중), `DELIVERED`(배송완료), `CANCELED`(취소됨) |

---

## 1. 관리자 회원가입

### 1-1. 회원가입 — `POST /admins/signup`

인증 불필요. 가입 시 상태는 `PENDING`(승인대기)으로 자동 설정.

**Request**

```json
{
  "name": "최정윤",
  "email": "admin@test.com",
  "password": "password123",
  "phone": "010-1234-5678",
  "role": "CS"
}
```

| 필드 | 검증 규칙 |
|------|-----------|
| name | 필수 |
| email | 필수, 이메일 형식, 중복 불가 |
| password | 필수, 최소 8자 (bcrypt 암호화 저장) |
| phone | 필수, `010-XXXX-XXXX` 형식 |
| role | 필수, `SUPER` / `MANAGER` / `CS` 중 하나 |

**Response** `201 Created`

```json
{
  "id": 1,
  "name": "최정윤",
  "email": "admin@test.com",
  "phone": "010-1234-5678",
  "role": "CS",
  "status": "PENDING",
  "createdAt": "2026-07-10T10:00:00"
}
```

**에러**: 형식 오류·필수값 누락(400), 이메일 중복(409)

---

## 2. 관리자 인증

### 2-1. 로그인 — `POST /admins/login`

**Request**

```json
{
  "email": "admin@test.com",
  "password": "password123"
}
```

**Response** `200 OK` + `Set-Cookie: JSESSIONID=...; HttpOnly`

```json
{
  "id": 1,
  "name": "김관리",
  "email": "admin@test.com",
  "role": "CS"
}
```

세션 저장 정보: 관리자 ID, 이메일, 역할 / 세션 유효시간 24시간

**에러**

| 상황                | 코드  |
|-------------------|-----|
| 형식 오류, 필수값 누락     | 400 |
| 이메일 또는 비밀번호 불일치   | 401 |
| 승인대기 / 거부 / 정지 / 비활성 계정 | 403 |

### 2-2. 로그아웃 — `POST /admins/logout`

요청 바디 없음. 서버 세션 무효화.

**Response** `204 No Content`

**에러**: 미로그인 상태에서 로그아웃 시도(404)

---

## 3. 관리자 정보 관리

### 3-1. 관리자 리스트 조회 — `GET /admins`

**Query Parameters**

| 파라미터 | 설명 | 기본값 |
|----------|------|--------|
| keyword | 이름 또는 이메일 검색 | - |
| page | 페이지 번호 | 1 |
| size | 페이지당 개수 | 10 |
| sortBy | `name` / `email` / `createdAt` | createdAt |
| direction | `asc` / `desc` | desc |
| role | 역할 필터 | - |
| status | 상태 필터 | - |

**Response** `200 OK`

**에러**: 없는 ID(404)

```json
{
  "admins": [
    {
      "id": 1,
      "name": "김관리",
      "email": "admin@test.com",
      "phone": "010-1234-5678",
      "role": "CS",
      "status": "ACTIVE",
      "createdAt": "2026-07-01T10:00:00",
      "approvedAt": "2026-07-02T09:00:00"
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "size": 10,
    "totalElements": 23,
    "totalPages": 3
  }
}
```
### 3-2. 관리자 상세 조회 — `GET /admins/{id}`

**Response** `200 OK` — 리스트 항목과 동일 구조 단건

**에러**: 없는 ID(404)

### 3-3. 관리자 정보 수정 — `PATCH /admins/{id}`

**Request**

```json
{
  "name": "김땡땡",
  "email": "new@test.com",
  "phone": "010-9999-8888"
}
```

**Response** `200 OK` — 수정된 name, email, phone

**에러**: 형식 오류(400), 없는 ID(404), 이메일 중복(409)

### 3-4. 관리자 역할 변경 — `PATCH /admins/{id}/role`

**Request** `{ "role": "MANAGER" }` → **Response** `200 OK` `{ "role": "MANAGER" }`

**에러**: 잘못된 역할값(400), 없는 ID(404)

### 3-5. 관리자 상태 변경 — `PATCH /admins/{id}/status`

**Request** `{ "status": "SUSPENDED" }` → **Response** `200 OK` `{ "status": "SUSPENDED" }`

**에러**: 잘못된 상태값(400), 없는 ID(404)

### 3-6. 관리자 삭제 — `DELETE /admins/{id}`

**Response** `204 No Content`

**에러**: 없는 ID(404)

### 3-7. 관리자 승인 — `PATCH /admins/{id}/approve`

요청 바디 없음. `PENDING` → `ACTIVE` 전환, `approvedAt` 자동 기록.

**Response** `200 OK`

```json
{
  "status": "ACTIVE",
  "approvedAt": "2026-07-10T11:00:00"
}
```

**에러**: 승인대기 상태가 아닌 관리자 승인 시도(400), 없는 ID(404)

### 3-8. 관리자 거부 — `PATCH /admins/{id}/reject`

`PENDING` → `REJECTED` 전환, 거부 일시·사유 기록.

**Request** `{ "rejectReason": "정보 불일치" }`

**Response** `200 OK`

```json
{
  "status": "REJECTED",
  "rejectedAt": "2026-07-10T11:00:00",
  "rejectReason": "정보 불일치"
}
```

**에러**: 거부 사유 누락(400), 승인대기 상태가 아닌 경우(400), 없는 ID(404)

### 3-9. 내 프로필 조회 — `GET /admins/me`

세션의 로그인 관리자 기준 조회. 요청 파라미터 없음.

**Response** `200 OK` — name, email, phone

### 3-10. 내 프로필 수정 — `PATCH /admins/me`

**Request/Response**: 3-3과 동일 구조 (대상만 본인)

**에러**: 형식 오류(400), 이메일 중복(409)

### 3-11. 비밀번호 변경 — `PATCH /admins/me/password`

**Request**

```json
{
  "password": "old12345",
  "newPassword": "new12345"
}
```

**Response** `200 OK`

**에러**: 새 비밀번호 8자 미만(400), 현재 비밀번호 불일치(401)

---

## 4. 고객 정보 관리

### 4-1. 고객 리스트 조회 — `GET /customers`

**Query Parameters**

| 파라미터 | 설명 | 기본값 |
|----------|------|--------|
| keyword | 이름 또는 이메일 검색 | - |
| page | 페이지 번호 | 1 |
| size | 페이지당 개수 | 10 |
| sortBy | `name` / `email` / `createdAt` | createdAt |
| direction | `asc` / `desc` | desc |
| status | `ACTIVE` / `INACTIVE` / `SUSPENDED` | - |

**Response** `200 OK`

```json
{
  "customers": [
    {
      "id": 1,
      "name": "홍길동",
      "email": "hong@test.com",
      "phone": "010-1111-2222",
      "status": "ACTIVE",
      "createdAt": "2026-06-15T10:00:00"
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "size": 10,
    "totalElements": 87,
    "totalPages": 9
  }
}
```

### 4-2. 고객 상세 조회 — `GET /customers/{id}`

**Response** `200 OK` — 리스트 항목과 동일 구조 (단건)

**에러**: 없는 ID(404)

### 4-3. 고객 정보 수정 — `PATCH /customers/{id}`

**Request**

```json
{
  "name": "홍길순",
  "email": "hong2@test.com",
  "phone": "010-3333-4444"
}
```

**Response** `200 OK` — 수정된 name, email, phone

**에러**: 형식 오류(400), 없는 ID(404), 이메일 중복(409)

### 4-4. 고객 상태 변경 — `PATCH /customers/{id}/status`

**Request** `{ "status": "SUSPENDED" }` → **Response** `200 OK` `{ "status": "SUSPENDED" }`

**에러**: 잘못된 상태값(400), 없는 ID(404)

### 4-5. 고객 삭제 — `DELETE /customers/{id}`

**Response** `204 No Content`

**에러**: 없는 ID(404)

---

## 5. 상품 정보 관리

### 5-1. 상품 등록 — `POST /products`

등록 관리자 = 세션의 로그인 관리자.

**Request**

```json
{
  "name": "무선 이어폰",
  "category": "전자기기",
  "price": 29900,
  "stock": 100,
  "status": "판매중"
}
```

**Response** `201 Created`

```json
{
  "id": 3,
  "name": "무선 이어폰",
  "category": "전자기기",
  "price": 29900,
  "stock": 100,
  "status": "판매중",
  "createdAt": "2026-07-10T15:00:00",
  "adminName": "김관리"
}
```

**에러**: 형식 오류·필수값 누락·가격/재고 음수(400), 미로그인(401)

### 5-2. 상품 리스트 조회 — `GET /products`

**Query Parameters**

| 파라미터 | 설명 | 기본값 |
|----------|------|--------|
| keyword | 상품명 검색 | - |
| page | 페이지 번호 | 1 |
| size | 페이지당 개수 | 10 |
| sortBy | `price` / `stock` / `createdAt` | createdAt |
| direction | `asc` / `desc` | desc |
| category | 카테고리 필터 | - |
| status | `ON_SALE` / `SOLD_OUT` / `DISCONTINUED` | - |

**Response** `200 OK` — 상품 목록(등록 관리자명 포함) + pageInfo (구조는 4-1과 동일 패턴)

### 5-3. 상품 상세 조회 — `GET /products/{id}`

**Response** `200 OK` — 리스트 항목 + `adminName` 추가

**에러**: 없는 ID(400),조회할 데이터 없음 (404)

### 5-4. 상품 정보 수정 — `PATCH /products/{id}`

수정 가능 필드: 상품명, 카테고리, 가격 (재고·상태는 전용 API 사용)

**Request**

```json
{
  "name": "무선 이어폰 프로",
  "category": "전자기기",
  "price": 39900
}
```

**Response** `200 OK` — 수정된 name, category, price

**에러**: 형식 오류(400), 없는 ID(404)

### 5-5. 상품 재고 변경 — `PATCH /products/{id}/stock`

**Request** `{ "stock": 50 }` → **Response** `200 OK` `{ "stock": 50, "status": "ON_SALE" }`

**자동 전환 정책**

- 변경 후 재고 0 이하 → 상태 `품절`
- 변경 후 재고 1 이상 → 상태 `판매중`
- 상품이 `단종`(단종)이면 재고만 변경, 상태는 유지

**에러**: 재고가 음수가 되는 요청(400), 없는 ID(404)

### 5-6. 상품 상태 변경 — `PATCH /products/{id}/status`

**Request** `{ "status": "DISCONTINUED" }` → **Response** `200 OK` `{ "status": "DISCONTINUED" }`

**에러**: 잘못된 상태값(400), 없는 ID(404)

### 5-7. 상품 삭제 — `DELETE /products/{id}`

**Response** `204 No Content`

**에러**: 없는 ID(404)

---

## 6. 주문 정보 관리

### 6-1. CS 주문 생성 — `POST /orders`

**Request**

```json
{
  "customerId": 1,
  "productId": 3,
  "quantity": 2
}
```

**서버 처리**

- 주문번호 자동 생성 (예: `ORD-20260710-0001`)
- 주문일 자동 생성, 초기 상태 `PREPARING`
- 총 금액 = 주문 당시 상품 가격 × 수량
- 재고 검증 → 차감 → 상품 상태 자동 전환 (하나의 트랜잭션)
- 등록 관리자 = 세션의 로그인 관리자

**Response** `201 Created`

```json
{
  "orderId": 10,
  "orderNumber": "ORD-20260710-0001",
  "customerName": "홍길동",
  "productName": "무선 이어폰",
  "quantity": 2,
  "totalPrice": 59800,
  "status": "PREPARING",
  "orderedAt": "2026-07-10T14:30:00",
  "adminName": "김관리"
}
```

**에러**

| 상황 | 코드 |
|------|------|
| 수량 1 미만 | 400 |
| 단종 상품 주문 | 400 |
| 품절 상품 주문 | 400 |
| 재고 < 주문 수량 | 400 |
| 존재하지 않는 고객/상품 | 404 |

### 6-2. 주문 리스트 조회 — `GET /orders`

**Query Parameters**

| 파라미터 | 설명 | 기본값 |
|----------|------|--------|
| keyword | 주문번호 또는 고객명 검색 | - |
| page | 페이지 번호 | 1 |
| size | 페이지당 개수 | 10 |
| sortBy | `quantity` / `totalPrice` / `orderedAt` | orderedAt |
| direction | `asc` / `desc` | desc |
| status | `PREPARING` / `SHIPPING` / `DELIVERED` / `CANCELED` | - |

**Response** `200 OK`

```json
{
  "orders": [
    {
      "orderId": 10,
      "orderNumber": "ORD-20260710-0001",
      "customerName": "홍길동",
      "productName": "무선 이어폰",
      "quantity": 2,
      "totalPrice": 59800,
      "orderedAt": "2026-07-10T14:30:00",
      "status": "PREPARING",
      "adminName": "김관리"
    }
  ],
  "pageInfo": {
    "currentPage": 1,
    "size": 10,
    "totalElements": 42,
    "totalPages": 5
  }
}
```

### 6-3. 주문 상세 조회 — `GET /orders/{id}`

**Response** `200 OK`

```json
{
  "orderNumber": "ORD-20260710-0001",
  "customerName": "홍길동",
  "customerEmail": "hong@test.com",
  "productName": "무선 이어폰",
  "quantity": 2,
  "totalPrice": 59800,
  "orderedAt": "2026-07-10T14:30:00",
  "status": "PREPARING",
  "adminName": "김관리",
  "adminEmail": "admin@test.com",
  "adminRole": "CS"
}
```

고객 직접 주문인 경우 `adminName`, `adminEmail`, `adminRole`은 `null`.

**에러**: 없는 ID(404)

### 6-4. 주문 상태 수정 — `PATCH /orders/{id}/status`

**Request** `{ "status": "SHIPPING" }` → **Response** `200 OK` `{ "status": "SHIPPING" }`

**허용 전환**: `PREPARING → SHIPPING`, `SHIPPING → DELIVERED` 만 가능

**에러** (모두 400)

- 단계 건너뛰기 (`PREPARING → DELIVERED`)
- 역방향 전환 (`SHIPPING → PREPARING`)
- `CANCELED` / `DELIVERED` 주문의 상태 변경
- 없는 ID(404)

### 6-5. 주문 취소 — `PATCH /orders/{id}/cancel`

`PREPARING` 상태에서만 취소 가능.

**Request** `{ "cancelReason": "고객 변심" }`

**Response** `200 OK`

```json
{
  "status": "CANCELED",
  "cancelReason": "고객 변심"
}
```

**취소 시 재고 처리 정책**

- 주문 수량만큼 상품 재고 자동 복구
- 복구된 재고에 따라 상품 상태 자동 전환 (재고 1 이상 → `판매중`)
- 단, `단종`(단종) 상품은 재고만 복구, 상태는 유지

**에러**

| 상황 | 코드 |
|------|------|
| 취소 사유 누락 | 400 |
| `SHIPPING`/`DELIVERED` 상태에서 취소 | 400 |
| 이미 취소된 주문 재취소 | 400 |
| 없는 ID | 404 |

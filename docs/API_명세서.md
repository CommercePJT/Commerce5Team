# CommercePJT API 명세서

이커머스 어드민 백엔드 API 명세서입니다. (팀: 오류가 없조)

## 공통 사항

### 인증

* 세션 + 쿠키(JSESSIONID) 기반 인증
* 로그인 제외 모든 API는 로그인 필수 (LoginInterceptor)
* 인증 제외 경로: `POST /admins/signup`, `POST /admins/login`, `/h2-console/\\\*\\\*`, `/error`
* 미로그인 요청 시: `401 { "status": 401, "message": "로그인이 필요합니다" }`

### 페이징 (리스트 조회 공통)

Spring Pageable 방식을 사용합니다.

|파라미터|설명|기본값|
|-|-|-|
|page|페이지 번호 (**0부터 시작**)|0|
|size|페이지당 개수|10|
|sort|정렬: `필드명,방향` (예: `createdAt,desc`)|createdAt,desc|

요청 예시: `GET /orders?page=0\\\&size=10\\\&sort=totalPrice,asc`

리스트 응답에는 페이징 정보가 포함됩니다.

```json
"pageInfo": {
  "page": 1,
  "size": 10,
  "totalElements": 53,
  "totalPages": 6
}
```

※ 응답의 page는 1부터 표기 (사람 기준)

### 에러 응답 포맷

```json
{
  "status": 404,
  "message": "주문을 찾을 수 없습니다."
}
```

|코드|의미|
|-|-|
|400|유효성 검증 실패, 비즈니스 규칙 위반 (재고 부족, 잘못된 상태 전환 등)|
|401|인증 실패 (미로그인, 로그인 정보 불일치)|
|403|접근 불가 계정 (승인대기/거부/정지/비활성)|
|404|대상 리소스 없음|
|409|중복 (이메일 등)|

\---

## 전체 엔드포인트 요약

|도메인|기능|메서드|URL|
|-|-|-|-|
|인증|관리자 가입|POST|/admins/signup|
|인증|로그인|POST|/admins/login|
|인증|로그아웃|POST|/admins/logout|
|인증|비밀번호 변경|PATCH|/admins/me/password|
|관리자|내 프로필 조회|GET|/admins/me|
|관리자|내 프로필 수정|PATCH|/admins/me|
|관리자|리스트 조회|GET|/admins|
|관리자|상세 조회|GET|/admins/{id}|
|관리자|정보 수정|PATCH|/admins/{id}|
|관리자|역할 변경|PATCH|/admins/{id}/role|
|관리자|상태 변경|PATCH|/admins/{id}/status|
|관리자|승인|PATCH|/admins/{id}/approve|
|관리자|거부|PATCH|/admins/{id}/reject|
|관리자|삭제|DELETE|/admins/{id}|
|고객|리스트 조회|GET|/customers|
|고객|상세 조회|GET|/customers/{customerId}|
|고객|정보 수정|PATCH|/customers/{customerId}|
|고객|상태 변경|PATCH|/customers/{customerId}/status|
|고객|삭제|DELETE|/customers/{customerId}|
|상품|등록|POST|/products|
|상품|리스트 조회|GET|/products|
|상품|상세 조회|GET|/products/{productId}|
|상품|정보 수정|PATCH|/products/{productId}|
|상품|재고 수정|PATCH|/products/{productId}/stock|
|상품|상태 수정|PATCH|/products/{productId}/status|
|상품|삭제|DELETE|/products/{productId}|
|주문|CS 주문 생성|POST|/orders|
|주문|리스트 조회|GET|/orders|
|주문|상세 조회|GET|/orders/{orderId}|
|주문|상태 수정|PATCH|/orders/{orderId}/status|
|주문|취소|PATCH|/orders/{orderId}/cancel|
|리뷰|리뷰 생성|POST|/reviews|
|리뷰|리스트 조회|GET|/reviews|
|리뷰|상세 조회|GET|/reviews/{reviewId}|
|리뷰|삭제|DELETE|/reviews/{reviewId}|
|리뷰|상품별 리뷰 통계|GET|/products/{productId}/reviews|

\---

## 1\. 인증 (Auth)

### 1-1. 관리자 회원가입 — `POST /admins/signup`

**Request**

```json
{
  "name": "홍길동",
  "email": "hong@test.com",
  "password": "password123",
  "phone": "010-1234-5678",
  "role": "CS\\\_ADMIN"
}
```

|필드|검증 규칙|
|-|-|
|name|필수|
|email|필수, 이메일 형식, 중복 불가|
|password|필수, 최소 8자 이상 (BCrypt 암호화 저장)|
|phone|필수, 010-XXXX-XXXX 형식,중복불가|
|role|필수: SUPER\_ADMIN / OPERATION\_ADMIN / CS\_ADMIN|

**Response** `201 Created` — 가입된 관리자 정보 (상태: PENDING)

**에러**: 검증 실패,필수값누락,이메일/전화번호 형식오류, 비밀번호 8자 미만(400), 이메일,전화번호 중복(409)

### 1-2. 로그인 — `POST /admins/login`

**Request**

```json
{ "email": "hong@test.com", "password": "password123" }
```

**Response** `200 OK` — 세션 생성, JSESSIONID 쿠키 발급

**에러**

* 이메일/비밀번호 불일치: `401 "이메일 또는 비밀번호가 일치하지 않습니다."`
* 승인 대기: `403 "계정 승인 대기 중입니다."`
* 거부: `403 "관리자 신청이 거부되었습니다. 거부 날짜: ..., 거부 사유: ..."`
* 정지: `403 "정지된 계정입니다."` / 비활성: `403 "비활성화된 계정입니다."`

### 1-3. 로그아웃 — `POST /admins/logout`

**Response** `204 No Content` — 세션 무효화

### 1-4. 비밀번호 변경 — `PATCH /admins/me/password`

**Request**

```json
{ "password": "현재비밀번호", "newPassword": "새비밀번호" }
```

**Response** `204 No Content`

**에러**: 현재 비밀번호 불일치(401), 관리자 없음(404)

\---

## 2\. 관리자 (Admin)

### 2-1. 내 프로필 조회 — `GET /admins/me`

**Response** `200 OK`

```json
{ "name": "홍길동", "email": "hong@test.com", "phone": "010-1234-5678" }
```

### 2-2. 내 프로필 수정 — `PATCH /admins/me`

**Request**: name, email, phone
**Response** `200 OK` — 수정된 프로필
**에러**: 이메일 중복(409)

### 2-3. 관리자 리스트 조회 — `GET /admins`

|파라미터|설명|
|-|-|
|keyword|이름 또는 이메일 검색 (부분 일치)|
|role|역할 필터|
|status|상태 필터 (PENDING/ACTIVE/INACTIVE/SUSPENDED/REJECTED)|
|page, size, sort|공통 페이징|

**Response** `200 OK` — 관리자 목록 + pageInfo

### 2-4. 관리자 상세 조회 — `GET /admins/{id}`

**Response** `200 OK` — id, name, email, phone, role, status, createdAt, approvedAt 등

### 2-5. 관리자 정보 수정 — `PATCH /admins/{id}`

**Request**: name, email, phone / **Response** `200 OK` / **에러**: 404, 이메일 중복(409)

### 2-6. 역할 변경 — `PATCH /admins/{id}/role`

**Request**: `{ "role": "OPERATION\\\_ADMIN" }` / **Response** `200 OK` — 변경된 역할

### 2-7. 상태 변경 — `PATCH /admins/{id}/status`

**Request**: `{ "status": "INACTIVE" }` / **Response** `200 OK` — 변경된 상태

### 2-8. 승인 — `PATCH /admins/{id}/approve`

**Response** `200 OK` — 상태(ACTIVE), 승인일시
**에러**: 승인 대기 상태가 아니면 400

### 2-9. 거부 — `PATCH /admins/{id}/reject`

**Request**: `{ "rejectReason": "사유" }` (필수)
**Response** `200 OK` — 상태(REJECTED), 거부일시, 거부사유
**에러**: 승인 대기 상태가 아니면 400, 사유 누락 400

### 2-10. 삭제 — `DELETE /admins/{id}`

**Response** `204 No Content`

\---

## 3. 고객 (Customer)
### 3-1. 고객 리스트 조회 — `GET /customers`

| 파라미터 | 설명 |
|---|---|
| keyword | 이름 또는 이메일 검색 |
| status | ACTIVE / INACTIVE / SUSPENDED |
| page, size, sort | 공통 페이징 (기본값: page=1, size=10) |

**Response** `200 OK` — 고객 목록 + pageInfo  
**에러**: 존재하지 않는 정렬 필드(400), 잘못된 status 값(400)

### 3-2. 고객 상세 조회 — `GET /customers/{customerId}`

**Response** `200 OK` — id, name, email, phone, status, createdAt  
**에러**: 존재하지 않는 고객(404)

### 3-3. 고객 정보 수정 — `PATCH /customers/{customerId}`

**Request**: name, email, phone  
**Response** `200 OK`  
**에러**: 이메일/전화번호 형식 오류 또는 필수값 누락(400), 존재하지 않는 고객(404), 이메일 중복(409)

### 3-4. 고객 상태 변경 — `PATCH /customers/{customerId}/status`

**Request**: `{ "status": "SUSPENDED" }`  
**Response** `200 OK`  
**에러**: 정의되지 않은 status 값 또는 필수값 누락(400), 존재하지 않는 고객(404)

### 3-5. 고객 삭제 — `DELETE /customers/{customerId}`

**Response** `204 No Content`  
**에러**: 존재하지 않는 고객(404), 주문 이력이 있는 고객은 삭제 불가(409)


\---

## 4\. 상품 (Product)

### 4-1. 상품 등록 — `POST /products`

**Request**

```json
{ "name": "무선 이어폰", "category": "전자기기", "price": 29900, "stock": 10 }
```

등록 관리자는 로그인 세션에서 자동 저장.

**Response** `201 Created` — 상품 정보 (재고 0이면 SOLD\_OUT, 아니면 ON\_SALE)

### 4-2. 상품 리스트 조회 — `GET /products`

|파라미터|설명|
|-|-|
|keyword|상품명 검색|
|category|카테고리 필터|
|status|ON\_SALE / SOLD\_OUT / DISCONTINUED|
|page, size, sort|공통 페이징|

**Response** `200 OK` — 상품 목록 + pageInfo

### 4-3. 상품 상세 조회 — `GET /products/{productId}`

**Response** `200 OK` — 상품 정보 + 등록 관리자 정보

### 4-4. 상품 정보 수정 — `PATCH /products/{productId}`

**Request**: name, category, price / **Response** `200 OK`

### 4-5. 재고 수정 — `PATCH /products/{productId}/stock`

**Request**: `{ "stock": 50 }`
**Response** `200 OK` — 변경된 재고, 상태

* 재고 0 → SOLD\_OUT 자동 전환 / 재고 1 이상 → ON\_SALE 자동 전환 (단종 제외)

**에러**: 음수 재고(400), 단종 상품(400)

### 4-6. 상태 수정 — `PATCH /products/{productId}/status`

**Request**: `{ "status": "DISCONTINUED" }` / **Response** `200 OK`

### 4-7. 상품 삭제 — `DELETE /products/{productId}`

**Response** `204 No Content`

\---

## 5\. 주문 (Order)

### 5-1. CS 주문 생성 — `POST /orders`

고객 전화 요청을 관리자가 대신 등록. 등록 관리자는 세션에서 자동 저장.

**Request**

```json
{ "customerId": 1, "productId": 1, "quantity": 2 }
```

|필드|검증 규칙|
|-|-|
|customerId|필수, 존재하는 고객|
|productId|필수, 존재하는 상품|
|quantity|필수, 1 이상|

**처리 규칙**

* 주문번호 자동 생성: `ORD-yyyyMMdd-XXXXXXXX`
* 총액 = 주문 시점 상품 가격 × 수량
* 재고 검증 → 차감 → 재고 0이 되면 상품 SOLD\_OUT 자동 전환 (단일 트랜잭션)
* 초기 상태: PREPARING(준비중)

**Response** `201 Created`

```json
{
  "orderId": 1,
  "orderNumber": "ORD-20260714-A1B2C3D4",
  "customerName": "박활성",
  "productName": "판매중",
  "quantity": 2,
  "totalPrice": 59800,
  "status": "PREPARING",
  "orderedAt": "2026-07-14T10:00:00",
  "adminName": "장상담"
}
```

**에러**: 고객/상품/관리자 없음(404), 단종·품절 상품(400), 재고 부족(400), 수량 0 이하(400)

### 5-2. 주문 리스트 조회 — `GET /orders`

|파라미터|설명|
|-|-|
|keyword|주문번호 또는 고객명 검색|
|status|PREPARING / SHIPPING / DELIVERED / CANCELED|
|page, size, sort|공통 페이징|

**Response** `200 OK` — 주문 목록(주문ID, 주문번호, 고객명, 상품명, 수량, 총액, 주문일, 상태, 관리자명) + pageInfo

### 5-3. 주문 상세 조회 — `GET /orders/{orderId}`

**Response** `200 OK` — 주문번호, 고객 정보(이름/이메일), 상품명, 수량, 총액, 주문일, 상태, 등록 관리자 정보(이름/이메일/역할, 직접 주문 시 null)

### 5-4. 주문 상태 수정 — `PATCH /orders/{orderId}/status`

**Request**: `{ "status": "SHIPPING" }`

**상태 전환 규칙**: 준비중 → 배송중 → 배송완료 순서만 허용 (역방향·건너뛰기 불가)

**Response** `200 OK`

```json
{ "orderId": 1, "orderNumber": "ORD-20260714-A1B2C3D4", "status": "SHIPPING" }
```

**에러**: 잘못된 순서 전환(400), 취소된 주문(400), 주문 없음(404)

### 5-5. 주문 취소 — `PATCH /orders/{orderId}/cancel`

**Request**: `{ "cancelReason": "고객 변심" }` (필수)

**처리 규칙**

* 준비중(PREPARING) 상태만 취소 가능
* 취소 시 재고 자동 복구 + 상품 상태 자동 전환 (단종 상품은 상태 유지, 재고만 복구)

**Response** `204 No Content`

**에러**: 준비중이 아닌 주문(400), 사유 누락(400), 주문 없음(404)

\---

## 6\. 리뷰 (Review)

### 6-1. 리뷰 생성 — `POST /reviews`

**Request**

```json
{ "orderId": 1, "rating": 5, "content": "배송 빠르고 좋아요!" }
```

|필드|검증 규칙|
|-|-|
|orderId|필수, 존재하는 주문|
|rating|필수, 1\~5|
|content|필수|

**Response** `201 Created` — 리뷰ID, 주문번호, 고객명, 상품명, 평점, 내용, 작성일

**에러**: 평점 범위 초과(400), 주문 없음(404)

### 6-2. 리뷰 리스트 조회 — `GET /reviews`

|파라미터|설명|
|-|-|
|keyword|고객명 또는 상품명 검색|
|rating|평점 필터 (1\~5)|
|page, size, sort|공통 페이징 (정렬: rating, createdAt)|

**Response** `200 OK` — 리뷰 목록 + pageInfo

### 6-3. 리뷰 상세 조회 — `GET /reviews/{reviewId}`

**Response** `200 OK` — 상품명, 고객명, 고객 이메일, 작성일, 평점, 리뷰 내용

### 6-4. 리뷰 삭제 — `DELETE /reviews/{reviewId}`

**Response** `204 No Content`

### 6-5. 상품별 리뷰 통계 — `GET /products/{productId}/reviews`

**Response** `200 OK`

```json
{
  "averageRating": 3.7,
  "totalReviewCount": 3,
  "ratingCounts": { "5": 1, "4": 1, "3": 0, "2": 1, "1": 0 },
  "recentReviews": \\\[
    { "customerName": "박활성", "rating": 5, "content": "배송 빠르고 좋아요!", "createdAt": "..." }
  ]
}
```

* 평균 평점: 소수점 1자리 반올림 (리뷰 없으면 0.0)
* 별점별 개수: 없는 별점도 0으로 포함 (5→1 순서)
* 최신 리뷰: 작성일 내림차순 3개


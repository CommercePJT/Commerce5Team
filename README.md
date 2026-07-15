# 🛒 CommercePJT — 이커머스 어드민 백엔드

이커머스 플랫폼의 **관리자(Admin) 시스템 백엔드**입니다.
관리자 계정 관리부터 고객·상품·주문 관리까지, 이커머스 운영에 필요한 핵심 어드민 기능을 REST API로 제공합니다.

## 📌 프로젝트 개요

- **주제**: 이커머스 어드민 백엔드 시스템
- **기간**: 2026.07.19 (오류가 없조 팀 프로젝트)
- **인원**: 6명 (2개 파트로 분담)
- **인증 방식**: 세션 + 쿠키 (HttpOnly)

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot |
| ORM | Spring Data JPA |
| Database | H2 (개발) |
| Auth | Session + Cookie, BCrypt (`at.favre.lib:bcrypt`) |
| Build | Gradle |
| Collaboration | GitHub, Notion, Slack |

## 👥 팀 구성 및 역할 분담

| 파트                | 담당 도메인 |
|-------------------|------------|
| A파트 (최정윤,신형탁,임경식) | 1. 관리자 회원가입 · 2. 관리자 인증 · 3. 관리자 정보 관리 |
| B파트 (김혁중,장준혁,최정이) | 4. 고객 정보 관리 · 5. 상품 정보 관리 · 6. 주문 정보 관리 |

> 각 도메인은 담당자가 entity / dto / repository / service / controller 전체를 구현합니다.

## ✨ 주요 기능

### 1️⃣ 인증
- 회원가입 : 입력값 유효성 검증 (이메일 형식,전화번호 `010-XXXX-XXXX`),
이메일/전화번호 중복 검증 (중복 시 409),비밀번호 BCrypt 암호화 저장, 
가입 시 **승인대기** 상태
- 로그인 : **활성** 상태만 허용 - 승인대기/거부(거부일,사유안내)/정지/비활성은 상태별 403 메세지 분리
- 로그인 실패시 이메일/비밀번호 구분 없는 메세지 (계정 존재 여부 노출 방지)
- LoginInterceptor : 전 API 로그인 검사 (가입/로그인/H2콘솔만 예외) 미로그인 401
- @LoginAdmin ArgumentResolver : 컨트롤러 파라미터로 로그인 관리자 ID 자동주입

### 2️⃣ 관리자 관리
- 관리자 리스트 조회 — 검색(이름/이메일), 페이징•정렬, 역할/상태 필터
- 관리자 상세 조회 / 정보 수정(이메일,휴대폰 중복 검증) / 역할 변경 / 상태 변경 / 삭제
- **가입 승인/거부** — 승인 시 승인일시 기록, 거부 시 거부 사유(필수)·거부일시 기록
- 내 프로필 조회/수정, 비밀번호 변경 (현재 비밀번호 검증)

### 4️⃣ 고객 관리
- 고객 리스트 조회 — 검색(이름/이메일), 페이징•정렬, 상태 필터
- 고객 상세 조회 / 정보 수정(이메일,휴대폰 중복 검증) / 상태 변경 / 삭제(주문이력이있는 고객은 삭제 불가)

### 5️⃣ 상품 정보 관리
- 상품 등록 (등록 관리자 세션에서 자동 저장) / 리스트 조회(검색•카테고리•상태 필터)/ 상세 조회 / 수정 / 삭제
- **재고 자동 전환 정책**
  - 재고 0 이하 → `품절` 자동 전환
  - 재고 1 이상 → `판매중` 자동 전환
  - `단종` 상품은 재고 변동과 무관하게 상태 유지

### 6️⃣ 주문 정보 관리
- **CS 대리 주문 생성** — 주문번호·주문일 자동 생성, 주문 당시 가격 기준 총액 계산
- 주문 생성 시 **재고 검증·차감 및 상품 상태 자동 전환을 단일 트랜잭션**으로 처리(실패시 롤백)
- 주문 리스트 조회 — 검색(주문번호/고객명), 페이징•정렬, 상태 필터
- 주문 상태 관리 — `준비중 → 배송중 → 배송완료` 순서 강제
- **주문 취소** — 준비중 상태만 취소 가능, 취소 사유 필수, 재고 자동 복구 (단종 상품은 상태 유지)

## 🗂 ERD

<img width="1536" height="1024" alt="erd" src="https://github.com/user-attachments/assets/8b3b5abd-9002-4c0c-8fb8-08e89a55eb31" />

- 모든 관계는 **1:N** (FK는 자식 테이블에 위치)
- `orders.admin_id`는 **nullable** — 고객 직접 주문 시 등록 관리자 없음
- 모든 테이블은 `BaseEntity`(created_at, modified_at)를 상속 — JPA Auditing 적용
- 리뷰는 **주문을 참조** - 구매 기반 리뷰 구조 (고객•상품 정보는 주문 경유 접근)

## 📡 API 요약

전체 명세는 [API 명세서](./docs/API_명세서.md) 참고

| 도메인 | 메서드 | 엔드포인트 |
|--------|--------|-----------|
| 인증 | POST·PATCH | `/admins/signup`, `/admins/login`, `/admins/logout`,/admins/me/password' |
| 관리자 | GET·PATCH·DELETE | `/admins`, `/admins/{id}`, `/admins/{id}/role·status·approve·reject`, `/admins/me` |
| 고객 | GET·PATCH·DELETE | `/customers`, `/customers/{customerId}`, `/customers/{customerId}/status` |
| 상품 | POST·GET·PATCH·DELETE | `/products`, `/products/{productId}`, `/products/{productId}/stock·status` |
| 주문 | POST·GET·PATCH | `/orders`, `/orders/{oderId}`, `/orders/{oderId}/status·cancel` |
| 리뷰 | POST·GET·DELETE | `/reviews`, ` /reviews/{reviewID}`, `/products/{productID}/reviews`  |

**페이징 (공통)** : Spring Pageable - `?page=0&size=10&sort=createdAt,desc` (기본 10개, 최신순)

**공통 에러 응답**: 400(형식 오류·규칙 위반), 401(인증 실패), 403(접근 불가 계정), 404(리소스 없음), 409(중복)

## 📁 패키지 구조

```
com.example.commercepjt
├── common
│   ├── config        # LoginAdmin, ArgumentResolver,PasswordEncoder,WebConfig
│   ├── dto           # PageInfo
│   ├── entity        # BaseEntity (JPA Auditing)
│   └── exception     # GlobalExceptionHandler,공통 예외 처리
├── auth              # 회원가입·로그인·세션 (controller/dto/service/session)
├── admin
│   ├── controller
│   ├── dto
│        ├── request
│        └── response 
│   ├── entity
│   ├── repository   # + Specification
│   └── service
├── customer
├── product
├── order
└── review           # (각 도메인 동일 구조)
```

도메인형 패키지 구조로 + dto의 request/response 분리로, 도메인별 담당자가 독립적으로 작업할 수 있도록 설계했습니다.

## 🚀 실행 방법

```bash
# 1. 클론
git clone https://github.com/CommercePJT/Commerce5Team.git
cd Commerce5Team

# 2. 실행
./gradlew bootRun
```

- 서버: `http://localhost:8080`
- H2 콘솔: `http://localhost:8080/h2-console` (JDBC URL :'jdbc:h2:mem:commercedb', User : 'sa')
- 실행 시 'data.sql'로 상태별 테스트 데이터 자동 입력 (관리자,고객,상품,주문,리뷰)
- 
## 🌿 Git 컨벤션

**브랜치 전략**

```
main                         # 통합 브랜치
├── 1.feature/auth
├── 2.feature/admin
├── 3.feature/customer
├── 4.feature/product
├── 5.feature/order
└── 6.feature/review
```
**커밋 컨벤션** : 'feat:'	새 기능/'fix:'	버그 수정/'docs:'	문서 수정/'refactor:'	코드 정리/ 'test:' 테스트

- 도메인별 feature 브랜치에서 작업 후 PR → 팀 리뷰 → main 머지

## 🔨 코드 컨벤션

통합 단계에서 전 도메인 코드 스타일을 통일했습니다.

-**페이징** : Spring 'Pageable' + '@PageableDefault(size = 10)' - 페이징 조립 코드 제거
-**DTO 변환** : 엔티티를 받는 생성자 방식 'new XxxResponse(entity)' 스트림은 '.map(XxxResponse::new)'
-**메서드 네이밍** : 조회 'findOne'/'findAll',생성 'create',수정 'update',삭제 'delete' (도메인명 없는 짧은 동사)
-**검증 위치** : 형식 검증은 DTO('@Valid' + Bean Validation),비즈니스 검증은 Service
-**에러 메시지** : '"~를 찾을 수 없습니다."' 포멧 통일
-**조회 트랜젝션** : @Transactional(readOnly = true)'

## 💡 기술적 의사결정

- **테이블명 `orders`** — `order`는 SQL 예약어(ORDER BY)라 복수형 사용
- **`@Enumerated(EnumType.STRING)`** — enum 순서 변경에도 데이터가 깨지지 않도록 문자열 저장
- **연관관계 `FetchType.LAZY`** — 불필요한 join 방지
- **재고 차감/복구 로직을 Product 엔티티 메서드로 통합** — 상품 도메인과 주문 도메인에서 동일 로직을 공유해 중복 제거
- **주문 삭제 대신 취소(상태 변경)** — 주문 이력 보존
- **리뷰가 주문을 참조** - 구매 검증된 리뷰 구조, 고객·상품 정보 중복 저장 방지
- **Security 대신 Interceptor 직접 구현** - 세션 인증 학습 목적에 맞게 인증 흐름을 직접 제어
- **BaseEntity '@Temporal' 제거** - 'LocalDateTime'은 타입 자체가 명확해 불필요함을 확인하고 정리

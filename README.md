# FluxMall - 전통적 Spring Framework 쇼핑몰

## 프로젝트 개요

**FluxMall**은 **Spring Framework 5.x** 기반의 전통적인 서버 사이드 렌더링 쇼핑몰 프로젝트입니다.
**JSP**를 뷰(View)로 사용하고, **JdbcTemplate**로 데이터 접근하며, **MySQL**을 데이터베이스로 사용합니다.

이 프로젝트의 목표는 **핵심 쇼핑몰 기능(회원, 상품, 주문)**을 간결하고 명확하게 구현하여 Spring MVC의 기본 동작 방식을 학습하는 것입니다.

## 기술 스택

| 구분             | 기술                          |
|------------------|-------------------------------|
| 프레임워크       | Spring Framework 5.x (Non-Boot) |
| 뷰               | JSP (Java Server Pages)       |
| 데이터 접근      | JdbcTemplate                  |
| 데이터베이스     | MySQL 5.7 / 8.0             |
| 빌드 도구       | Maven                         |
| 서버             | Apache Tomcat 9 (외장 WAS)  |
| 라이브러리       | Log4j2, JSTL, Tiles          |
| 기타             | BCryptPasswordEncoder           |

## 구현된 기능

### 회원 관리
- **회원가입**: 이메일과 비밀번호를 통한 회원 등록
  - 이메일 중복 체크
  - BCrypt를 통한 비밀번호 암호화
- **로그인/로그아웃**: 세션 기반 인증
  - 로그인 성공 시 세션에 회원 정보 저장
  - 로그아웃 시 세션 무효화
- **마이페이지**: 내 정보 조회 및 수정

### 상품 관리
- **상품 목록 조회**: 판매 중인 상품 목록
  - 키워드 기반 검색 (상품명/설명)
- **상품 상세 조회**: 상품 상세 정보
  - 실시간 재고 표시
  - 재고가 0인 상품은 '품절' 상태로 표시
- **재고 관리**: 재고 차감/복구 기능

### 주문 관리
- **주문 생성**: 상품 구매 시 주문서 생성
  - 재고 선점 (주문 시 재고 즉시 차감)
  - 결제 대기 상태의 주문 레코드 생성
  - 재고 부족 시 주문 불가 처리
- **결제 처리**: 주문 결제 완료 처리
  - 주문 상태를 '결제 완료'로 변경
- **주문 취소**: 주문 취소 시 재고 복구
  - 재고를 다시 상품 테이블로 복원
  - 주문 상태를 '주문 취소'로 변경
- **주문 내역**: 회원별 주문 목록 조회 및 상세 조회

## 핵심 기술 특징

### 1. 재고 정합성 보장
- 주문 생성 시 `@Transactional`을 통한 원자성 보장
- 재고 차감과 주문 생성은 하나의 트랜잭션으로 처리
- 결제 실패 시 자동으로 재고 복구

### 2. 세션 기반 인증
- `HttpSession`을 이용한 세션 관리
- 로그인된 회원 정보를 세션에 저장 (`loginMember`)
- 인증이 필요한 페이지 접근 제어

### 3. Tiles 레이아웃
- 공통 레이아웃 (header/footer) 적용
- 기반 레이아웃을 상속받는 9개 페이지 템플릿 정의

### 4. JdbcTemplate + RowMapper
- JDBC 직접 사용 대신 Spring의 JdbcTemplate 활용
- `RowMapper` 인터페이스를 통한 ResultSet → VO 변환
- `EmptyResultDataAccessException`을 통한 null 반환 처리

## 프로젝트 구조

```
com.fluxmall
├── config/          # Spring 설정 (DataSource, Transaction, Tiles)
├── controller/      # HTTP 요청 처리 (@Controller)
├── dao/            # 데이터베이스 접근 (JdbcTemplate)
├── domain/
│   ├── vo/         # Value Objects (MemberVO, ProductVO, OrderVO)
│   └── mapper/     # RowMapper 구현
├── service/        # 비즈니스 로직 (@Transactional)
├── util/           # 공통 유틸리티 (DateFormatter)
└── FluxMallApplication.java  # 메인 애플리케이션
```

## 데이터베이스 스키마

### Members (회원)
- `id`: 회원 ID (PK, AUTO_INCREMENT)
- `email`: 이메일 (로그인 ID, UNIQUE)
- `password`: 암호화된 비밀번호
- `name`: 회원 이름
- `created_at`, `updated_at`: 타임스탬프

### Products (상품)
- `id`: 상품 ID (PK, AUTO_INCREMENT)
- `name`: 상품명
- `description`: 상품 설명
- `price`: 상품 가격
- `stock_quantity`: 재고 수량
- `status`: 판매 상태 (ON_SALE, SOLD_OUT)
- `created_at`, `updated_at`: 타임스탬프

### Orders (주문)
- `id`: 주문 ID (PK, AUTO_INCREMENT)
- `member_id`: 회원 ID (FK)
- `order_number`: 주문 번호 (유니크)
- `product_id`: 상품 ID (FK)
- `quantity`: 주문 수량
- `total_price`: 총 결제 금액
- `status`: 주문 상태 (PENDING, PAID, CANCELLED)
- `created_at`, `updated_at`: 타임스탬프

## 빌드 및 실행

### 빌드
```bash
# WAR 파일 생성
./mvnw clean package

# 테스트 스킵
./mvnw clean package -DskipTests
```

### 실행
```bash
# MySQL 데이터베이스 먼저 실행
mysql -u root -p shopdb < src/main/resources/schema.sql

# WAR 파일을 Tomcat webapps 디렉토리에 배포
cp target/ROOT.war $TOMCAT_HOME/webapps/

# 또는 IDE에서 Tomcat 실행 후 접속
```

### 단일 테스트 실행
```bash
# 특정 테스트 클래스 실행
./mvnw test -Dtest=ClassName#methodName

# 모든 테스트 실행
./mvnw test
```

## 접속 정보

- **기본 URL**: http://localhost:8080/ROOT/
- **로그인**: 이메일/비밀번호로 로그인
- **회원가입**: 이메일 중복 체크 후 가입
- **상품 목록**: 전체 상품 또는 카테고리별 목록
- **주문하기**: 로그인 후 상품에서 구매 버튼 클릭

## 코드 스타일

- **네이밍 컨벤션**: PascalCase (클래스), camelCase (메서드/변수)
- **접미사**: VO (Value Objects), Dao, Service, Controller, RowMapper
- **의존성 주입**: 생성자 주입 + `@Autowired`
- **트랜잭션 관리**: `@Transactional` 어노테이션 사용
- **예외 처리**: DAO에서 null 반환, Service에서 RuntimeException 발생

## 주의사항

1. **JSP 사용**: 최근 웹 개발 트렌드(Thymeleaf, React 등)와 달리 JSP 사용
2. **JdbcTemplate 직접 사용**: ORM(JPA/Hibernate) 대신 SQL 직접 작성
3. **외장 Tomcat 사용**: 내장 Tomcat 대신 WAR 배포 방식
4. **Log4j2 설정**: `/logs` 디렉토리에 로그 파일 생성

## 향후 개선 방향

- JSP 대신 Thymeleaf 같은 최신 템플릿 엔진으로 마이그레이션
- JdbcTemplate 대신 JPA/Hibernate 도입
- Spring Boot로 마이그레이션
- 테스트 코드 추가 (현재 없음)

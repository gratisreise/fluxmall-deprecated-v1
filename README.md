# FluxMall - 간단한 쇼핑몰 프로젝트

## 프로젝트 개요

**FluxMall**은 **Spring Boot 2.x** 기반의 전통적인 서버 사이드 렌더링 쇼핑몰 프로젝트입니다.
뷰(View)는 **JSP**를 사용하고, 데이터 접근은 **JdbcTemplate**, 데이터베이스는 **MySQL**을 사용합니다.

이 프로젝트의 주요 목표는 **핵심 쇼핑몰 기능(회원, 상품, 장바구니, 주문)**을 간결하고 명확하게 구현하는 것입니다.
현대적인 SPA(Single Page Application)가 아닌 클래식한 MVC 구조를 통해 Spring Boot의 기본 동작 방식을 이해하는 데 좋은 예제입니다.

## 주요 기능

### 회원
- 이메일/아이디 기반 회원가입 (AJAX를 이용한 실시간 아이디 중복 체크)
- 로그인 / 로그아웃 (세션 + Spring Security)
- 마이페이지 (내 정보 조회 및 닉네임/비밀번호 수정)

### 상품
- 카테고리별 상품 목록 조회 (페이징 처리)
- 키워드 기반 상품 검색 (상품명/설명)
- 상품 상세 정보 조회 (실시간 재고 표시)
- 상품 등록 (로그인한 회원은 누구나 상품 등록 가능 → 간단한 판매자 개념)

### 장바구니
- 장바구니에 상품 담기 (AJAX, 수량 지정 가능)
- 수량 증감 / 개별 상품 삭제 / 선택 상품 삭제 (AJAX를 이용한 실시간 반영)
- 총 결제 예정 금액 실시간 계산

### 주문
- 장바구니의 모든 상품 주문 또는 개별 상품 바로 구매
- 주문서 작성 (배송지 입력)
- 결제 시 실시간 재고 차감 (`@Transactional` 보장)
- 주문 완료 페이지
- 주문 내역 목록 (페이징 처리) 및 상세 조회

## 기술 스택

| 구분             | 기술                          |
|------------------|-------------------------------|
| 프레임워크       | Spring Boot 2.7.18            |
| 뷰               | JSP (Java Server Pages)       |
| 데이터 접근      | JdbcTemplate                  |
| 데이터베이스     | MySQL                         |
| 보안             | Spring Security (폼 로그인 + CSRF) |
| 비밀번호 암호화  | BCryptPasswordEncoder         |
| 의존성 관리      | Maven                         |
| 기타             | Lombok, JSTL                  |

## 시작하기

### 준비물

*   Java 11 이상
*   Maven 3.6 이상
*   MySQL 8.0 이상

### 설치 및 실행

1.  **저장소 복제:**
    ```bash
    git clone https://github.com/your-username/fluxmall.git
    cd fluxmall
    ```

2.  **데이터베이스 설정:**
    *   MySQL에 `fluxmall`이라는 이름의 데이터베이스를 생성합니다.
    *   `src/main/resources/application.properties` 파일에서 데이터베이스 연결 정보를 수정합니다.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/fluxmall
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```
    *   `database/schema.sql` 파일을 실행하여 테이블을 생성합니다. (**참고:** 이 파일은 직접 생성해야 합니다.)

3.  **프로젝트 빌드:**
    ```bash
    ./mvnw clean package
    ```

4.  **애플리케이션 실행:**
    ```bash
    ./mvnw spring-boot:run
    ```
    애플리케이션은 `http://localhost:8080`에서 접속할 수 있습니다.

## 프로젝트 구조 (주요 패키지)

```
com.fluxmall
├── config         # Spring Security, Web MVC 등 설정 파일
├── controller     # HTTP 요청 처리
├── dao            # 데이터베이스 접근 객체 (JdbcTemplate 사용)
├── domain         # DTO, VO, Enum, Mapper 등 도메인 객체
├── service        # 비즈니스 로직
└── FluxMallApplication.java # 메인 애플리케이션
```
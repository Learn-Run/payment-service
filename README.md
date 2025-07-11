
# Payment Service

## 1. 프로젝트 소개

`pick-and-learn` 프로젝트의 결제 마이크로서비스입니다. Toss Payments API를 활용하여 포인트 충전 및 결제 기능을 제공합니다.

## 2. 기술 스택

- **언어**: Java 11
- **프레임워크**: Spring Boot 2.7.18
- **데이터베이스**: MySQL 8.0
- **메시지 큐**: Kafka
- **캐시**: Redis
- **API 문서**: Swagger (Springdoc)
- **기타**:
    - **Toss Payments API**: 결제 연동
    - **Resilience4j**: 서킷 브레이커, 재시도 등 장애 내성 패턴 구현
    - **Eureka**: 서비스 디스커버리
    - **Gradle**: 빌드 도구

## 3. 실행 방법

### 3.1. 환경 변수 설정

`.env` 파일을 생성하고 아래 환경 변수를 설정합니다.

```
# Toss Payments
TOSS_PAYMENT_CLIENT_KEY=...
TOSS_PAYMENT_SECRET_KEY=...
TOSS_PAYMENT_BASE_URL=https://api.tosspayments.com
TOSS_PAYMENT_SUCCESS_URL_DEV=http://localhost:8080/success
TOSS_PAYMENT_FAIL_URL_DEV=http://localhost:8080/fail
TOSS_PAYMENT_SUCCESS_URL_PROD=...
TOSS_PAYMENT_FAIL_URL_PROD=...

# MySQL (운영 환경)
MYSQL_HOST=...
MYSQL_USER=...
MYSQL_PASSWORD=...

# EC2 (운영 환경)
EC2_INFRA_HOST=...
```

### 3.2. 로컬 환경 실행

1. **Docker Compose 실행**

   `docker-compose-payment.yml` 파일을 사용하여 MySQL, Kafka, Redis, Eureka 등 인프라를 실행합니다.

   ```bash
   docker-compose -f docker-compose-payment.yml up -d
   ```

2. **애플리케이션 실행**

   `dev` 프로파일로 애플리케이션을 실행합니다.

   ```bash
   ./gradlew bootRun
   ```

## 4. API 엔드포인트

API 문서는 Swagger를 통해 제공됩니다. 애플리케이션 실행 후 아래 주소로 접속하여 확인할 수 있습니다.

- **Swagger UI**: `http://localhost:{port}/swagger-ui.html`
- **API Docs**: `http://localhost:{port}/v3/api-docs`

### 주요 엔드포인트

| Method | URI                               | 설명                           |
| ------ | --------------------------------- | ------------------------------ |
| POST   | /api/v1/order                     | 주문 생성                      |
| POST   | /api/v1/payment/request           | 결제 요청                      |
| POST   | /api/v1/payment/confirm           | 결제 승인                      |
| POST   | /api/v1/payment/{paymentKey}/cancel | 결제 취소 (환불)               |
| GET    | /api/v1/payment/{paymentKey}      | 결제 상세 정보 조회 (paymentKey) |
| GET    | /api/v1/payment/order/{orderId}   | 결제 상세 정보 조회 (orderId)  |
| GET    | /api/v1/payment/info/all          | 결제 UUID 전체 페이지 조회     |
| GET    | /api/v1/payment/{paymentUuid}/summary | 결제 요약 정보 단건 조회       |
| POST   | /api/v1/payment/order/request     | 주문 생성 및 결제 요청         |

## 5. 프로젝트 구조

```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/unionclass/paymentservice
│   │   │       ├── common          # 공통 모듈 (설정, 예외 처리, 응답 형식 등)
│   │   │       └── domain          # 도메인별 비즈니스 로직
│   │   │           ├── order       # 주문 도메인
│   │   │           └── payment     # 결제 도메인
│   │   └── resources
│   │       ├── application.yml     # 공통 설정
│   │       ├── application-dev.yml # 개발 환경 설정
│   │       └── application-prod.yml# 운영 환경 설정
│   └── test
│       └── java
└── build.gradle                    # 프로젝트 의존성 및 빌드 설정
```


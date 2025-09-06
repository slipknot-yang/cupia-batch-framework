# CUPIA Event-Driven Batch Framework

Spring Boot 기반 이벤트 처리 프레임워크입니다. Kafka를 통한 이벤트 수신과 처리를 위한 간단하면서도 확장 가능한 솔루션을 제공합니다.

## ✨ 주요 기능

- 🚀 **하이브리드 처리 방식**: @Service 기반 Simple 처리와 Spring Batch 기반 처리 모두 지원
- ⚡ **설정 기반 전환**: 코드 변경 없이 설정만으로 처리 방식 선택 가능
- 🔧 **Auto-Configuration**: 의존성 추가만으로 자동 설정
- 📦 **Spring Boot Starter**: 쉬운 통합과 설정
- 🛡️ **Transactional Outbox Pattern**: 메시지 발행 신뢰성 보장 (Phase 5 예정)

## 📋 요구사항

- Java 21+
- Spring Boot 3.2+
- Apache Kafka

## 🚀 빠른 시작

### 1. 의존성 추가

**Gradle**:
```gradle
dependencies {
    implementation 'com.cupia.framework:cupia-batch-framework:0.0.1-SNAPSHOT'
}
```

**Maven**:
```xml
<dependency>
    <groupId>com.cupia.framework</groupId>
    <artifactId>cupia-batch-framework</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. 애플리케이션 설정

**application.yml**:
```yaml
cupia:
  framework:
    kafka:
      enabled: true
    processor:
      type: SIMPLE  # 또는 BATCH

spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: your-app-group
  cloud:
    stream:
      kafka:
        streams:
          binder:
            brokers: localhost:9092
```

### 3. 메인 애플리케이션 클래스

```java
@SpringBootApplication
public class YourApplication {
    public static void main(String[] args) {
        SpringApplication.run(YourApplication.class, args);
    }
}
```

### 4. 이벤트 프로세서 구현

```java
@Component
public class OrderEventProcessor implements EventProcessor<OrderEvent> {
    
    @Override
    @Transactional
    public void process(OrderEvent event) {
        // 주문 이벤트 처리 로직
        log.info("Processing order: {}", event.getOrderId());
        
        // 비즈니스 로직 수행
        processOrderLogic(event);
    }
}
```

## ⚙️ 설정 옵션

### Framework 설정

```yaml
cupia:
  framework:
    kafka:
      enabled: true                    # Kafka 기능 활성화/비활성화
    processor:
      type: SIMPLE                     # SIMPLE 또는 BATCH
```

### Simple Processor (기본)
- 가벼운 이벤트 처리
- @Transactional 기반 트랜잭션 관리
- 빠른 응답 시간

### Batch Processor (Phase 3 예정)
- 대용량 데이터 처리
- Spring Batch 기반 배치 처리
- 복잡한 워크플로우 지원

## 🏗️ 아키텍처

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Kafka Topic   │───▶│  Event Listener │───▶│ Event Processor │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                       │
                                ▼                       ▼
                       ┌─────────────────┐    ┌─────────────────┐
                       │ Auto Config     │    │ Business Logic  │
                       └─────────────────┘    └─────────────────┘
```

## 📚 개발 로드맵

### Phase 1: 기반 구조 (완료)
- ✅ 프로젝트 구조 및 의존성 설정
- ✅ Kafka 연동 기본 구조

### Phase 2: Simple 구현체 (진행 중)
- 🔄 EventProcessor 인터페이스 설계
- 🔄 @Service 기반 이벤트 처리 구현

### Phase 3: 확장 구조 (예정)
- 📅 Spring Batch 통합
- 📅 하이브리드 구조 완성

### Phase 4: 프레임워크화 (예정)
- 📅 Spring Boot Starter 완성
- 📅 배포 및 문서화

### Phase 5: 신뢰성 보장 (예정)
- 📅 Transactional Outbox Pattern 적용
- 📅 메시지 발행 원자성 보장

## 🤝 기여하기

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 라이선스

This project is licensed under the MIT License.

## 🆘 지원

문제가 있거나 질문이 있으시면 [Issues](https://github.com/slipknot-yang/cupia-batch-framework/issues)에 등록해 주세요.
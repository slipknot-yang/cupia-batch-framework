# Transactional Outbox Pattern 구현 계획

## 개요

Transactional Outbox Pattern은 데이터베이스 트랜잭션과 메시지 발행을 원자적으로 처리하기 위한 패턴입니다. 업무 로직 완료 후 Kafka에 메시지를 프로듀스할 때 발생할 수 있는 오류를 방지하여 데이터 일관성과 메시지 전송의 신뢰성을 보장합니다.

## 패턴 설명

### 문제점
- 업무 로직 처리 완료 후 Kafka 프로듀서 실패 시 데이터 불일치 발생
- 트랜잭션 커밋 후 메시지 발행 실패 시 보상 트랜잭션 필요
- 분산 환경에서 두 개의 독립적인 시스템(DB, Kafka) 간 원자성 보장 어려움

### 해결 방법
1. **Outbox Table**: 메시지를 임시 저장할 별도 테이블 생성
2. **단일 트랜잭션**: 업무 데이터와 아웃박스 데이터를 동일 트랜잭션으로 처리
3. **별도 프로세스**: 아웃박스 테이블의 메시지를 Kafka로 전송하는 별도 프로세스
4. **메시지 상태 관리**: 발행 완료된 메시지 상태 업데이트 및 정리

## 구현 계획

### Phase 1: 아웃박스 테이블 설계 및 생성

#### 1.1 Outbox 엔티티 설계
```sql
CREATE TABLE outbox_events (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    aggregate_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    event_data TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    processed_at TIMESTAMP NULL,
    status ENUM('PENDING', 'PROCESSED', 'FAILED') DEFAULT 'PENDING',
    retry_count INT DEFAULT 0,
    version BIGINT DEFAULT 1
);
```

#### 1.2 JPA 엔티티 구현
- `OutboxEvent` 엔티티 클래스
- 기본적인 CRUD 리포지토리
- 상태별 조회 메서드

### Phase 2: 이벤트 생산자(Producer) 구현

#### 2.1 아웃박스 서비스 개발
- `OutboxEventService`: 아웃박스 이벤트 생성 및 관리
- 업무 로직과 동일 트랜잭션에서 아웃박스 레코드 생성
- 이벤트 직렬화 및 메타데이터 관리

#### 2.2 EventProcessor 통합
- 기존 `SimpleEventProcessor`에 아웃박스 패턴 적용
- `@Transactional` 범위 내 아웃박스 이벤트 생성
- 설정 기반 아웃박스 활성화/비활성화

### Phase 3: 이벤트 릴레이(Relay) 구현

#### 3.1 아웃박스 폴링 메커니즘
- `OutboxEventRelay`: PENDING 상태 이벤트 조회 및 처리
- 스케줄링 기반 배치 처리
- 동시성 제어 및 중복 처리 방지

#### 3.2 Kafka 프로듀서 통합
- 아웃박스 이벤트를 Kafka 메시지로 변환
- 발행 성공 시 상태 업데이트 (PROCESSED)
- 실패 시 재시도 로직 및 실패 처리

### Phase 4: 에러 처리 및 모니터링

#### 4.1 재시도 메커니즘
- 지수 백오프 기반 재시도
- 최대 재시도 횟수 제한
- Dead Letter Queue 구현

#### 4.2 모니터링 및 알림
- 아웃박스 테이블 크기 모니터링
- 실패 이벤트 알림 시스템
- 메트릭 수집 및 대시보드 연동

### Phase 5: 성능 최적화 및 운영

#### 5.1 배치 처리 최적화
- 일괄 처리(Batch Processing) 구현
- 파티셔닝 기반 병렬 처리
- 데이터베이스 커넥션 풀 최적화

#### 5.2 데이터 정리 정책
- 처리된 이벤트 아카이브
- 오래된 데이터 자동 정리
- 데이터 보존 정책 구현

## 설정 및 사용법

### 애플리케이션 설정
```yaml
cupia:
  framework:
    outbox:
      enabled: true
      relay:
        polling-interval: 5000
        batch-size: 100
        max-retry: 3
      cleanup:
        retention-days: 30
        cleanup-interval: 86400000
```

### 사용 예시
```java
@Service
@Transactional
public class OrderEventProcessor implements EventProcessor<OrderEvent> {
    
    @Autowired
    private OutboxEventService outboxService;
    
    @Override
    public void process(OrderEvent event) {
        // 업무 로직 처리
        processOrderLogic(event);
        
        // 아웃박스 이벤트 생성 (동일 트랜잭션)
        outboxService.createEvent(
            event.getOrderId(),
            "ORDER_PROCESSED",
            event
        );
    }
}
```

## 기대 효과

### 신뢰성 향상
- 데이터베이스 트랜잭션과 메시지 발행의 원자성 보장
- 메시지 손실 방지
- 시스템 장애 시에도 메시지 전송 보장

### 운영 안정성
- 분산 시스템 환경에서의 데이터 일관성 유지
- 장애 복구 시 미처리 메시지 자동 재전송
- 모니터링을 통한 프로액티브 운영

## 구현 우선순위

1. **Phase 1 & 2 (필수)**: 기본적인 아웃박스 패턴 구현
2. **Phase 3 (핵심)**: 릴레이 메커니즘 및 Kafka 통합
3. **Phase 4 (안정성)**: 에러 처리 및 재시도 로직
4. **Phase 5 (최적화)**: 성능 최적화 및 운영 도구

## 마일스톤

- **M1 (Phase 1 완료)**: 아웃박스 테이블 및 기본 엔티티 구현
- **M2 (Phase 2 완료)**: EventProcessor와 아웃박스 통합
- **M3 (Phase 3 완료)**: 릴레이 메커니즘 및 Kafka 프로듀서 구현
- **M4 (Phase 4 완료)**: 에러 처리 및 모니터링 시스템 구축
- **M5 (Phase 5 완료)**: 성능 최적화 및 운영 도구 완성

## 기술 스택

- **Database**: H2/MySQL/PostgreSQL
- **ORM**: Spring Data JPA
- **Messaging**: Apache Kafka, Spring Kafka
- **Scheduling**: Spring Scheduler
- **Monitoring**: Micrometer, Actuator
- **Testing**: TestContainers, Embedded Kafka
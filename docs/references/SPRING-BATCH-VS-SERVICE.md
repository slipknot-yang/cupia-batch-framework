### **Spring Batch vs @Service 아키텍처 선택 논의**

-----

### **1. 핵심 논의 포인트**

#### **@Service 기반 접근법**

  * **구조:** `Kafka Event` → `@EventListener` → `@Service.processEvent()`
  * **장점:**
      * **개발 단순성:** 별도의 배치 Job 설정 불필요.
      * **경량성:** Spring Batch 메타데이터 테이블 오버헤드가 없음.
      * **빠른 응답성:** 이벤트 수신 즉시 처리가 가능.
      * **트랜잭션:** `@Transactional`로 간단하게 트랜잭션 관리 가능.
  * **단점:**
      * **복잡한 에러 처리:** 실패 시 재시도, 에러 정책 등 복잡한 에러 처리가 어려움.
      * **재시작 기능:** 작업이 실패하면 처음부터 다시 실행해야 하며, 특정 실패 지점부터 재시작하는 기능이 없음.
      * **대용량 처리 한계:** 대용량 데이터를 처리할 때 메모리 한계에 부딪힐 수 있음.

#### **Spring Batch Job 기반 접근법**

  * **구조:** `Kafka Event` → `@EventListener` → `JobLauncher.run(job)`
  * **장점:**
      * **강력한 재시작/복구:** 실패한 작업은 중단된 지점부터 재시작 가능.
      * **유연한 정책:** 건너뛰기(Skip), 재시도(Retry) 등 강력한 정책 지원.
      * **메모리 효율성:** 청크 단위 처리를 통해 대용량 데이터도 효율적으로 처리 가능.
      * **실행 이력 관리:** `Job Repository`를 통해 모든 작업의 실행 이력을 체계적으로 관리.
  * **단점:**
      * **설정 복잡성:** Spring Batch의 복잡한 설정(Job, Step, Reader, Processor, Writer)이 요구됨.
      * **메타데이터 오버헤드:** Job 실행 정보를 저장하기 위한 메타데이터 테이블이 필요.
      * **과도한 복잡성:** 단순한 이벤트 처리에는 과도한 오버헤드를 발생시킬 수 있음.

-----

### **2. 결정을 위한 고려사항**

  * **CUPIA의 실제 요구사항:**
      * 기존 시스템의 배치 작업이 얼마나 복잡했는지?
      * 작업 실패 시 특정 지점부터 재시작하는 기능이 필수적인지?
      * 처리해야 할 데이터량이 얼마나 되는지? (예: 하루 수십만 건 vs 수백만 건 이상)
  * **이벤트 기반 특성:**
      * 이벤트 기반 시스템은 전통적인 배치와 달리 대용량 일괄처리보다는 개별 이벤트의 실시간 처리가 더 중요할 수 있음.
  * **범용 프레임워크 목표:**
      * 다양한 복잡도를 가진 작업을 모두 지원할 수 있는 유연성이 요구됨.
      * 사용 편의성과 확장성을 동시에 고려해야 함.

-----

### **3. 제안하는 해결 방안: 하이브리드 접근법**

두 아키텍처의 장점을 모두 활용하는 **점진적/하이브리드 접근법**을 제안합니다.

1.  **공통 인터페이스 정의:**

      * 모든 이벤트 처리 로직이 구현할 공통 `EventProcessor` 인터페이스를 정의합니다.

    <!-- end list -->

    ```java
    public interface EventProcessor<T> {
        void process(T event);
    }
    ```

2.  **구현체 선택:**

      * **단순한 처리용:** `@Service`를 사용하는 경량 구현체를 만듭니다.

    <!-- end list -->

    ```java
    @Service
    public class SimpleEventProcessor implements EventProcessor<OrderEvent> {
        @Transactional
        public void process(OrderEvent event) {
            // 단순 비즈니스 로직
        }
    }
    ```

      * **복잡한 처리용:** Spring Batch를 실행하는 구현체를 만듭니다.

    <!-- end list -->

    ```java
    @Component
    public class BatchEventProcessor implements EventProcessor<OrderEvent> {
        public void process(OrderEvent event) {
            // JobLauncher를 사용하여 Spring Batch Job 실행
        }
    }
    ```

3.  **설정 기반의 유연한 선택:**

      * 애플리케이션 설정 파일(`application.yml` 등)을 통해 각 이벤트 처리 방식(`simple` 또는 `batch`)을 선택할 수 있도록 합니다.

    <!-- end list -->

    ```yaml
    cupia:
      event-processing:
        order-processor:
          type: simple # 초기에는 simple로 설정
          retry: 3
          timeout: 30s
    ```

### **결론**

이 방식은 다음과 같은 이점을 제공합니다.

  * **초기 개발 속도:** 복잡한 Spring Batch 설정 없이 `@Service` 방식으로 빠르게 기능을 구현할 수 있습니다.
  * **점진적 확장:** 나중에 특정 작업의 복잡도가 높아지거나 대용량 처리가 필요해지면, 코드 변경 없이 설정만으로 Spring Batch 기반 구현체로 전환할 수 있습니다.
  * **프레임워크 투명성:** 프레임워크 사용자는 복잡한 구현 방식을 알 필요 없이, 설정만으로 적절한 처리 방식을 선택할 수 있습니다.


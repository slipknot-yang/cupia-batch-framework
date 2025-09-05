### **CUPIA Event-Driven Processing Framework 개발 계획**

---

### **📋 확정된 아키텍처 결정**

* **하이브리드 접근법 채택:** `@Service` 기반과 `Spring Batch` 기반의 장점을 모두 활용하는 하이브리드 아키텍처를 채택.
* **초기 개발:** `@Service` 기반의 경량 구현으로 빠르게 시작하여 단순한 이벤트 처리에 집중.
* **점진적 확장:** 복잡한 로직이나 대용량 처리가 필요한 경우, `Spring Batch` 기반 구현체로 전환.
* **프레임워크 투명성:** 사용자는 설정 파일을 통해 원하는 처리 방식을 선택하고, 내부 구현 방식은 추상화하여 투명하게 제공.

---

### **🚀 개발 단계별 계획**

#### **Phase 1: 기반 구조 구축**

1.  **프로젝트 기본 구조 생성**
    * **기술 스택:** Java 21, Spring Boot, Gradle
    * **주요 의존성:** Spring Cloud Streams, Kafka
2.  **핵심 인터페이스 정의**
    * `EventProcessor<T>`: 이벤트 처리 로직을 위한 공통 인터페이스.
    * 이벤트 리스너 (`@EventListener`)를 활용한 Kafka 이벤트 수신 구조 설계.

#### **Phase 2: Simple 구현체 개발**

1.  **`@Service` 기반 `EventProcessor` 구현**
    * `SimpleEventProcessor` 클래스 구현.
    * `@Transactional`을 활용한 트랜잭션 관리 로직 구현.
2.  **설정 기반 선택 시스템**
    * YAML 설정 파일(`application.yml`)에서 `processor.type`을 `simple`로 지정.
    * `ProcessorFactory` 패턴을 적용하여 설정에 따라 적절한 `EventProcessor` 구현체를 주입하도록 구성.

#### **Phase 3: 확장 가능 구조 준비**

1.  **Spring Batch 통합 준비**
    * `BatchEventProcessor` 인터페이스 또는 클래스 설계.
    * `JobLauncher`를 통해 `Spring Batch Job`을 실행하는 연동 구조 준비.
    * `BatchEventProcessor`를 위한 별도 설정 속성 추가 (예: `batch.job-name`).
2.  **프레임워크화**
    * **Auto-configuration:** 사용자가 별도의 설정 없이 의존성 추가만으로 프레임워크를 사용할 수 있도록 자동 구성 로직 개발.
    * **Starter 모듈:** 프로젝트를 모듈화하여 `cupia-edpf-starter`와 같은 형태로 제공.

#### **Phase 4: 테스트 및 문서화**

1.  **통합 테스트**
    * **TestContainers**를 활용하여 Kafka 연동 테스트 환경 구축.
    * `SimpleEventProcessor`와 `BatchEventProcessor` 두 가지 구현체에 대한 동작 검증.
    * 실패 및 재시도 시나리오 테스트.
2.  **사용 가이드 작성**
    * **개발자 가이드 문서:** 프레임워크의 개념, 아키텍처, 사용법 설명.
    * **설정 및 사용법 예제:** `application.yml` 설정 예시와 함께 각 구현체 사용 방법 제공.

---

### **🎯 핵심 설계 원칙**

* **점진적 복잡성:** 단순한 요구사항은 단순한 코드로, 복잡한 요구사항은 점진적으로 확장 가능한 구조로 설계.
* **설정 기반 제어:** 사용자가 코드 변경 없이 설정만으로 프레임워크의 동작 방식을 전환할 수 있도록 유연성을 제공.
* **투명한 추상화:** 내부의 복잡한 구현 세부사항(e.g., Spring Batch 메타데이터)을 숨기고, 사용자에게는 `EventProcessor`라는 명확한 인터페이스만 노출.
* **범용성:** 특정 도메인에 종속되지 않고, 다양한 프로젝트에서 재사용 가능한 범용 프레임워크로 개발.



🔍 사용 가능한 옵션들

     1. Atlassian 공식 Remote MCP Server (권장)

     - 상태: 2024년 베타 출시, Claude 공식 파트너
     - 지원 플랫폼: Claude Teams/Enterprise
     - 기능: 
       - Jira 이슈 조회/생성/수정
       - 자연어로 티켓 생성
       - 대량 작업 처리 가능
     - 보안: OAuth 2.1 기반, HTTPS 암호화

     2. 커뮤니티 MCP Server들

     - MankowskiNick's jira-mcp
     - Composio's Jira MCP  
     - sooperset's mcp-atlassian

     🚀 구현 계획

     Phase 1: Jira MCP 연동 설정

     1. Claude Teams 계정 확인
       - 현재 Claude 플랜 레벨 확인
       - Teams/Enterprise 필요시 업그레이드 검토
     2. Atlassian Remote MCP Server 설정
       - Atlassian Cloud 사이트 연결
       - OAuth 2.1 인증 설정
       - Jira 권한 확인 및 설정

     Phase 2: 자동화 워크플로우 구축

     1. Jira 프로젝트 생성
     프로젝트 키: CUPIA_EDPF
     프로젝트 유형: Scrum
     Epic 구조: E1(기반구조) → E2(코어) → E3(확장) → E4(프레임워크)
     2. 자동 이슈 생성 시스템
       - Claude가 개발 진행사항을 Jira에 자동 기록
       - Story/Task 자동 생성 및 상태 업데이트
       - 일일 작업 로그를 Jira 코멘트로 동기화

     Phase 3: 워크플로우 통합

     1. 개발 → Jira 자동 연동
       - 코드 커밋시 자동 이슈 업데이트
       - 테스트 결과를 Jira에 자동 기록
       - Sprint 진행상황 실시간 동기화
     2. 문서화 자동화  
       - Phase 완료 보고서를 Jira에 자동 첨부
       - 회고 내용을 Jira Epic에 자동 기록

     🎯 예상 효과

     - 수동 작업 80% 감소: 이슈 생성, 상태 업데이트 자동화
     - 실시간 추적: 개발 진행사항 즉시 Jira 반영  
     - 문서 일관성: Claude 작업 내용과 Jira 동기화
     - 팀 협업 향상: 실시간 진행 상황 공유

     📋 다음 단계

     1. Claude 플랜 레벨 확인
     2. Atlassian MCP Server 설정
     3. 테스트 프로젝트로 연동 검증
     4. CUPIA EDPF 프로젝트에 적용
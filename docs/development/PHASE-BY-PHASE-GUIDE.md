# CUPIA EDPF 단계별 개발 가이드

## 개발 단계 개요

### Phase 1: 기반 구조 구축 (Sprint 1-2)
**목표**: 개발 환경 설정 및 기본 이벤트 처리 인프라 구축

### Phase 2: Simple 구현체 개발 (Sprint 3-4) 
**목표**: @Service 기반 EventProcessor 완전 구현

### Phase 3: 확장 구조 준비 (Sprint 5-6)
**목표**: Spring Batch 통합 및 하이브리드 구조 완성

### Phase 4: 프레임워크화 (Sprint 7-8)
**목표**: 배포 가능한 Starter 모듈 및 문서 완성

---

## Phase 1: 기반 구조 구축

### Sprint 1 목표
**Jira Epic**: `[EDPF-E1]` 프로젝트 기반 구조 구축  
**Sprint Goal**: Kafka 기반 이벤트 수신 구조 완성

#### 주요 Story
1. **`[EDPF-1]` 프로젝트 기본 구조 생성**
   - **AC**: Gradle 빌드 성공, 기본 패키지 구조 생성
   - **Tasks**:
     - `build.gradle` 의존성 설정
     - 패키지 구조 생성 (`com.cupia.framework.batch.*`)
     - 기본 Application 클래스 생성

2. **`[EDPF-2]` Spring Cloud Streams Kafka 연동**
   - **AC**: Kafka에서 테스트 이벤트 수신 가능
   - **Tasks**:
     - `application.yml` Kafka 설정
     - 기본 이벤트 리스너 구현
     - Kafka 연동 테스트

3. **`[EDPF-3]` 테스트 인프라 구축**
   - **AC**: TestContainers 기반 통합 테스트 실행 가능
   - **Tasks**:
     - TestContainers Kafka 설정
     - 기본 통합 테스트 템플릿 생성

### Sprint 1 완료 기준
- [ ] Kafka에서 이벤트 수신하여 로그 출력 확인
- [ ] 통합 테스트 실행 성공
- [ ] 코드 커버리지 80% 이상
- [ ] 모든 설정 파일 문서화 완료

### 산출물
- `src/main/java/com/cupia/framework/batch/` 패키지 구조
- `application.yml` 기본 설정
- `KafkaEventListenerTest` 통합 테스트
- `docs/development/phase1-completion-report.md`

---

## Phase 2: Simple 구현체 개발

### Sprint 3-4 목표  
**Jira Epic**: `[EDPF-E2]` 이벤트 프로세싱 코어 모듈 개발
**Sprint Goal**: @Service 기반 이벤트 처리 완전 구현

#### 주요 Story
1. **`[EDPF-10]` EventProcessor 인터페이스 설계**
2. **`[EDPF-11]` SimpleEventProcessor 구현** 
3. **`[EDPF-12]` 설정 기반 프로세서 선택 시스템**
4. **`[EDPF-13]` 트랜잭션 및 에러 처리**

### 완료 기준
- [ ] 실제 비즈니스 로직으로 이벤트 처리 가능
- [ ] 설정 파일로 프로세서 타입 선택 가능
- [ ] 트랜잭션 롤백 테스트 통과
- [ ] 성능 테스트 (초당 1000건 처리) 통과

---

## Phase 3: 확장 구조 준비

### Sprint 5-6 목표
**Jira Epic**: `[EDPF-E3]` Spring Batch 통합 모듈 개발  
**Sprint Goal**: 하이브리드 구조로 두 가지 방식 모두 지원

#### 주요 Story
1. **`[EDPF-20]` BatchEventProcessor 인터페이스 설계**
2. **`[EDPF-21]` JobLauncher 통합 구현**
3. **`[EDPF-22]` ProcessorFactory 패턴 구현**
4. **`[EDPF-23]` 설정 기반 동적 전환 시스템**

### 완료 기준
- [ ] 설정 변경만으로 Simple ↔ Batch 전환 가능
- [ ] Spring Batch Job 실행 및 재시작 테스트 통과
- [ ] 두 방식 모두 동일한 인터페이스로 사용 가능
- [ ] 성능 벤치마크 비교 문서 완성

---

## Phase 4: 프레임워크화

### Sprint 7-8 목표
**Jira Epic**: `[EDPF-E4]` 프레임워크화 및 배포
**Sprint Goal**: 재사용 가능한 Spring Boot Starter 완성

#### 주요 Story  
1. **`[EDPF-30]` Auto-configuration 구현**
2. **`[EDPF-31]` Starter 모듈 패키징**
3. **`[EDPF-32]` 사용자 가이드 문서 작성**
4. **`[EDPF-33]` 예제 애플리케이션 개발**

### 완료 기준
- [ ] Maven Central 또는 내부 저장소 배포 완료
- [ ] 예제 애플리케이션으로 5분 내 동작 확인 가능
- [ ] API 문서 및 사용 가이드 완성
- [ ] 다른 팀에서 독립적으로 사용 가능

---

## 각 Phase 공통 작업

### 문서화 의무사항
각 Phase 완료시 다음 문서들을 반드시 업데이트:

1. **기술 문서**
   - `docs/development/phase{N}-completion-report.md`
   - API 문서 업데이트
   - 설정 가이드 업데이트

2. **프로젝트 관리 문서**  
   - `docs/progress-tracking/weekly-reports/`
   - 스프린트 회고록
   - 다음 Phase 계획 업데이트

### 품질 관리
- 매 Sprint 종료시 코드 커버리지 80% 이상 유지
- SonarQube 품질 게이트 통과
- 성능 회귀 테스트 통과
- 보안 취약점 점검 완료

### 리스크 관리  
- 매주 리스크 체크리스트 업데이트
- 블로커 이슈는 24시간 내 에스컬레이션
- 기술 부채는 매 Phase마다 정리

이 가이드를 바탕으로 체계적이고 추적 가능한 개발을 진행하겠습니다.
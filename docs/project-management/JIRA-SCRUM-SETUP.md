# CUPIA EDPF Jira 스크럼 프로젝트 설정 가이드

## 1. Jira 프로젝트 생성

### 1.1 기본 설정
- **프로젝트 유형**: Scrum
- **프로젝트명**: CUPIA Event-Driven Processing Framework
- **프로젝트 키**: `CUPIA_EDPF`
- **프로젝트 리드**: [담당자명]

### 1.2 Epic 구성
프로젝트의 주요 Epic들을 다음과 같이 생성합니다:

#### `[EDPF-E1]` 프로젝트 기반 구조 구축
- **목표**: 개발 환경 및 기본 프레임워크 구조 설정
- **예상 기간**: 1-2 Sprint

#### `[EDPF-E2]` 이벤트 프로세싱 코어 모듈 개발  
- **목표**: EventProcessor 인터페이스 및 Simple 구현체 개발
- **예상 기간**: 2-3 Sprint

#### `[EDPF-E3]` Spring Batch 통합 모듈 개발
- **목표**: BatchEventProcessor 및 확장 가능한 구조 구현
- **예상 기간**: 2-3 Sprint

#### `[EDPF-E4]` 프레임워크화 및 배포
- **목표**: Auto-configuration, Starter 모듈, 문서화
- **예상 기간**: 1-2 Sprint

## 2. 스프린트 계획

### Sprint 구성
- **스프린트 기간**: 2주
- **스프린트 목표**: 각 스프린트마다 동작하는 기능 완성
- **데일리 스탠드업**: 매일 오전 9시 (15분)
- **스프린트 리뷰**: 격주 금요일 오후 2시
- **회고**: 스프린트 리뷰 후 진행

### Sprint 1 목표 (Phase 1)
```
Epic: [EDPF-E1] 프로젝트 기반 구조 구축
Goal: 개발 환경 설정 및 기본 Kafka 연동 구조 완성

Stories:
- [EDPF-1] Gradle 프로젝트 구조 생성 및 의존성 설정
- [EDPF-2] Spring Boot + Spring Cloud Streams 기본 설정
- [EDPF-3] Kafka 연동 및 기본 이벤트 리스너 구현
- [EDPF-4] 테스트 환경 구축 (TestContainers)
```

## 3. 이슈 관리 규칙

### 이슈 유형 분류
- **Epic**: 대규모 기능 단위 (예: 코어 모듈 개발)
- **Story**: 사용자 스토리 기반 기능 (예: 이벤트 처리기 구현)
- **Task**: 기술적 작업 (예: 설정 파일 작성)
- **Bug**: 결함 수정
- **Spike**: 기술 조사 및 연구

### 우선순위 기준
- **Highest**: 프로젝트 진행 블로커
- **High**: 현재 스프린트 필수 완료
- **Medium**: 현재 스프린트 가능하면 완료
- **Low**: 다음 스프린트에 고려
- **Lowest**: 여유시간에 처리

### 상태 관리
```
TODO → IN PROGRESS → CODE REVIEW → TESTING → DONE
```

## 4. 백로그 관리

### Definition of Ready (DoR)
Story가 스프린트에 포함되기 위한 조건:
- [ ] 명확한 인수 기준(Acceptance Criteria) 정의
- [ ] 기술적 의존성 파악 완료
- [ ] 예상 스토리 포인트 산정 완료
- [ ] 관련 문서 또는 참고 자료 첨부

### Definition of Done (DoD)
Story가 완료되기 위한 조건:
- [ ] 기능 구현 완료
- [ ] 단위 테스트 작성 (커버리지 80% 이상)
- [ ] 통합 테스트 통과
- [ ] 코드 리뷰 완료
- [ ] 문서 업데이트 완료
- [ ] 관련 설정 파일 업데이트

## 5. 보고 및 추적

### 번다운 차트 활용
- 매일 스프린트 진행 상황 추적
- 예상 vs 실제 진행률 비교

### 속도 추적 (Velocity Tracking)
- 스프린트별 완료된 스토리 포인트 기록
- 팀 평균 속도 계산 및 다음 스프린트 계획에 반영

### 주간 진행 보고서
매주 금요일 진행 상황 문서 업데이트:
- `docs/progress-tracking/weekly-reports/`

## 6. 협업 규칙

### 코드 리뷰 프로세스
1. 개발자가 PR 생성
2. Jira 이슈 번호를 PR 제목에 포함 (`[EDPF-123] 기능 구현`)
3. 최소 1명 이상의 리뷰어 승인 필요
4. 모든 테스트 통과 확인
5. 메인 브랜치에 머지

### 브랜치 전략
```
main (운영)
├── develop (개발)
│   ├── feature/EDPF-123-event-processor
│   ├── feature/EDPF-124-kafka-integration
│   └── bugfix/EDPF-125-config-issue
```

### 커밋 메시지 규칙
```
[EDPF-123] feat: EventProcessor 인터페이스 구현

- EventProcessor<T> 제네릭 인터페이스 정의
- 기본 예외 처리 구조 추가
- 단위 테스트 작성

Closes EDPF-123
```
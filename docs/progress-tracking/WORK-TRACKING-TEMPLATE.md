# CUPIA EDPF 작업 추적 템플릿

## 일일 작업 추적 (Daily Tracking)

### 템플릿 사용법
매일 작업 시작 전에 다음 템플릿을 복사하여 `daily-logs/YYYY-MM-DD.md` 파일을 생성합니다.

```markdown
# 일일 작업 로그 - YYYY-MM-DD

## Today's Plan
- [ ] [EDPF-XX] 작업 제목
- [ ] [EDPF-XX] 작업 제목  
- [ ] [EDPF-XX] 작업 제목

## Progress Updates
### [EDPF-XX] 작업 제목
- **Status**: IN PROGRESS / COMPLETED / BLOCKED
- **Time Spent**: X hours
- **What I Did**: 구체적인 작업 내용
- **Next Steps**: 다음에 할 일
- **Issues/Blockers**: 발생한 문제나 장애요소

### [EDPF-XX] 작업 제목  
- **Status**: 
- **Time Spent**: 
- **What I Did**:
- **Next Steps**:
- **Issues/Blockers**:

## Tomorrow's Plan
- [ ] [EDPF-XX] 작업 제목
- [ ] [EDPF-XX] 작업 제목

## Notes & Learning
- 오늘 배운 것이나 중요한 메모
- 기술적 인사이트나 결정사항
```

---

## 주간 스프린트 리포트 (Weekly Sprint Report)

### 템플릿 위치
`weekly-reports/sprint-{N}-week-{W}.md`

```markdown
# Sprint {N} Week {W} Report

## Sprint 정보
- **Sprint**: Sprint {N}
- **Period**: YYYY-MM-DD ~ YYYY-MM-DD
- **Sprint Goal**: [스프린트 목표]

## 이번 주 완료 작업
### Completed Stories
- [x] [EDPF-XX] 스토리 제목 (SP: X)
  - 완료 내용 요약
  - 주요 산출물: `파일명.java`, `설정파일.yml`
  - 테스트 결과: PASS/FAIL

- [x] [EDPF-XX] 스토리 제목 (SP: X)
  - 완료 내용 요약

### Completed Tasks  
- [x] [EDPF-XX] 태스크 제목
- [x] [EDPF-XX] 태스크 제목

## 진행 중 작업
### In Progress Stories
- [ ] [EDPF-XX] 스토리 제목 (SP: X, Progress: 60%)
  - 현재 상황: 
  - 예상 완료: YYYY-MM-DD

## 이슈 및 블로커
### Blockers
- **[EDPF-XX]**: 블로커 설명
  - Impact: HIGH/MEDIUM/LOW
  - 해결 방안:
  - 담당자:

### Risks
- **리스크 설명**:
  - Probability: HIGH/MEDIUM/LOW  
  - Impact: HIGH/MEDIUM/LOW
  - 완화 방안:

## 다음 주 계획
### Next Week Goals
- [ ] [EDPF-XX] 스토리 제목
- [ ] [EDPF-XX] 스토리 제목

### Key Focus Areas
1. 주요 집중 영역 1
2. 주요 집중 영역 2

## 메트릭스
### 개발 메트릭스
- **Stories Completed**: X개
- **Story Points Completed**: X점
- **Code Coverage**: X%
- **Technical Debt**: X시간

### 품질 메트릭스  
- **Bugs Found**: X개
- **Bugs Fixed**: X개
- **Code Review Comments**: X개
- **Build Success Rate**: X%

## 회고 (Retrospective)
### What Went Well ✅
- 잘된 점 1
- 잘된 점 2

### What Didn't Go Well ❌  
- 개선이 필요한 점 1
- 개선이 필요한 점 2

### Action Items 🎯
- [ ] 개선 액션 1 (담당자: XXX, 기한: YYYY-MM-DD)
- [ ] 개선 액션 2 (담당자: XXX, 기한: YYYY-MM-DD)
```

---

## Jira 이슈 추적 체크리스트

### Story 생성시 체크리스트
- [ ] Epic에 연결되어 있는가?
- [ ] 명확한 인수 기준(AC) 정의됨
- [ ] Story Point 산정 완료  
- [ ] 라벨 적절히 설정 (frontend, backend, testing 등)
- [ ] 컴포넌트 할당 완료
- [ ] 우선순위 설정 완료

### Story 진행 중 체크리스트
- [ ] 브랜치 생성 (`feature/EDPF-{N}-{description}`)
- [ ] 일일 시간 로그 업데이트
- [ ] 진행 상황 코멘트 추가
- [ ] 관련 문서 업데이트
- [ ] 테스트 케이스 작성

### Story 완료시 체크리스트  
- [ ] 모든 AC 완료 확인
- [ ] 단위 테스트 작성 및 통과
- [ ] 통합 테스트 통과
- [ ] 코드 리뷰 승인 받음
- [ ] PR 머지 완료
- [ ] 문서 업데이트 완료
- [ ] Story를 DONE으로 이동

---

## 파일 구조

```
docs/progress-tracking/
├── WORK-TRACKING-TEMPLATE.md (이 파일)
├── daily-logs/
│   ├── 2024-01-15.md
│   ├── 2024-01-16.md
│   └── ...
├── weekly-reports/
│   ├── sprint-1-week-1.md
│   ├── sprint-1-week-2.md
│   └── ...
├── sprint-retrospectives/
│   ├── sprint-1-retrospective.md
│   ├── sprint-2-retrospective.md
│   └── ...
└── metrics/
    ├── velocity-chart.md
    ├── burndown-data.md
    └── quality-metrics.md
```

이 템플릿들을 활용하여 모든 작업을 체계적으로 추적하고 관리하겠습니다.
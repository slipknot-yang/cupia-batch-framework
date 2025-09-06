# Git Flow Guidelines

CUPIA Batch Framework 프로젝트의 Git Flow 워크플로우 가이드라인입니다.

## 🌿 브랜치 구조

### Main Branches
- **`main`**: 프로덕션 준비된 안정 버전 (릴리즈 전용)
- **`development`**: 개발 통합 브랜치 (다음 릴리즈 준비)

### Supporting Branches
- **`feature/*`**: 새로운 기능 개발
- **`release/*`**: 릴리즈 준비 및 버그 수정
- **`hotfix/*`**: 프로덕션 긴급 수정
- **`bugfix/*`**: 개발 브랜치 버그 수정

## 🚀 워크플로우

### Feature 개발
```bash
# development 브랜치에서 feature 브랜치 생성
git checkout development
git pull origin development
git checkout -b feature/EDPF-XX-description

# 개발 작업 수행
git add .
git commit -m "feat: implement feature description"

# development로 merge
git checkout development
git merge --no-ff feature/EDPF-XX-description
git push origin development
git branch -d feature/EDPF-XX-description
```

### Release 준비
```bash
# development에서 release 브랜치 생성
git checkout development
git checkout -b release/v0.1.0

# 릴리즈 준비 (버전 업데이트, 문서화 등)
git commit -m "chore: prepare release v0.1.0"

# main으로 merge
git checkout main
git merge --no-ff release/v0.1.0
git tag -a v0.1.0 -m "Release version 0.1.0"

# development로도 merge
git checkout development
git merge --no-ff release/v0.1.0

# 브랜치 정리
git branch -d release/v0.1.0
```

### Hotfix 처리
```bash
# main에서 hotfix 브랜치 생성
git checkout main
git checkout -b hotfix/v0.1.1-critical-bug

# 긴급 수정 작업
git commit -m "fix: resolve critical production bug"

# main과 development 모두에 merge
git checkout main
git merge --no-ff hotfix/v0.1.1-critical-bug
git tag -a v0.1.1 -m "Hotfix version 0.1.1"

git checkout development
git merge --no-ff hotfix/v0.1.1-critical-bug

git branch -d hotfix/v0.1.1-critical-bug
```

## 📋 브랜치 명명 규칙

### Feature Branches
- `feature/EDPF-XX-short-description`
- 예: `feature/EDPF-5-kafka-event-listener`

### Release Branches
- `release/vX.Y.Z`
- 예: `release/v0.1.0`

### Hotfix Branches
- `hotfix/vX.Y.Z-description`
- 예: `hotfix/v0.1.1-kafka-connection-fix`

### Bugfix Branches
- `bugfix/EDPF-XX-description`
- 예: `bugfix/EDPF-25-configuration-error`

## 💬 커밋 메시지 규칙

### Format
```
<type>(<scope>): <description>

<body>

<footer>
```

### Types
- `feat`: 새로운 기능
- `fix`: 버그 수정
- `docs`: 문서 변경
- `style`: 코드 스타일 변경
- `refactor`: 코드 리팩토링
- `test`: 테스트 추가/수정
- `chore`: 빌드/설정 변경

### Examples
```
feat(core): implement EventProcessor interface

- Add generic EventProcessor<T> interface
- Support for both simple and batch processing
- Include transaction management capabilities

Closes #EDPF-10
```

## 🔄 Pull Request 규칙

### PR Title
- `[EDPF-XX] Brief description of changes`
- 예: `[EDPF-5] Implement Kafka event listener`

### PR Description Template
```markdown
## 📋 Summary
Brief description of changes

## 🔗 Related Issues
- Closes #EDPF-XX

## 📝 Changes
- [ ] Feature implementation
- [ ] Tests added/updated
- [ ] Documentation updated

## 🧪 Testing
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed

## 📚 Checklist
- [ ] Code follows project conventions
- [ ] No breaking changes (or documented)
- [ ] Documentation updated
```

## 🏷️ 태그 규칙

### Version Tags
- `v<major>.<minor>.<patch>`
- 예: `v0.1.0`, `v1.0.0`, `v1.2.3`

### Pre-release Tags
- `v<version>-<pre-release>`
- 예: `v0.1.0-alpha.1`, `v1.0.0-rc.1`

## 🛡️ 브랜치 보호 규칙

### Main Branch
- Direct push 금지
- Pull Request 필수
- 최소 1명 승인 필요
- Status check 통과 필요

### Development Branch
- Direct push 허용 (팀 내부)
- Pull Request 권장
- CI/CD 체크 통과 필요

## 🔧 현재 프로젝트 구조

### 구축 완료 사항
- **`main`**: 안정화된 릴리즈 브랜치 (프로덕션 준비)
- **`development`**: 개발 통합 브랜치 (다음 릴리즈 준비)
- **`.gitignore`**: 빌드 아티팩트, IDE 파일 제외 설정
- **Git Flow 가이드라인**: 완전한 워크플로우 문서화

### 개발 환경 설정
- Spring Boot 3.5.3 (최신 안정화 버전)
- Spring Cloud 2025.0.0 (Northfields 릴리즈)
- Java 21 기반 라이브러리 프레임워크
- Context7 MCP를 통한 최신 버전 관리

## 🎯 앞으로의 작업 방식

### 새 기능 개발
```bash
# development 브랜치에서 시작
git checkout development
git pull origin development

# 새 기능 브랜치 생성 (JIRA 이슈 기반)
git checkout -b feature/EDPF-XX-description

# 개발 작업 수행
# - 코드 구현
# - 테스트 작성
# - 문서 업데이트

# 커밋 및 푸시
git add .
git commit -m "feat: implement new feature"
git push origin feature/EDPF-XX-description

# Pull Request 생성하여 development로 머지
# GitHub에서 PR 생성 → 리뷰 → development 브랜치로 머지
```

### 릴리즈 준비
```bash
# development에서 release 브랜치 생성
git checkout development
git pull origin development
git checkout -b release/v0.1.0

# 릴리즈 준비 작업
# - 버전 업데이트 (build.gradle)
# - 문서 최종 점검
# - 릴리즈 노트 작성

# main과 development에 모두 머지 후 태깅
git checkout main
git merge --no-ff release/v0.1.0
git tag -a v0.1.0 -m "Release version 0.1.0"
git push origin main --tags

git checkout development
git merge --no-ff release/v0.1.0
git push origin development
```

### 작업 계획 변경/추가 시 동기화
모든 작업 계획이 변경되거나 추가되면 다음 순서로 동기화:
1. **로컬 문서** 업데이트 (계획, 가이드, 로그)
2. **Jira** 동기화 (Epic, Story, Task 생성/수정)  
3. **GitHub** 커밋 & 푸시
4. **다음 작업** 진행

## 🚨 주의사항

1. **Main 브랜치 직접 수정 금지**
   - 모든 변경사항은 Pull Request를 통해서만

2. **Feature 브랜치는 최신 Development 기준으로 생성**
   - 충돌 최소화 위해 정기적으로 rebase

3. **Commit은 논리적 단위로 분할**
   - 하나의 커밋은 하나의 변경사항만

4. **테스트 실패 시 머지 금지**
   - 모든 테스트 통과 후에만 머지

5. **Context7 MCP 활용**
   - 최신 버전 확인 및 공식 문서 참조
   - 버전 업데이트 시 항상 Context7로 호환성 검증

이 가이드라인을 따라 안정적이고 체계적인 개발 워크플로우를 유지합니다.
# 📚 Book Search

안드로이드 책 검색 앱으로, 카카오 책 검색 API를 활용하여 도서를 검색하고 즐겨찾기 기능을 제공합니다.

## 🚀 주요 기능

- **도서 검색**: 제목, 저자명으로 도서 검색
- **정렬 기능**: 정확도순, 발간일순 정렬
- **필터링**: 가격대, 출판사별 필터링
- **즐겨찾기**: 관심 있는 도서를 즐겨찾기에 추가/제거
- **상세 정보**: 도서의 상세 정보 확인
- **무한 스크롤**: 검색 결과 페이지네이션

## 🛠 기술 스택

### 프레임워크 & 라이브러리
- **언어**: Kotlin
- **UI**: Jetpack Compose
- **아키텍처**: Clean Architecture (MVVM)
- **의존성 주입**: Service Locator 패턴
- **네트워킹**: Ktor Client
- **로컬 데이터베이스**: Room
- **이미지 로딩**: Coil
- **네비게이션**: Navigation Compose
- **비동기 처리**: Kotlin Coroutines + Flow

### 개발 도구
- **빌드 도구**: Gradle (Kotlin DSL)
- **버전 관리**: Version Catalog
- **코드 생성**: KSP (Kotlin Symbol Processing)

## 📁 프로젝트 구조

```
book-search/
├── app/                    # 애플리케이션 모듈
│   ├── src/main/java/
│   │   ├── MainActivity.kt
│   │   ├── ServiceLocator.kt
│   │   └── ui/theme/       # 테마 관련 파일들
│   └── build.gradle.kts
├── domain/                 # 도메인 모듈 (비즈니스 로직)
│   └── src/main/kotlin/
│       └── com/mygomii/booksearch/domain/
│           ├── model/      # 도메인 모델
│           ├── repository/ # 리포지토리 인터페이스
│           └── usecase/    # 유스케이스
├── data/                   # 데이터 모듈 (데이터 소스)
│   └── src/main/java/
│       └── com/mygomii/booksearch/data/
│           ├── local/      # Room 데이터베이스
│           ├── remote/     # API 클라이언트
│           ├── mapper/     # DTO ↔ Domain 변환
│           └── repository/ # 리포지토리 구현체
├── presentation/           # 프레젠테이션 모듈 (UI)
│   └── src/main/java/
│       └── com/mygomii/booksearch/presentation/
│           ├── search/     # 검색 화면
│           ├── favorites/  # 즐겨찾기 화면
│           ├── detail/     # 상세 화면
│           ├── navigation/ # 네비게이션
│           └── util/       # 유틸리티
└── gradle/
    └── libs.versions.toml  # 의존성 버전 관리
```

## 🏗 아키텍처

### Clean Architecture 적용
```
┌─────────────────┐
│   Presentation  │ ← UI Layer (Compose + ViewModel)
├─────────────────┤
│     Domain      │ ← Business Logic Layer
├─────────────────┤
│      Data       │ ← Data Layer (Repository + DataSource)
└─────────────────┘
```

### 모듈 의존성
- `app` → `presentation` → `domain` ← `data`
- 각 모듈은 단방향 의존성을 유지
- 도메인 모듈은 외부 의존성 없음

## 🔧 빌드 방법

### 사전 요구사항
- Android Studio Hedgehog (2023.1.1) 이상
- JDK 17 이상
- Android SDK API 24 이상

### 1. 저장소 클론
```bash
git clone https://github.com/your-username/book-search.git
cd book-search
```

### 2. API 키 설정
`local.properties` 파일을 생성하고 카카오 API 키를 추가합니다:

```properties
KAKAO_API_KEY=your_kakao_api_key_here
```

카카오 API 키 발급 방법:
1. [카카오 개발자 콘솔](https://developers.kakao.com/) 접속
2. 애플리케이션 등록
3. REST API 키 복사

### 3. 프로젝트 빌드
```bash
# Debug 빌드
./gradlew assembleDebug

# Release 빌드
./gradlew assembleRelease
```

### 4. 앱 실행
```bash
# 디바이스에 설치 및 실행
./gradlew installDebug
```

## 📱 주요 구현 포인트

### 1. Clean Architecture
- **Domain Layer**: 비즈니스 로직과 엔티티 정의
- **Data Layer**: Repository 패턴으로 데이터 소스 추상화
- **Presentation Layer**: Compose + ViewModel로 UI 상태 관리

### 2. 상태 관리
```kotlin
data class SearchUiState(
    val query: String = "",
    val sort: SortType = SortType.ACCURACY,
    val items: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val favoriteIsbns: Set<String> = emptySet()
)
```

### 3. 네트워킹
- **Ktor Client**: HTTP 클라이언트
- **Kotlinx Serialization**: JSON 직렬화/역직렬화
- **에러 핸들링**: `runCatching`으로 안전한 네트워크 호출

### 4. 로컬 데이터베이스
- **Room**: SQLite 추상화
- **Flow**: 실시간 데이터 관찰
- **마이그레이션**: 스키마 변경 대응

### 5. UI/UX
- **Material 3**: 최신 디자인 시스템
- **반응형 UI**: 다양한 화면 크기 대응
- **로딩 상태**: 사용자 경험 개선

## 🔄 데이터 흐름

1. **검색 요청**: 사용자가 검색어 입력
2. **ViewModel**: UI 상태 업데이트 및 UseCase 호출
3. **UseCase**: 비즈니스 로직 처리
4. **Repository**: 데이터 소스 선택 (API 또는 로컬 DB)
5. **API 호출**: 카카오 책 검색 API 요청
6. **데이터 변환**: DTO → Domain 모델 변환
7. **UI 업데이트**: Compose 리컴포지션

## 🧪 테스트

```bash
# Unit 테스트 실행
./gradlew test

# Instrumented 테스트 실행
./gradlew connectedAndroidTest
```

## 📦 의존성 관리

Version Catalog를 사용하여 의존성을 중앙 집중식으로 관리:

```toml
[versions]
kotlin = "2.0.21"
composeBom = "2024.09.00"
ktor = "2.3.12"
room = "2.6.1"

[libraries]
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
```

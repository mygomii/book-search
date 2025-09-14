# Book Search

## 주요 기능

- 도서 검색: 제목/저자 키워드 검색, 페이지네이션
- 정렬: 정확도순/발간일순 전환
- 필터: 최소/최대 가격, 출판사
- 즐겨찾기: 추가/삭제 및 정렬(추가일/제목)
- 상세보기: 서지정보/가격/소개 확인

## 사용 프레임워크/라이브러리

- Kotlin, Kotlin Coroutines/Flow
- Jetpack Compose, Material3, Navigation Compose
- Coil (이미지 로딩)
- Ktor Client (REST API)
- Room (로컬 저장소)
- Gradle Kotlin DSL, Version Catalog, Convention Plugins(build-logic)

## 모듈 구조

```
book-search/
├── app/                # 앱 엔트리, BuildConfig(API 키)
├── domain/             # 순수 비즈니스 로직(모델/UseCase/Repo 인터페이스)
├── data/               # 원격(Ktor)/로컬(Room) + Repository 구현
├── presentation/       # 화면, ViewModel, 네비게이션
├── designsystem/       # 재사용 가능한 Compose 컴포넌트/리소스
├── build-logic/        # 컨벤션 플러그인(의존성/Android 설정)
└── gradle/libs.versions.toml
```

의존성 방향
- app → presentation → domain
- data → domain
- designsystem은 presentation에서 사용

## 디자인 시스템(Design System)

재사용 가능한 Compose 컴포넌트를 별도 모듈로 분리했습니다.

- 기본 컴포넌트: `DsText`, `DsOutlinedTextField`, `DsAsyncImage`, `DsIconButton`, `DsButton`, `DsTopAppBar`
- 복합 컴포넌트: `BookListItem`(썸네일/제목/저자/가격/즐겨찾기 토글), `FilterPanel`(가격/출판사 필터)
- 문자열 리소스: 하드코딩 문자열 제거 및 `strings.xml`로 관리

## build-logic(컨벤션 플러그인)으로 의존성 중앙 관리

각 모듈은 외부 라이브러리 의존성을 개별 `build.gradle.kts`에 직접 선언하지 않고, `build-logic`의 플러그인으로 주입합니다.

- com.mygomii.app.deps: 앱 공통(Compose, 테스트, 툴링 등)
- com.mygomii.presentation.deps: 프리젠테이션 UI 의존성(Compose, Navigation, Coil 등)
- com.mygomii.designsystem.deps: 디자인시스템 의존성(Compose, Coil)
- com.mygomii.data.deps: Ktor/Room/KSP
- com.mygomii.domain.deps: 코루틴

장점
- 버전/의존성 정의의 단일화 및 일관성 유지
- 모듈 빌드 스크립트 단순화(프로젝트 의존만 기술)

## 빌드/실행 방법

사전 요구사항
- Android Studio Koala 이상, JDK 17, Android SDK 24+

API 키 설정
- `local.properties`에 `KAKAO_API_KEY` 추가 (또는 환경변수로 설정)
```properties
KAKAO_API_KEY=your_kakao_api_key
```
앱 모듈은 `BuildConfig.KAKAO_API_KEY`로 주입된 값을 사용합니다.

빌드
```bash
# 전체 디버그 빌드
./gradlew assembleDebug

# 모듈별 빌드 예시
./gradlew :presentation:assembleDebug :data:assembleDebug :designsystem:assembleDebug
```

실행
```bash
./gradlew installDebug
```

## 아키텍처/구현 포인트

- Clean Architecture 스타일의 모듈 분리(단방향 의존성)
- ViewModel + State(Flow)로 Compose 상태 관리 및 상태호이스팅
- 네트워킹: Ktor + kotlinx.serialization + ContentNegotiation
- 영속화: Room(Entity/Dao) + Flow로 즐겨찾기 관찰
- 이미지: Coil의 AsyncImage를 DS 래퍼(`DsAsyncImage`)로 캡슐화
- 내비게이션: Navigation Compose, 단순 라우트 문자열로 화면 전환
- 국제화: 모든 사용자 노출 문자열은 `strings.xml`로 관리
- 디자인시스템 래퍼로 UI 일관성 확보(Text, OutlinedTextField, IconButton, Button, TopAppBar 등)

## 주요 코드 위치

- Präsentation
  - `presentation/.../search/SearchScreen.kt`: 검색 화면
  - `presentation/.../favorites/FavoritesScreen.kt`: 즐겨찾기 화면
  - `presentation/.../detail/DetailScreen.kt`: 상세 화면
  - `presentation/.../AppRoot.kt`: AppBar/BottomBar, NavHost
- Design System
  - `designsystem/.../BookListItem.kt`, `FilterPanel.kt`
  - `designsystem/.../Ds*.kt`: 공통 컴포넌트 래퍼
- Data
  - `data/.../remote`: Ktor API 클라이언트
  - `data/.../local`: Room DB/DAO
  - `data/.../repository`: Repository 구현
- Domain
  - `domain/.../model`, `usecase`, `repository`

## 카카오 API 키

키 발급은 [카카오 개발자 콘솔](https://developers.kakao.com/)에서 애플리케이션 생성 후 REST API 키를 발급받아 `local.properties` 또는 환경변수로 설정하세요.

---


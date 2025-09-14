# Book Search App – README

## 개요
- 카카오 도서 검색 API 기반의 안드로이드 샘플 앱입니다.
- Clean Architecture + 멀티 모듈 구성: `app`, `domain`, `data`, `presentation`, `build-logic`.
- UI는 Jetpack Compose, 네트워크는 Ktor, 로컬 저장은 Room을 사용합니다.

## 사용 프레임워크/라이브러리
- 언어/코어: Kotlin, Coroutines, Flow
- UI: Jetpack Compose (Material3, Navigation, Icons), Coil(이미지)
- 네트워크: Ktor Client + Kotlinx Serialization(Json)
- 로컬: Room (즐겨찾기 보관)
- 구조: Clean Architecture (Domain/Data/Presentation), 멀티모듈
- Gradle: AGP 8.13.0, Kotlin 2.0.21, Compose BOM 2024.09.00
- 빌드 로직: `build-logic`(Convention Plugins)

## 빌드/실행 방법
1) JDK 17 사용
- Android Studio에서 Gradle JDK 17 설정 (Gradle 8.x + AGP 8.x 호환)

2) Kakao API 키 설정
- `local.properties` 파일에 아래 항목 추가 (또는 환경변수 `KAKAO_API_KEY` 설정)
```
KAKAO_API_KEY=카카오_레스트_API_키
```

3) Gradle 빌드/설치
```
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```
- 최초 실행 시 검색 화면이 열리며 자동으로 ‘미움받을 용기’로 검색됩니다.

## 프로젝트 구조
```
book-search/
├─ app/                      # Application 모듈(Entry, ServiceLocator, AndroidManifest)
├─ domain/                   # 순수 Kotlin: 모델, Repository 인터페이스, UseCase
│  ├─ model/                 # Book, PagedBooks, SortType
│  ├─ repository/            # BookRepository
│  └─ usecase/               # SearchBooks/ToggleFavorite/ObserveFavorites
├─ data/                     # 구현체: 원격(Ktor), 로컬(Room), Repository Impl
│  ├─ remote/                # KakaoBookApi, DTOs, mappers
│  ├─ local/                 # Room(AppDatabase, Dao, Entity)
│  └─ repository/            # BookRepositoryImpl
├─ presentation/             # Compose UI, ViewModel, Navigation
│  ├─ search/                # 검색 화면 + VM
│  ├─ favorites/             # 즐겨찾기 화면 + VM
│  ├─ detail/                # 상세 화면
│  ├─ navigation/            # 라우트 정의
│  └─ util/                  # UI 유틸(가격 포맷 등)
├─ build-logic/              # Gradle Convention Plugins
│  ├─ AndroidApplicationConventionPlugin.kt (id: com.mygomii.android.application)
│  ├─ AndroidLibraryConventionPlugin.kt     (id: com.mygomii.android.library)
│  └─ KotlinLibraryConventionPlugin.kt      (id: com.mygomii.kotlin.library)
└─ example.md                # 이 문서
```

## 화면 및 주요 기능
- 하단 탭바: ‘검색’, ‘즐겨찾기’
- 상단 탭바(공통 TopAppBar): 라우트별 타이틀/액션

1) 검색 리스트(탭1)
- 검색: 상단 입력(제목/저자), 키보드 Search로 실행
- 정렬: 정확도순/발간일 토글 (좌측 현재 정렬 텍스트, 우측 정렬 아이콘)
- 필터: 최소/최대 가격, 출판사 (토글형 패널)
- 목록: 카드형(썸네일, 제목, 출판사, 저자, 가격), 즐겨찾기 하트
- 페이징: 20개씩 ‘더 보기’

2) 즐겨찾기(탭2)
- 검색: 제목/저자 로컬 검색
- 정렬: ‘오늘추가’(최근 추가) / ‘제목’ 토글 (상단 텍스트+정렬 아이콘)
- 필터: 최소/최대 가격, 출판사
- 목록: 카드형, 하트 해제 시 즉시 제거

3) 상세(탭3)
- 상단 탭바: 뒤로가기, 하트(저장/해제)
- 본문: 제목 → (좌 표지 / 우 정보: 저자/출판사/출간일/ISBN/가격) → 책 소개(스크롤)

## 데이터/아키텍처 개요
- Domain
  - `BookRepository`: 추상화된 인터페이스
  - UseCase: `SearchBooks`, `ToggleFavorite`, `ObserveFavorites`
- Data
  - Remote: `KakaoBookApi`(Ktor), Kakao DTO → Domain 매핑
  - Local: Room `AppDatabase`/`BookDao`/`FavoriteBookEntity(addedAt)`
  - Impl: `BookRepositoryImpl`에서 원격 검색 + 로컬 즐겨찾기 제공
- Presentation
  - Compose + ViewModel 상태(Flow/StateFlow)
  - 공통 TopAppBar: 라우트별 타이틀/상태/액션
  - Coil로 이미지 처리(플레이스홀더 포함)

## 외부 API
- Kakao Developers – 도서 검색 API
- 엔드포인트: `GET https://dapi.kakao.com/v3/search/book`
- 헤더: `Authorization: KakaoAK {REST_API_KEY}`
- 주요 파라미터: `query`, `sort=accuracy|recency`, `page`, `size`, `target=title|person` 등

## 구성/환경 주입
- API 키 주입: `local.properties` → `BuildConfig.KAKAO_API_KEY`로 주입
- Service Locator
  - Room DB(`books.db`) + `KakaoBookApi`로 `BookRepositoryImpl` 구성
  - 추후 Hilt/Koin DI로 전환 용이

## 빌드 로직(build-logic)
- 공통 컨벤션 플러그인으로 compileSdk/minSdk/targetSdk, JVM Toolchain(17), Compose, BuildConfig 설정 중앙화
- 라이브러리 모듈 기본 Compose 비활성 → 필요한 모듈(presentation 등)에서만 compose 플러그인/feature 활성화

## 접근성/UX
- 터치 타겟/텍스트 계층/대비 고려(추가 튜닝 여지 있음)
- 썸네일 미존재 시 기본 이미지 제공
- 가격 표기 일관: `#,###원`

## 한계/향후 개선
- 네비게이션: 간단화를 위해 선택 항목은 임시 Holder 사용 → Nav Arg/Serializable 모델로 개선 권장
- 에러/로딩 UI: 최소 구현 → 스낵바/리트라이 강화 가능
- Paging3 미사용 → 필요 시 교체
- 테스트: 유닛/스냅샷/UiTest 추가 가능
- 정렬/필터 확장: 상세 조건(저자/ISBN 등) 추가 및 저장/복원

## 라이선스
- 샘플 용도. 내부/개인 학습 프로젝트로 사용 가능.

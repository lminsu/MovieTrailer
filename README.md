## CS 개념 정리
- [program memory](https://github.com/lminsu/MovieTrailer/issues/24#issue-2646278526)
- [virtual memory](https://github.com/lminsu/MovieTrailer/issues/24#issuecomment-2466444707)
- [user/kernel space, system call과 interrupt, context switch](https://github.com/lminsu/MovieTrailer/issues/24#issuecomment-2466461454)
- [jvm](https://github.com/lminsu/MovieTrailer/issues/24#issuecomment-2466520007)
## 프로젝트 설명
- 최신 해외 영화에 대한 설명 및 예고편을 보여주며, 스와이프를 통해 다른 영화의 설명 및 예고편도 볼 수 있습니다.

https://github.com/user-attachments/assets/eae4e972-bdd8-4071-af5e-a404fd4952c0


### 프로젝트 전체 구조
- 앱은 액티비티 하나(MainAcitivy)와 두 개의 프래그먼트(PagerFragment, MainFragment)로 이루어져 있습니다.
  - MainActivity
    - 최상위 액티비티이며 PagerFragment를 보여줍니다.
  - PagerFragment
    - ViewPager를 가지고 있어 스와이프 기능을 제공하며, 각 page에는 MainFragment가 있습니다.
    - PagerViewModel에서 영화 랭킹 api를 호출하여, 최신 영화 순위 정보를 받아옵니다.
  - MainFragment
    - 실제 영화 정보를 보여주는 뷰들을 가지고 있습니다.
    - MainViewModel에서 유튜브 재생을 위한 video key api 호출하며, 이 video key 응답과 영화 정보를 합하여 MainFragment를 구성합니다.

![image](https://github.com/user-attachments/assets/a073ab51-de53-4151-999f-b1a7290cc44e)


### 개발 주안점
- [Unidirectional Data Flow](https://developer.android.com/topic/architecture#recommended-app-arch) 및 [single source of truth](https://developer.android.com/topic/architecture#single-source-of-truth)를 준수하려고 노력했습니다.
  - unidirectional data flow
    - ui 레이어에서 데이터 레이어로, 단방향으로 이벤트 전달하도록 하려고 하였습니다.
    - 뷰에서는 뷰모델을 `직접 참조`하여 이벤트 전달하며, 뷰모델에서는 뷰로 데이터 전달은 StateFlow를 사용하였습니다.(`직접 참조 안 함`)
  - single source of truthe
    - MovieRepository 클래스에서만 Movie 인스턴스를, VideoRepository 클래스에서만 Video 인스턴스를 생성하도록 하도록 구현하였습니다.
- [share data between a parent and child fragment](https://developer.android.com/guide/fragments/communicate#share_data_between_a_parent_and_child_fragment)
  - 자식 프래그먼트에서 부모 프래그먼트의 데이터가 필요한 경우, 부모 뷰모델로 직접 접근하여 사용하였습니다.

### 프로젝트 추가 작업
- 현재 [kotlin → java로 마이그레이션](https://github.com/lminsu/MovieTrailer/issues/22) 진행 중입니다.

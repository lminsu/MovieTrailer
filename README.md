### 프로젝트 전체 구조
- MainActivity
  - 최상위 액티비티, PagerFragment를 가지고 있음
- PagerFragment
  - ViewPager를 가지고 있음
  - 영화 랭킹 api를 호출함
- MainFragment
  - 실제 영화 정보를 보여주는 뷰들을 가지고 있음
  - 유튜브 재생을 위한 video key api 호출

![image](https://github.com/user-attachments/assets/a073ab51-de53-4151-999f-b1a7290cc44e)


### 개발 주안점
- mvvm 및 single source of truth 준수
  - ui 레이어에서 데이터 레이어로, 단방향으로 이벤트 전달하도록 함
    - 뷰에서 뷰모델을 직접 참조하여 이벤트 전달
    - 뷰모델에서는 StateFlow를 사용하여 뷰로 데이터 전달
  - MovieRepository 클래스에서만 Movie 인스턴스를, VideoRepository 클래스엥서만 Video 인스턴스를 생성하도록 함
- 자식 프래그먼트에서 부모 프래그먼트의 데이터가 필요한 경우, 부모 프로그래먼트을 ViewModelStoreOwner로 사용

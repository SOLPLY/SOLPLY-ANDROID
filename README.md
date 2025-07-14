<img width="4935" height="1758" alt="img_solply_banner" src="https://github.com/user-attachments/assets/ec392ef6-04d3-4e3c-887f-f88c108bb9af" />

## 🗺️ 솔플리(Solply) 
**혼자보내는 여가가 일상이 된 지금, 나만의 방식과 취향에 맞는 시간을 보내고 싶은 사람들을 위한 장소 및 코스 큐레이션 서비스**


## 🔢 목차
[프로젝트 설명](#프로젝트-설명)</br>
[서비스 목표](#서비스-목표)</br>
[주요 기능](#주요-기능)</br>
[시연 영상](#시연-영상)</br>
[기술 및 아키텍쳐 선정](#기술-및-아키텍쳐-선정)</br>
[컨벤션 규칙 및 브랜치 전략](#컨벤션-규칙-및-브랜치-전략)</br>
[파트원 사진](#파트원-사진)</br>
[팀원별 역할 분담](#팀원별-역할-분담)</br>
[폴더링](#폴더링)</br>

## 🎥 프로젝트 설명 
**솔플리**는 혼자 노는 사람들을 위한 장소 및 코스 큐레이션 서비스입니다. 사용자는 목적, 취향, 상황에 맞는 혼놀 스팟과 로컬 기반 추천 코스를 쉽게 탐색하고, 혼자 보내는 시간의 만족도를 높일 수 있습니다.
</br></br>

## 🎤 서비스 목표
- 혼자서도 편안하게 머무를 수 있는 장소와 코스를 쉽게 찾고 사용자가 자신만의 리듬으로 시간을 설계할 수 있도록!
- 단순한 공간 추천을 넘어, 사용자가 혼자만의 시간을 능동적이고 즐겁게 보낼 수 있도록!


## 📍 주요 기능 
1. 취향 기반 온보딩 : 선호하는 지역과 장소 유형, 활동 취향을 수집합니다. 사용자의 선호 지역과 취향 키워드가 반영된 추천 컨텐츠를 노출시킵니다!</br>
2. 장소 & 코스 추천 : 사용자의 취향과 목적에 따른 혼놀 장소를 검색할 수 있습니다. 장소나 방문 목적, 기타 특징에 기반한 필터링 기능을 통해, 사용자 맞춤형 혼놀 코스를 추천합니다!</br>
3. 장소 & 코스 수집 : 마음에 드는 장소나 코스를 저장할 수 있습니다. 저장한 코스에 새로운 장소를 추가하거나 기존의 장소를 삭제할 수 있으며 코스의 순서를 바꾸며 사용자가 커스터마이징할 수 있습니다!</br></br>
<!--
## 📹 시연 영상

<table>
  <tr>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
  </tr>
  <tr>
    <td align="center"><b>스플래시</b></td>
    <td align="center"><b>로그인</b></td>
    <td align="center"><b>메인(장소)</b></td>
    <td align="center"><b>메인(코스)</b></td>
  </tr>
</table>
<table>
  <tr>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
  </tr>
  <tr>
    <td align="center"><b>오늘의 추천 장소</b></td>
    <td align="center"><b>장소 메인 필터링</b></td>
    <td align="center"><b>추가 옵션 필터링</b></td>
    <td align="center"><b>장소 상세</b></td>
  </tr>
</table>
<table>
  <tr>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
  </tr>
  <tr>
    <td salign="center"><b>장소 내 코스 추가</b></td>
    <td align="center"><b>코스 상세보기</b></td>
    <td align="center"><b>장소 리뷰</b></td>
    <td align="center"><b>검색 기본</b></td>
  </tr>
</table>
<table>
  <tr>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
    <td width="25%"><img src="" width="100%" /></td>
  </tr>
  <tr>
    <td align="center"><b>수집함(장소)</b></td>
    <td align="center"><b>수집함(코스)</b></td>
    <td align="center"><b>코스 순서 변경 및 삭제</b></td>
    <td align="center"><b>새 코스 저장</b></td>
  </tr>
</table>
</br>
-->
## ⚙️ 기술 및 아키텍쳐 선정
+ `IDE - Android Studio Meerkat`</br>
+ `UI - Jetpack Compose`</br>
+ `Architecture - SSA, MVI, Clean Architecture, Multi-module`</br>
+ `DI - Hilt`</br>
+ `Network - Retrofit + Serialization`</br>
+ `Asynchronous - Coroutine, Flow`</br>
+ `Jetpack - DataStore, Navigation`</br>
+ `lint - ktlint`</br>


## ❗ 컨벤션 규칙 및 브랜치 전략

**깃 컨벤션:**  [Git Convention](https://www.notion.so/sopt-official/2251e48dd960807fa05fdf002e730663) </br>
**브랜치 전략:**  [Branch Strategy](https://www.notion.so/sopt-official/2251e48dd96080ac81c6f335d0b9f2a1) </br></br>

<!--
## 👨‍👩‍👧‍👦 파트원 사진
<img src="" alt="img_yoo0_android" width="100%"/></br>
-->

## 👤 팀원별 역할 분담
|[![이석찬](https://github.com/user-attachments/assets/7786c446-2c59-4ca8-b749-bbf5b6b81d69)](https://github.com/leeseokchan00)|[![이나경](https://github.com/user-attachments/assets/666a586f-a569-4e15-bb59-999e78b0d762)](https://github.com/nagaeng)|[![임형석](https://github.com/user-attachments/assets/05be73a0-c935-42b8-9bc5-532351862fb4)](https://github.com/ImHyungsuk)|[![박시현](https://github.com/user-attachments/assets/44a5e045-6e80-4adb-9ce9-898164e9caa5)](https://github.com/88guri)|
|:---------:|:---------:|:---------:|:---------:|
|[이석찬](https://github.com/leeseokchan00)|[이나경](https://github.com/nagaeng)|[임형석](https://github.com/ImHyungsuk)|[박시현](https://github.com/88guri)|
|`지도`|`메인(장소)`|`수집함`|`온보딩, 메인(코스)`|


## 🗂️ 폴더링

```bash
├── Recordy
├── 📁:app
├── 📁:build-logic
│   ├── 📁 convention
├── 📁:core
│   ├── 🗂️ buildconfig
├── 📁:data
│   ├── 🗂️ course
│   ├── 🗂️ maps
│   ├── 🗂️ mypage
│   ├── 🗂️ oauth
│   ├── 🗂️ onboarding
│   ├── 🗂️ place
├── 📁:local
│   ├── 🗂️ oauth
│   ├── 🗂️ place
├── 📁:remote
│   ├── 🗂️ course
│   ├── 🗂️ maps
│   ├── 🗂️ mypage
│   ├── 🗂️ onboarding
│   ├── 🗂️ place
├── 📁:domain
│   ├── 🗂️ course
│   ├── 🗂️ maps
│   ├── 🗂️ mypage
│   ├── 🗂️ oauth
│   ├── 🗂️ onboarding
│   ├── 🗂️ place
├── 📁:feature
│   ├── 🗂️ course
│   ├── 🗂️ maps
│   ├── 🗂️ mypage
│   ├── 🗂️ oauth
│   ├── 🗂️ onboarding
│   ├── 🗂️ place
├── 📁:gradle
│   ├──  libs.versions.toml
```

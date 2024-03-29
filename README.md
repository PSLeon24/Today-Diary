# Today-Diary
<img src="https://github.com/PSLeon24/Today-Diary/assets/59058869/928e1d33-dffd-42de-a246-78a9c3999fe9" height="300px"/>&nbsp;&nbsp;일기 앱 <b>'오늘, 하루'</b> 개발 일지<br>
시연 영상 보기: https://tv.kakao.com/v/440038661

## I. 개발 앱 선정 및 선정 이유
교재를 학습하던 중 Diary 앱 실습 예제가 있어서 현재 플레이스토어에 존재하는 일기장 어플을 찾아 보았다. 대부분의 일기가 너무 복잡한 기능이 많았으며, 일기의 경우 개인의 사생활이 많이 기록될 것이라 본인 외에는 쉽게 볼 수 없는 비밀번호가 적용된 일기장이 만들면 어떨까? 하는 생각으로 '오늘, 하루'라는 비밀 일기장 어플리케이션을 개발하게 되었다.

## II. 작업 계획표(일정표) 작성

<pre>
날짜      진행 여부      목표
~ 11/03     O      프로젝트 구상, 아이디어 논의, 활용 기술 정리
~ 11/10     O      UI설계, 리소스 자료 구하기
~ 11/17     O      화면 설계 → .xml 코드 작성, 일기 작성, 수정 java 코드 작성
~ 11/24     O      기능 구현 → 일기 수정 및 삭제 코드 수정, 일기 목록 java 코드 작성
~ 12/01     O      기능 구현 → 목록 부분 .java 코드 작성 / 1차 프로젝트 마무리 / 앱 유지보수 및 최종보고서 양식 제작 및 초안 작성
~ 12/08     O      주간보고서 및 최종보고서 제출
</pre>

## III. 관련 앱 조사
- 이지 다이어리 [Android]
https://play.google.com/store/apps/details?id=me.blog.korn123.easydiary&hl=ko&gl=US
- Diary with lock [Android]
https://play.google.com/store/apps/details?id=com.adpog.diary&hl=ko&gl=US
- 해마 [Android]
https://play.google.com/store/apps/details?id=com.marchlab.haema&hl=ko&gl=US

## IV. 활용 기술 정리
사용자 인증 구현
- 교재 p. 324 ~ 326, 파일처리(미리 파일을 생성해두고 0000으로 초기값 설정, 설정 메뉴에서 변경 가능 → 앱 최초 실행 시 데이터가 '0000'인 password.txt 파일 생성 [SharedPreferences]

화면 전환 구현
- 교재 p. 392 (인텐트 활용)

디자인 활용
- CalendarView
- Custom Font - (카페 24 클래식 타입, 휴먼범석체)
- Option Menu

일기 저장, 읽기, 수정, 삭제 구현
- File Input/Output (교재 p. 328 ~ 332)
- 삭제 버튼 클릭 시 (대화상자[dialog] 활용하여 삭제 여부 확인) - 교재 p. 312 ~ 317

우측 상단 옵션 메뉴 구현
- 교재 p. 287~293 메뉴와 대화상자
- PIN 재설정(대화상자[교재 p .315 ~ 317], 파일 입출력)

일기 목록 구현
- 교재 p. 429 ~ 430, 리스트뷰(Custom ListView: List, ListItem, ListAdapter)
- 대화상자 - 교재 p. 322 문제 6번
- 롱클릭 이벤트 - 삭제 구현

## V. 앱에 필요한 리소스 정리
 - 앱 아이콘

 - 스플래시 이미지
 - 로고

 - PIN 관련 리소스 이미지  - 일기 저장 리소스 이미지

## 개선 사항 기록
- 본 어플리케이션의 의도를 사용자에게 알리고자 스플래시 화면 및 앱 아이콘을 적용하였다.
- PIN 재설정을 한 적이 없으면 PIN 찾기를 눌러도 무반응인 것을 파악 후, PIN 재설정한 적이 없으면 hint.txt가 존재하지 않는 것을 조건문을 활용해 Toast 메시지를 출력하도록 수정하였다.
- 목록 화면을 꾹 누르면 삭제가 가능하도록 수정하였다.
- 2022년 12월 1일과 같은 경우 파일명이 2022121.txt가 되는데, 20221201.txt과 같이 8자리가 되지 않으면 에러가 발생하는 것을 파악 후, 먼저 파일명을 불러와 7자리인지 8자리인지 판별하는 알고리즘을 추가하여 오류를 해결하였다.
- 위 오류와 마찬가지로 2022년 12월 1일과 같이 파일명이 7자리인 경우 삭제가 되지 않는 오류를 발견하고 이를 개선했다.

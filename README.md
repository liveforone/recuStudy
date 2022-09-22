# recuStudy - 스터디 모집 서비스
> 각종 스터디를 같이 할 사람을 찾는 서비스이다.

제한 인원, 최종 날짜, 장소, 참여 버튼 클릭하고, 참여한 사람 리스트에 출력까지, 남은인원 출력

## 설명
* 개발은 특히 스터디를 하는 것이 중요하다.
* 무언가를 공부하기도 하고, 같이 프로젝트를 하기도 한다.
* 마음이 맞는 시기에, 장소에서 그런 사람들끼리 만나는 플랫폼이 부족하다 느꼈다.
* 따라서 해당 프로젝트는 인원, 마감날짜, 일정, 장소까지 모두 맞는 사람끼리 스터디를 하는 플랫폼이다.
* rest-api로 통신하고 뷰는 처리하지 않고 오직 서버만 제작한다.

## 설계
* 스터디 참여는 한 번의 버튼 클릭으로 참여한다.
* study, users 도메인이 존재한다.
* 유저는 최대 3개의 스터디를 진행할 수 있다.
* 스터디에 한명이 신청을 할때마다 카운트는 올라가고, 
  제한인원에 도달시 서버안에서 더이상 신청이 불가능하게 한다.(이러한 경우 body에 메세지를 보낸다.)
* 또한 마감날짜가 다 찼다면 신청이 불가능해진다.

### 마감날짜 제한 방법
* localdate 는 2019-11-13 이러한 형태를 가진다.
* 아래와 같은 방법으로 월과 일을 문자열로 뽑아낼 수 있다.
* 다만 1월부터 9월의 경우 01, 02 와 같은 식으로 출력된다. 사용자에게 월을 입력받을때 0을 강요할 순 없다.
* 따라서 아래 뽑아낸 문자열에 앞에 0을 더해주면된다.
```
LocalDate localDate = LocalDate.now();
String parsedLocalDate = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
String[] arr = parsedLocalDateTimeNow.split("-");
String curMonth = arr[1];
String curDay = arr[2];
System.out.println(curMonth);
System.out.println(curDay);
------------------------------
String d = "9";
System.out.println("0" + d);
```

# recuStudy - 스터디 모집 서비스
> 각종 스터디를 같이 할 사람을 찾는 서비스이다.

## 설명
* 개발은 특히 스터디를 하는 것이 중요하다.
* 무언가를 공부하기도 하고, 같이 프로젝트를 하기도 한다.
* 마음이 맞는 시기에, 장소에서 그런 사람들끼리 만나는 플랫폼이 부족하다 느꼈다.
* 따라서 해당 프로젝트는 인원, 마감날짜, 일정, 장소까지 모두 맞는 사람끼리 스터디를 하는 플랫폼이다.
* rest-api로 통신하고 뷰는 처리하지 않고 오직 서버만 제작한다.
* 여러가지 제한 조건들이 붙는 프로젝트이다.

## 설계
* 스터디 참여는 한 번의 버튼 클릭으로 참여한다.
* study, users 도메인이 존재한다.
* 유저는 최대 3개의 스터디를 진행할 수 있다.
* 스터디에 한명이 신청을 할때마다 카운트는 올라가고, 
  제한인원에 도달시 서버안에서 더이상 신청이 불가능하게 한다.(이러한 경우 body에 메세지를 보낸다.)
* 또한 마감날짜가 다 찼다면 신청이 불가능해진다.
* 즉 스터디 신청시 두개의 제한을 가지는데, 첫째로 인원제한, 둘째로 마감날짜

## 마감날짜 제한 방법
* localdate 는 2019-11-13 이러한 형태를 가진다.
* 아래와 같은 방법으로 월과 일을 문자열로 뽑아낼 수 있다.
* 다만 1월부터 9월의 경우 01, 02 와 같은 식으로 출력된다. 사용자에게 월을 입력받을때 0을 강요할 순 없다.
* 따라서 사용자가 한자릿수의 월과 일을 입력할경우 앞에 0을 붙이는 것은 사용자의 자유이고,
* 서버에서는 한자릿수일경우 앞에 0을 붙이고 아닐경우 0을 붙이지 않는 방식으로 진행한다.
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
if (d.length() == 1) {
  System.out.println("0" + d);
}
```

## 유저 스터디 목록 저장
* 첫번째로 인원체크를 한다.
* limitMon <= currMonth && limitDay < currDay로 마감 월과 일을 체크한다.
* 모든 조건에 부합할경우 현재 참여인원을 +1 하고,
* 유저 서비스에서 study1부터 3까지 널이 아닌 필드를 찾아서 스터디 제목을 집어넣어 업데이트한다.
* 따라서 스터디 몇에 들어갔던 널체크를 통해서 유동적으로 저장이 가능함
* 스터디 마감일 지날시 유저단에서 스터디 자동으로 삭제되는 것은 향후 집어넣기로....

## 주요 url
* /user/signup - get/post
* /user/login - get/post
* /user/myPage - get
* /study/post - post
* /study/apply/{id}

## json body
#### users
<pre>
{
    "email" : "yc4852@gmail.com",
    "password" : "1234"
}

{
    "email" : "admin@restStandard.com",
    "password" : "1234"
}
</pre>

#### study
<pre>
- 테스트1 : 정상 동작
{
    "title" : "test1",
    "content" : "this is content",
    "limitUser" : 3,
    "currUser" : 1,
    "limitMonth" : "11",
    "limitDay" : "05",
    "location" : "seoul"
}

- 테스트2 : limitUser 초과 -
{
    "title" : "test2",
    "content" : "this is content",
    "limitUser" : 3,
    "currUser" : 3,
    "limitMonth" : "11",
    "limitDay" : "05",
    "location" : "seoul"
}

- 테스트3 : 날짜 초과
{
    "title" : "test2",
    "content" : "this is content",
    "limitUser" : 3,
    "currUser" : 2,
    "limitMonth" : "9",
    "limitDay" : "22",
    "location" : "seoul"
}
</pre>
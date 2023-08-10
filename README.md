# wanted-pre-onboarding-backend

# 8월 11일까지 완료하겠습니다.

## 과제 주소
원티드 : https://www.wanted.co.kr/events/pre_ob_be_6 <br/>
깃허브 : https://github.com/lordmyshepherd-edu/wanted-pre-onboardung-backend-selection-assignment

## 지원자 성명
김솔배 

## 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)



## 데이터베이스 테이블 구조
![img.png](img.png)

## 구현한 API의 동작을 촬영한 데모 영상 링크


추가 예정

## 구현 방법 및 이유에 대한 간략한 설명

### Mysql 사용 이유
- 관계형 데이터베이스로 테이블에 관계를 맺고 필요한 부분만 조회할 수 있기에 사용하였습니다.

### QueryDsl 사용사용 이유
- 게시글 조회시 카테고리 선택 등의 확장성을 고려해 Pageable 보다 장점이 많다고 판단하여 사용하였습니다.
- 문자가 아닌 코드로 쿼리를 작성할 수 있어 컴파일 시점에서 문법 오류를 확인할 수 있어 사용하였습니다.

### Spring Security 사용 이유
- 인증, 인가를 확인하는데 있어 Spring Security에서 잘 구현되어있어 사용하였습니다.
- 직접 서블렛 필터를 작성하는 비용보다 Spring Security를 사용하는게 유리하다고 판단하였습니다.

### validation 사용이유
- 사용자 입력에 대한 검증을 간편하게 하기위해 사용하였습니다.


### JWT 사용이유
- 상태를 가지지 않는(stateless) 인증 방식으로 서버의 확장성과 성능을 고려하여 사용하였습니다.

### 사용자 회원가입

* 이메일, 비밀번호를 입력받고 Vaildation을 통해 검증합니다. <br/>사용한 Vaildation : @Email, @Size
* 비밀번호는 BCryptPasswordEncoder를 사용해 암호화 하였습니다.

### 사용자 로그인
* Spring Security의 로그인 검증을 통해 로그인을 진행하였습니다.
* 로그인 성공시 JWT를 발급하여 Header에 저장하여 클라이언트에 반환합니다.

### 게시글을 생성
* Spring Security를 사용해 로그인한(JWT를 Header에 갖고있는) 사용자는 게시글을 작성할 수 있습니다.

### 게시글 목록을 조회
* QueryDsl을 이용하여 게시글 목록을 최신순으로 DB에서 조회합니다.
* 디폴트 Page : 1, Size : 10 입니다. (파라미터로 변경 가능)

### 특정 게시글을 조회
* 특정 게시글ID를 요청 파라미터로 입력받아 게시글ID에 해당하는 게시글을 DB에서 조회하여 반환합니다.

### 특정 게시글을 수정
* Spring Security를 사용해 로그인한(JWT를 Header에 갖고있는) 사용자만 게시글 수정페이지에 접근할 수 있습니다.
* 게시글 작성자만 본인의 게시글을 수정할 수 있습니다.

### 특정 게시글을 삭제
* Spring Security를 사용해 로그인한(JWT를 Header에 갖고있는) 사용자만 게시글 삭제페이지에 접근할 수 있습니다.
* 게시글 작성자만 본인의 게시글을 삭제할 수 있습니다.

## API 명세(request/response 포함)

### 과제 1. 사용자 회원가입 엔드포인트 <br/>

```html
🎯 [POST] /join
```


#### Request
```json
{
  "email": "wnated@wanted.com",
  "password": "wanted-internship"
}
```

#### Response

성공
```json
{
  "code": "200",
  "message": "회원가입을 성공하였습니다.",
  "data": null
}
```

실패
```json
{
  "code": "400",
  "message": "유효성검사 실패",
  "data": {
    "email": "이메일 조건: @ 포함"
  }
}
```

실패
```json
{
  "code": "400",
  "message": "유효성검사 실패",
  "data": {
    "password": "비밀번호 조건: 8자 이상"
  }
}
```
실패
```json
{
  "code": "400",
  "message": "유효성검사 실패",
  "data": {
    "password": "비밀번호 조건: 8자 이상"
  }
}
```
실패
```json
{
  "code": "400",
  "message": "이미 가입된 이메일입니다.",
  "data": null
}
```
### 과제 2. 사용자 로그인 엔드포인트 <br/>
```html
🎯 [POST] /login
```
#### Request
```json
{
  "email": "wnated@wanted.com",
  "password": "wanted-internship"
}
```

#### Response

성공
```json
{
  "code": "200",
  "message": "로그인 성공",
  "data": {
    "id": 1,
    "email": "wnated@wanted.com"
  }
}
```
Response Header
```
authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3YW50ZWQiLCJyb2xlIjoiVVNFUiIsImlkIjoyLCJleHAiOjE2OTIyNDQ2ODMsImVtYWlsIjoid25hdGVkQHdhbnRlZC5jb20ifQ.ulHmMgsdf3C-ni9lwZr1m-sP6JHKcEbZgPeQBsf1qMctkBDEv8p2O-kN9DFrnsnzcLQheLR8R7yhAg4V9tn3ag 
```

실패
```json
{
  "code": "400",
  "message": "로그인실패",
  "data": null
}
```

### 과제 3. 새로운 게시글을 생성하는 엔드포인트 <br/>
```html
🎯 [POST] /user/posts
```
#### Request
```json
{
  "title": "원티드 백엔드 인텁십",
  "content": "글 생성 엔드포인트"
}
```

#### Response

성공
```json
{
  "code": "200",
  "message": "글 작성을 성공했습니다.",
  "data": null
}
```

실패
```json
{
  "code": "400",
  "message": "로그인을 진행해 주세요",
  "data": null
}
```

### 과제 4. 게시글 목록을 조회하는 엔드포인트 <br/>
```html
🎯 [GET] /posts?page=1&size=10
```

#### Response

성공
```json
{
  "code": "200",
  "message": "글 리스트 조회를 성공했습니다.",
  "data": [
    {
      "id": 7,
      "title": "과제 7",
      "content": "게시글 삭제 기능 구현"
    },
    {
      "id": 6,
      "title": "과제 6",
      "content": "게시글 수정 기능 구현"
    },
    {
      "id": 5,
      "title": "과제 5",
      "content": "게시글 단건조회 기능 구현"
    },
    {
      "id": 4,
      "title": "과제 4",
      "content": "게시글 목록조회 기능 구현"
    },
    {
      "id": 3,
      "title": "과제 3",
      "content": "게시글 등록 기능 구현"
    },
    {
      "id": 2,
      "title": "과제 2",
      "content": "로그인 기능 구현"
    },
    {
      "id": 1,
      "title": " 1",
      "content": "회원가입 기능 구현"
    }
  ]
}
```

### 과제 5. 특정 게시글을 조회하는 엔드포인트 <br/>
```html
🎯 [GET] /posts/{postId}
```

#### Response

성공
```json
{
  "code": "200",
  "message": "글 조회에 성공했습니다.",
  "data": {
    "id": 1,
    "title": " 1",
    "content": "회원가입 기능 구현"
  }
}
```

실패
```json
{
  "code": "404",
  "message": "존재하지 않는 글입니다.",
  "data": null
}
```

### 과제 6. 특정 게시글을 수정하는 엔드포인트 <br/>
```html
🎯 [POST] /user/posts/{postId}
```
#### Request
```json
{
  "title": "제목을 수정합니다.",
  "content": "수정된 내용입니다."
}
```

#### Response

성공
```json
{
  "code": "200",
  "message": "글 수정을 성공했습니다.",
  "data": null
}
```

실패
```json
{
  "code": "404",
  "message": "존재하지 않는 글입니다.",
  "data": null
}
```
실패
```json
{
  "code": "400",
  "message": "로그인을 진행해 주세요",
  "data": null
}
```

실패
```json
{
  "code": "403",
  "message": "게시글을 수정/삭제 할 수 있는 사용자는 게시글 작성자만이어야 합니다.",
  "data": null
}
```

### 과제 7. 특정 게시글을 삭제하는 엔드포인트 <br/>
```html
🎯 [DELETE] /user/posts/{postId}
```
#### Response

성공
```json
{
  "code": "200",
  "message": "글 삭제를 성공했습니다.",
  "data": null
}
```

실패
```json
{
  "code": "404",
  "message": "존재하지 않는 글입니다.",
  "data": null
}
```
실패
```json
{
  "code": "400",
  "message": "로그인을 진행해 주세요",
  "data": null
}
```

실패
```json
{
  "code": "403",
  "message": "게시글을 수정/삭제 할 수 있는 사용자는 게시글 작성자만이어야 합니다.",
  "data": null
}
```
